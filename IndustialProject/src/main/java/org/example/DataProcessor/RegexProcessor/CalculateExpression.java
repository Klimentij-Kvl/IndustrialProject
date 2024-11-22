package org.example.DataProcessor.RegexProcessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class CalculateExpression {
    public static String calculate(String expression) {
        return expression;
    }
    public static int CreateNumber(String snumber){
        if(snumber.charAt(0) == '+'){
            int inumber = Integer.parseInt(snumber.substring(1));
            return inumber;
        }
        if(snumber.charAt(0) == '-') {
            int inumber = Integer.parseInt(snumber);
            return inumber;
        }
        int inumber = Integer.parseInt(snumber);
        return inumber;
    }
}

class Number extends CalculateExpression{
    String snumber;
    Number(String snumber) {
        this.snumber = snumber;
    }
    int getNumber() {
        // Вызов метода из родительского класса
        return CreateNumber(snumber);
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
        String regex = "(\\d+)(.)(\\d+)";
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
