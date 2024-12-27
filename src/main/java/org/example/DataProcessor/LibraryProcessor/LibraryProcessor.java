package org.example.DataProcessor.LibraryProcessor;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.math.BigDecimal;

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
}
