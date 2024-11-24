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
            return String.valueOf(calc.simpleCalculator());
        }
    }
}


class CalculateSimpleExpression extends CalculateExpression {
    CalculateSimpleExpression(String expression) {
        this.expression = expression;
    }

    static int CreateNumber(String sNumber) {
        return Integer.parseInt(sNumber);
    }

    public int simpleCalculator() throws ArithmeticException {
        String regexMulDiv = "(\\d+)([*/])([+-]?\\d+)";
        Pattern patternMulDiv = Pattern.compile(regexMulDiv);
        Matcher matcherMulDiv = patternMulDiv.matcher(expression);

        while (matcherMulDiv.find()) {
            int operand1 = CreateNumber(matcherMulDiv.group(1));
            int operand2 = CreateNumber(matcherMulDiv.group(3));
            String operator = matcherMulDiv.group(2);

            int result;
            if (operator.equals("*")) {
                result = operand1 * operand2;
            } else if (operator.equals("/")) {
                if (operand2 == 0) {
                    throw new ArithmeticException("[division by zero]");
                }
                result = operand1 / operand2;
            } else {
                continue;
            }

            expression = expression.substring(0, matcherMulDiv.start()) + result + expression.substring(matcherMulDiv.end());
            matcherMulDiv = patternMulDiv.matcher(expression);
        }

        String regexAddSub = "([+-]?\\d+)([+-])([+-]?\\d+)";
        Pattern patternAddSub = Pattern.compile(regexAddSub);
        Matcher matcherAddSub = patternAddSub.matcher(expression);

        while (matcherAddSub.find()) {
            int operand1 = CreateNumber(matcherAddSub.group(1));
            int operand2 = CreateNumber(matcherAddSub.group(3));
            String operator = matcherAddSub.group(2);

            int result;
            if (operator.equals("+")) {
                result = operand1 + operand2;
            } else if (operator.equals("-")) {
                result = operand1 - operand2;
            } else {
                continue;
            }

            expression = expression.substring(0, matcherAddSub.start()) + result + expression.substring(matcherAddSub.end());
            matcherAddSub = patternAddSub.matcher(expression);
        }

        return CreateNumber(expression);
    }
}
