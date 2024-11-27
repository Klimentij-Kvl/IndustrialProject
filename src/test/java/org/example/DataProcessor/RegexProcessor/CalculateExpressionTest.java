package org.example.DataProcessor.RegexProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CalculateExpressionTest {

    @Test
    public void testSimpleExpressions(){
        //Суммы и разности
        assertEquals("5",CalculateExpression.result("3+2"));
        assertEquals("-2",CalculateExpression.result("2-4"));
        assertEquals("-1",CalculateExpression.result("-3+2"));
        assertEquals("4", CalculateExpression.result("5+2-3"));
        //Произведение
        assertEquals("6",CalculateExpression.result("3*2"));
        assertEquals("-6",CalculateExpression.result("-3*2"));
        assertEquals("-6",CalculateExpression.result("3*-2"));
        assertEquals("10", CalculateExpression.result("5*6/3"));
        //Частное и деление на ноль
        assertEquals("3",CalculateExpression.result("6/2"));
        assertEquals("3",CalculateExpression.result("6÷2"));
        assertEquals("-3",CalculateExpression.result("-6/2"));
        assertEquals("1,5",CalculateExpression.result("6/4"));
        assertEquals("-1,5",CalculateExpression.result("-6/4"));
        assertEquals("-2,4",CalculateExpression.result("6/-2.5"));
        assertEquals("2,4",CalculateExpression.result("-6/-2.5"));

        assertThrows(ArithmeticException.class, () -> {
            CalculateExpression.result("6/0");
        });
    }

    @Test
    public void testNumber(){
        assertEquals("3",CalculateExpression.result("3"));
        assertEquals("-3",CalculateExpression.result("-3"));
        assertEquals("3",CalculateExpression.result("+3"));
        assertEquals("3",CalculateExpression.result("--3"));
    }


    @Test
    public void testComplicatedExpressions(){
        assertEquals("4", CalculateExpression.result("(5+2)-3"));
        assertEquals("4", CalculateExpression.result("5+(2-3)"));
        assertEquals("2", CalculateExpression.result("(5+2)-(2+3)"));
        assertEquals("2", CalculateExpression.result("5*(2-3)+7"));
        assertEquals("-2", CalculateExpression.result("5+(2-3)*7"));
        assertEquals("4", CalculateExpression.result("5+(2-3)*7-2*-3"));
        assertEquals("8", CalculateExpression.result("2-3*-2"));
        assertEquals("13", CalculateExpression.result("5+(2-3*-2)"));
        assertEquals("8,5", CalculateExpression.result("5+(2-3/-2)"));
        assertEquals("-9", CalculateExpression.result("(-3/5)*(7+8)"));
        assertEquals("-0,6", CalculateExpression.result("(-3/5)"));

        assertThrows(ArithmeticException.class, () -> {
            CalculateExpression.result("5+(2-3)/0");
        });
    }

}
