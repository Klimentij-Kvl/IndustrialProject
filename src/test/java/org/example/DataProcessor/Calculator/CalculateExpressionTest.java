package org.example.DataProcessor.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CalculateExpressionTest {
    private final CalculateExpression calculateExpression = new CalculateExpression();

    @Test
    public void testSimpleExpressions(){
        //Суммы и разности
        assertEquals("5", calculateExpression.calculate("3+2"));
        assertEquals("-2", calculateExpression.calculate("2-4"));
        assertEquals("-1", calculateExpression.calculate("-3+2"));
        assertEquals("4", calculateExpression.calculate("5+2-3"));
        //Произведение
        assertEquals("6", calculateExpression.calculate("3*2"));
        assertEquals("-6", calculateExpression.calculate("-3*2"));
        assertEquals("-6", calculateExpression.calculate("3*-2"));
        assertEquals("10", calculateExpression.calculate("5*6/3"));
        //Частное и деление на ноль
        assertEquals("3", calculateExpression.calculate("6/2"));
        assertEquals("3", calculateExpression.calculate("6÷2"));
        assertEquals("-3", calculateExpression.calculate("-6/2"));
        assertEquals("1,5", calculateExpression.calculate("6/4"));
        assertEquals("-1,5", calculateExpression.calculate("-6/4"));
        assertEquals("-2,4", calculateExpression.result("6/-2.5"));
        assertEquals("2,4", calculateExpression.result("-6/-2.5"));

        assertThrows(ArithmeticException.class, () -> {
            calculateExpression.calculate("6/0");
        });
    }

    @Test
    public void testNumber(){
        assertEquals("3", calculateExpression.calculate("3"));
        assertEquals("-3", calculateExpression.calculate("-3"));
        assertEquals("3", calculateExpression.calculate("+3"));
        assertEquals("3", calculateExpression.calculate("--3"));
    }


    @Test
    public void testComplicatedExpressions(){
        assertEquals("4", calculateExpression.result("(5+2)-3"));
        assertEquals("4", calculateExpression.result("5+(2-3)"));
        assertEquals("2", calculateExpression.result("(5+2)-(2+3)"));
        assertEquals("2", calculateExpression.result("5*(2-3)+7"));
        assertEquals("-2", calculateExpression.result("5+(2-3)*7"));
        assertEquals("4", calculateExpression.calculate("5+(2-3)*7-2*-3"));
        assertEquals("8", calculateExpression.calculate("2-3*-2"));
        assertEquals("13", calculateExpression.calculate("5+(2-3*-2)"));
        assertEquals("8,5", calculateExpression.calculate("5+(2-3/-2)"));

        assertThrows(ArithmeticException.class, () -> {
            calculateExpression.calculate("5+(2-3)/0");
        });
    }

}
