package org.example.DataProcessor.RegexProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CalculateExpressionTest {


    @Test
    public void testSimpleExpressions(){
        //Суммы и разности
        assertEquals("5",CalculateExpression.calculate("3+2"));
        assertEquals("-2",CalculateExpression.calculate("2-4"));
        assertEquals("-1",CalculateExpression.calculate("-3+2"));
        assertEquals("4", CalculateExpression.calculate("5+2-3"));
        //Произведение
        assertEquals("6",CalculateExpression.calculate("3*2"));
        assertEquals("-6",CalculateExpression.calculate("-3*2"));
        assertEquals("-6",CalculateExpression.calculate("3*-2"));
        assertEquals("10", CalculateExpression.calculate("5*6/3"));
        //Частное и деление на ноль
        assertEquals("3",CalculateExpression.calculate("6/2"));
        assertEquals("-3",CalculateExpression.calculate("-6/2"));

        try {
            CalculateExpression.calculate("6/0");
            fail("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            assertEquals("[division by zero]", e.getMessage());
        }
    }

    @Test
    public void testComplicatedExpressions(){
        assertEquals("4", CalculateExpression.calculate("(5+2)-3"));
        assertEquals("4", CalculateExpression.calculate("5+(2-3)"));
        assertEquals("2", CalculateExpression.calculate("(5+2)-(2+3)"));
        assertEquals("2", CalculateExpression.calculate("5*(2-3)+7"));
        assertEquals("-2", CalculateExpression.calculate("5+(2-3)*7"));
        assertEquals("4", CalculateExpression.calculate("5+(2-3)*7-2*-3"));
        assertEquals("8", CalculateExpression.calculate("2-3*-2"));
        assertEquals("13", CalculateExpression.calculate("5+(2-3*-2)"));

        try {
            CalculateExpression.calculate("5+(2-3)/0");
            fail("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            assertEquals("[division by zero]", e.getMessage());
        }

    }

}