package org.example.DataProcessor.LibraryProcessor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryProcessorTest
{
    @Test
    void testSimpleExpressions()
    {
        assertEquals("5", LibraryProcessor.calculate("3+2"));
        assertEquals("-2", LibraryProcessor.calculate("2-4"));
        assertEquals("6", LibraryProcessor.calculate("3*2"));
        assertEquals("3", LibraryProcessor.calculate("6/2"));
    }
    @Test
    void testComplicatedExpressions()
    {
        assertEquals("4", LibraryProcessor.calculate("(5+2)-3"));
        assertEquals("8", LibraryProcessor.calculate("2-3*-2"));
        assertEquals("8.5", LibraryProcessor.calculate("5+(2-3/-2)"));
    }
    @Test
    void testDivisionByZero()
    {
        assertThrows(ArithmeticException.class, () -> LibraryProcessor.calculate("6/0"));
    }
    @Test
    void testTrigonometricConstants()
    {
        assertEquals("0", LibraryProcessor.calculate("sin(pi)"));
        assertEquals("0", LibraryProcessor.calculate("tan(0)"));
        assertEquals("-1", LibraryProcessor.calculate("cos(pi)"));
    }
    @Test
    void testArithmeticConstants()
    {
        assertEquals("1", LibraryProcessor.calculate("ln(e)"));
        assertEquals("0", LibraryProcessor.calculate("ln(1)"));
    }
    @Test
    void testMathematicalConstants()
    {
        assertEquals("3.1415926536", LibraryProcessor.calculate("pi"));
        assertEquals("2.7182818285", LibraryProcessor.calculate("e"));
    }
}
