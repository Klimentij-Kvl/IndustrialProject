package org.example.DataProcessor.RegexProcessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class CalculateExpression {

    protected String expression;
    public static String calculate(String expression) {
        {
            CalculateSimpleExpression calc = new CalculateSimpleExpression(expression);
            return String.valueOf(calc.simpleCalculator());
        }
    }

}

class CalculateSimpleExpression extends CalculateExpression{

    CalculateSimpleExpression(String expression){
        this.expression = expression;
    }
    static int CreateNumber(String sNumber){
        if(sNumber.charAt(0) == '+'){
            return Integer.parseInt(sNumber.substring(1));
        }
        return Integer.parseInt(sNumber);
    }

    public int simpleCalculator() throws ArithmeticException{
        String regex = "([+-]?\\d+)([+\\-*/])([+-]?\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        if(matcher.find()) {
            String sOperand1 = matcher.group(1);
            String operator = matcher.group(2);
            String sOperand2 = matcher.group(3);
            int operand1 = CreateNumber(sOperand1);
            int operand2 = CreateNumber(sOperand2);
            switch (operator) {
                case "+":
                    return operand1 + operand2;
                case "-":
                    return operand1 - operand2;
                case "*":
                    return operand1 * operand2;
                case "/":
                    if (operand2 == 0) {
                        throw new ArithmeticException("[division by zero]");
                    }
                    return operand1 / operand2;
                default:
                    throw new IllegalArgumentException("Invalid expression format");
            }
        } else {
            throw new IllegalArgumentException("Invalid expression format");
        }
    }

}


//Waiting to be DELETED:
/*
class Number extends CalculateExpression{
    String sNumber;
    Number(String sNumber) {
        this.sNumber = sNumber;
    }
    int getNumber() {
        // Вызов метода из родительского класса
        return CreateNumber(sNumber);
    }
}

class Sum extends CalculateExpression{
    String simpleSum;
    Sum(String simpleSum){
        this.simpleSum = simpleSum;
    }

    int CalculateSum(){
        String regex = "([+-]?\\d+)([+-]\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(simpleSum);
        String saugend, saddend;
        int augend =0,addend=0;
        if (matcher.find()) {
            saugend = matcher.group(1);
            saddend = matcher.group(2);
            augend = CreateNumber(saugend);
            addend = CreateNumber(saddend);

        }
        return (augend + addend);
    }
}

class Mul extends CalculateExpression {
    String multi;
    Mul(String multi){
        this.multi = multi;
    }

    int CalculateMul(){
        String regex = "([+-]?\\d+)(.)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(multi);
        String smul1, smul2;
        int mul2=0,mul1=0;
        if (matcher.find()) {
            smul1 = matcher.group(1);
            smul2 = matcher.group(3);
            mul1 = CreateNumber(smul1);
            mul2 = CreateNumber(smul2);

        }
        return (mul1 * mul2);
    }
}

class Div extends CalculateExpression {
    String divi;
    Div(String divi){
        this.divi = divi;
    }
    int CalculateDiv() throws ArithmeticException{
        String regex = "([+-]?\\d+)(.)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(divi);
        String smul1, smul2;
        int mul2=1,mul1=0;
        if (matcher.find()) {
            smul1 = matcher.group(1);
            smul2 = matcher.group(3);
            mul1 = CreateNumber(smul1);
            mul2 = CreateNumber(smul2);
        }
        if (mul2 == 0) {
            throw new ArithmeticException("[divizion by zero]");
        }
        return (mul1 / mul2);
    }
}*/