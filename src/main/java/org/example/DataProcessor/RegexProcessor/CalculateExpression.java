package org.example.DataProcessor.RegexProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CalculateExpression {
    protected String expression;

    public static String calculate(String expression) throws ArithmeticException {
        {
            Pattern pattern = Pattern.compile("\\(([^()]+)\\)");
            Matcher matcher = pattern.matcher(expression);
            while(matcher.find()){
                String simpleExpression = matcher.group(1);
                String simpleResult = calculate(simpleExpression);
                expression = expression.replace("(" + simpleExpression + ")", simpleResult);
                matcher = pattern.matcher(expression);
            }
            CalculateSimpleExpression calc = new CalculateSimpleExpression(expression);
            return calc.simpleCalculator();
        }
    }
}


class CalculateSimpleExpression extends CalculateExpression {
    CalculateSimpleExpression(String expression) {
        this.expression = expression;
    }

    static double CreateNumber(String sNumber) {
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
        String regexMulDiv = "(\\d+\\.?\\d*)([*/÷])([+-]?\\d+\\.?\\d*)";
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

        String regexAddSub = "([+-]?\\d+\\.?\\d*)([+-])([+-]?\\d+\\.?\\d*)";
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