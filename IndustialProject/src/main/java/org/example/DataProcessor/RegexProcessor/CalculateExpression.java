package org.example.DataProcessor.RegexProcessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class CalculateExpression {
    public static String calculate(String expression) {
        return expression;
    }

}

class Number extends CalculateExpression{
    String snumber;
    Number(String snumber) {
        this.snumber = snumber;
    }

    String CreateNumber(){
        if(snumber.charAt(0) == '+'){
            return snumber.substring(1);
        }
        for(int i = 0; i<3;i++){}
        if(snumber.charAt(0) == '-') {
            return snumber;
        }
        return snumber;
    }
}
//
