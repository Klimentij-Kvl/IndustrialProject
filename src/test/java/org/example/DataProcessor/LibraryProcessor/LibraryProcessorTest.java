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
    void testNumberFormatting()
    {
        assertEquals("3.14159", LibraryProcessor.calculate("3.14159"));
    }
}
