package org.example.DataProcessor.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class RegexCalculatorTest {
    private final RegexCalculator regexCalculator = new RegexCalculator();

    @Test
    public void testSimpleExpressions(){
        //Суммы и разности
        assertEquals("5", regexCalculator.calculate("3+2"));
        assertEquals("-2", regexCalculator.calculate("2-4"));
        assertEquals("-1", regexCalculator.calculate("-3+2"));
        assertEquals("4", regexCalculator.calculate("5+2-3"));
        //Произведение
        assertEquals("6", regexCalculator.calculate("3*2"));
        assertEquals("-6", regexCalculator.calculate("-3*2"));
        assertEquals("-6", regexCalculator.calculate("3*-2"));
        assertEquals("10", regexCalculator.calculate("5*6/3"));
        //Частное и деление на ноль
        assertEquals("3", regexCalculator.calculate("6/2"));
        assertEquals("3", regexCalculator.calculate("6÷2"));
        assertEquals("-3", regexCalculator.calculate("-6/2"));
        assertEquals("1,5", regexCalculator.calculate("6/4"));
        assertEquals("-1,5", regexCalculator.calculate("-6/4"));
        assertEquals("-2,4", regexCalculator.result("6/-2.5"));
        assertEquals("2,4", regexCalculator.result("-6/-2.5"));

        assertThrows(ArithmeticException.class, () -> {
            regexCalculator.calculate("6/0");
        });
    }

    @Test
    public void testNumber(){
        assertEquals("3", regexCalculator.calculate("3"));
        assertEquals("-3", regexCalculator.calculate("-3"));
        assertEquals("3", regexCalculator.calculate("+3"));
        assertEquals("3", regexCalculator.calculate("--3"));
    }


    @Test
    public void testComplicatedExpressions(){
        assertEquals("4", regexCalculator.result("(5+2)-3"));
        assertEquals("4", regexCalculator.result("5+(2-3)"));
        assertEquals("2", regexCalculator.result("(5+2)-(2+3)"));
        assertEquals("2", regexCalculator.result("5*(2-3)+7"));
        assertEquals("-2", regexCalculator.result("5+(2-3)*7"));
        assertEquals("4", regexCalculator.calculate("5+(2-3)*7-2*-3"));
        assertEquals("8", regexCalculator.calculate("2-3*-2"));
        assertEquals("13", regexCalculator.calculate("5+(2-3*-2)"));
        assertEquals("8,5", regexCalculator.calculate("5+(2-3/-2)"));

        assertThrows(ArithmeticException.class, () -> {
            regexCalculator.calculate("5+(2-3)/0");
        });
    }

}
