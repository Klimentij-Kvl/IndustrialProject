package org.example.DataProcessor.Calculator;

import org.example.DataBase.DataStorage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexCalculator extends CalculatorAdapter {
    protected String expression;
    private final DataStorage dataStorage = DataStorage.getInstance();

    public RegexCalculator(){}

    @Override
    protected String ReplaceAddedFunctionsToExpressions(String rawExpression) {
        Map<String, String> functions = dataStorage.getFunctions();
        String funcName = functions.keySet().iterator().next(); // Имя функции
        String funcTemplate = functions.get(funcName); // Шаблон функции
        String regex = funcName + "\\((\\d+(?:,\\d+)*)\\)"; // Регулярное выражение для функции

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rawExpression);
        StringBuilder result = new StringBuilder();
        String[] vars = {"x", "y", "z"}; // Переменные для аргументов

        while (matcher.find()) {
            String[] args = matcher.group(1).split(","); // Аргументы функции
            String funcBody = funcTemplate;

            // Замена переменных аргументами
            for (int i = 0; i < args.length && i < vars.length; i++) {
                funcBody = funcBody.replace(vars[i], args[i]);
            }

            // Оборачиваем результат функции в скобки
            matcher.appendReplacement(result, "(" + funcBody + ")");
        }

        matcher.appendTail(result); // Добавляем остаток строки
        return result.toString();
    }


    public String result(String expression)throws ArithmeticException{
        String result = calculate(expression);
        return sign(result);
    }

    @Override
    public String calculate(String expr) throws ArithmeticException {
        {
            if(!dataStorage.getFunctions().isEmpty()) {
                expr = ReplaceAddedFunctionsToExpressions(expr);
            }
            Pattern pattern = Pattern.compile("\\(([^()]+)\\)");
            Matcher matcher = pattern.matcher(expr);
            while(matcher.find()){
                String simpleExpression = matcher.group(1);
                String simpleResult = calculate(simpleExpression);
                expr = expr.replace("(" + simpleExpression + ")", simpleResult);
                matcher = pattern.matcher(expr);
            }
            CalculateSimpleExpression calc = new CalculateSimpleExpression(expr);
            return calc.simpleCalculator();
        }
    }

    public static String sign(String toChange){
        toChange = toChange.replaceAll("--", "+");
        if (toChange.startsWith("+")) {
            toChange = toChange.substring(1);
        }
        return toChange;
    }


    static class CalculateSimpleExpression extends RegexCalculator {
        CalculateSimpleExpression(String expression) {
            this.expression = expression;
        }

        static double CreateNumber(String sNumber) {
            sNumber = sNumber.replace("--", "");
            sNumber = sNumber.replace(",", ".");
            return Double.parseDouble(sNumber);
        }

        public String FormatResult(double result){
            if (result % 1 == 0) {
                return String.valueOf((int) result);
            } else {


                String formatted = String.format("%.3f", result);
                formatted = formatted.replaceAll("\\.?0+$", "");
                return formatted;

            }
        }

        public String simpleCalculator() throws ArithmeticException {
            String regexMulDiv = "(\\d+[.,]?\\d*)([*/÷])([+-]?\\d+[.,]?\\d*)";
            Pattern patternMulDiv = Pattern.compile(regexMulDiv);
            Matcher matcherMulDiv = patternMulDiv.matcher(expression);

            while (matcherMulDiv.find()) {
                double operand1 = CreateNumber(matcherMulDiv.group(1));
                double operand2 = CreateNumber(matcherMulDiv.group(3));
                String operator = matcherMulDiv.group(2);

                double result;
                if (operator.equals("*")) {
                    result = operand1 * operand2;
                } else if (operator.equals("/") || operator.equals("÷")) {
                    if (operand2 == 0) {
                        throw new ArithmeticException("[division by zero]");
                    }
                    result = operand1 / operand2;
                } else {
                    continue;
                }

                expression = expression.substring(0, matcherMulDiv.start()) + FormatResult(result) + expression.substring(matcherMulDiv.end());
                matcherMulDiv = patternMulDiv.matcher(expression);
            }

            String regexAddSub = "([+-]?\\d+[.,]?\\d*)([+-])([+-]?\\d+[.,]?\\d*)";
            Pattern patternAddSub = Pattern.compile(regexAddSub);
            Matcher matcherAddSub = patternAddSub.matcher(expression);

            while (matcherAddSub.find()) {
                double operand1 = CreateNumber(matcherAddSub.group(1));
                double operand2 = CreateNumber(matcherAddSub.group(3));
                String operator = matcherAddSub.group(2);

                double result;
                if (operator.equals("+")) {
                    result = operand1 + operand2;
                } else if (operator.equals("-")) {
                    result = operand1 - operand2;
                } else {
                    continue;
                }

                expression = expression.substring(0, matcherAddSub.start()) + FormatResult(result) + expression.substring(matcherAddSub.end());
                matcherAddSub = patternAddSub.matcher(expression);
            }

            return FormatResult(CreateNumber(expression));
        }
    }
}
