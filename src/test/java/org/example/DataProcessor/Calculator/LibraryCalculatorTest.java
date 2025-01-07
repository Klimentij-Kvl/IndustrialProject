package org.example.DataProcessor.Calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryCalculatorTest
{
    private final Calculator libraryCalculator = new LibraryCalculator();
    @Test
    void testSimpleExpressions()
    {
        assertEquals("5", libraryCalculator.calculate("3+2"));
        assertEquals("-2", libraryCalculator.calculate("2-4"));
        assertEquals("6", libraryCalculator.calculate("3*2"));
        assertEquals("3", libraryCalculator.calculate("6/2"));
    }
    @Test
    void testComplicatedExpressions()
    {
        assertEquals("4", libraryCalculator.calculate("(5+2)-3"));
        assertEquals("8", libraryCalculator.calculate("2-3*-2"));
        assertEquals("8.5", libraryCalculator.calculate("5+(2-3/-2)"));
    }
    @Test
    void testDivisionByZero()
    {
        //assertThrows(ArithmeticException.class, () -> libraryCalculator.calculate("6/0"));
        assertEquals("6/0", libraryCalculator.calculate("6/0"));
    }
    @Test
    void testTrigonometricConstants()
    {
        assertEquals("0", libraryCalculator.calculate("sin(pi)"));
        assertEquals("0", libraryCalculator.calculate("tan(0)"));
        assertEquals("-1", libraryCalculator.calculate("cos(pi)"));
    }
    @Test
    void testArithmeticConstants()
    {
        assertEquals("1", libraryCalculator.calculate("ln(e)"));
        assertEquals("0", libraryCalculator.calculate("ln(1)"));
    }
    @Test
    void testMathematicalConstants()
    {
        assertEquals("3.1415926536", libraryCalculator.calculate("pi"));
        assertEquals("2.7182818285", libraryCalculator.calculate("e"));
    }
    /*
    @Test
    void testProcessExpressions()
    {
        List<String> input = List.of("  3 + 2", "3          * 2", "(5 + 2) - 3");
        List<String> expected = List.of("5", "6", "4");
        assertEquals(expected, libraryCalculator.processExpressions(input));
    }
     */
}
