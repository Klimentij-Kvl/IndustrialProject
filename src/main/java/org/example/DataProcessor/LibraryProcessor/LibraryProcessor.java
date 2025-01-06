package org.example.DataProcessor.LibraryProcessor;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LibraryProcessor
{

    public static String calculate(String expr)
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
            throw new ArithmeticException("[Ошибка вычисления]");
        }
    }

    public static List<String> processExpressions(List<String> data)
    {
        return data.stream()
                .map(LibraryProcessor::extractAndCalculate)
                .collect(Collectors.toList());
    }

    private static String extractAndCalculate(String line)
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
