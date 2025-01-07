package org.example.DataProcessor.Calculator;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryCalculator extends CalculatorAdapter
{
    protected String ReplaceAddedFunctionsToExpressions(String rawExpression){
        //TODO: realize replacing user added functions to expressions
        return rawExpression;
    }

    @Override
    public String calculate(String expr)
    {
        try
        {
            // Создаем новый объект ExpressionEvaluator и оцениваем выражение
            ExpressionEvaluator exprEval = new ExpressionEvaluator();
            double result = exprEval.evaluate(expr);
            // Возвращаем результат в формате строки без лишних нулей
            return BigDecimal.valueOf(result).stripTrailingZeros().toPlainString();
        }
        catch (Exception e)
        {
            //throw new ArithmeticException("[Ошибка вычисления]");
            return expr;
        }
    }

    private String extractAndCalculate(String line)
    {
        Pattern pattern = Pattern.compile("\\s*([0-9+\\-*/().]+)\\s*");
        Matcher matcher = pattern.matcher(line);
        //StringBuilder result = new StringBuilder(line);
        if(matcher.matches())
        {
            String expression = matcher.group(1).replaceAll("\\s+", "");
            try
            {
                //String evaluated = calculate(expression);
                //int start = result.indexOf(expression);
                //result.replace(start, start + expression.length(), evaluated);
                return calculate(expression);
            }
            catch(ArithmeticException e)
            {
                // ігнорік
                return "[Error of equation]";
            }
        }
        //return result.toString();
        return "[Uncorrect expression]";
    }
}
