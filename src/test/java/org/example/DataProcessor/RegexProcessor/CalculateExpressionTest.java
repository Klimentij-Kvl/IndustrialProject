package org.example.DataProcessor.RegexProcessor;

import org.example.DataProcessor.Calculator.RegexCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class CalculateExpressionTest {

    @ParameterizedTest
    @CsvSource({
            "3, 3",
            "-3, -3",
            "+3, 3",
            "--3, 3"
    })
    public void testNumber(String expression, String expected) {
        assertEquals(expected, new RegexCalculator().calculate(expression));
    }

    @ParameterizedTest
    @CsvSource({
            "3+2, 5",
            "2-4, -2",
            "-3+2, -1"
    })
    public void testSimpleSumAndDifference(String expression, String expected) {
        assertEquals(expected, new RegexCalculator().calculate(expression));
    }

    @ParameterizedTest
    @CsvSource({
            "3*2, 6",
            "-3*2, -6",
            "3*-2, -6",
            "4/-2, -2",
            "4/-2, -2",
            "-4÷2, -2",
            "-4÷2, -2",
            "5*6/3, 10"
    })
    public void testSimpleMultiplicationAndDivision(String expression, String expected) {
        assertEquals(expected, new RegexCalculator().calculate(expression));
    }

    @ParameterizedTest
    @CsvSource({
            "6/4, 1.5",
            "6÷4, 1.5",
            "-6/4, -1.5",
            "6/-2.5, -2.4",
            "6÷-2.5, -2.4",
            "-6/-2.5, 2.4"
    })
    public void testDivisionWithDecimals(String expression, String expected) {
        String actual = new RegexCalculator().calculate(expression).replace(',', '.');
        assertEquals(expected, actual);
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            new RegexCalculator().calculate("6/0");
        });
    }

    @ParameterizedTest
    @CsvSource({
            "5+2-3, 4",
            "(5+2)-3, 4",
            "5+(2-3), 4",
            "(5+2)-(2+3), 2",
            "5*(2-3)+7, 2",
            "5+(2-3)*7, -2",
            "5+(2-3)*7-2*-3, 4",
            "2-3*-2, 8",
            "5+(2-3*-2), 13",
            "5+(2-3/-2), 8.5"
    })
    public void testComplicatedExpressions(String expression, String expected) {
        String actual = new RegexCalculator().calculate(expression).replace(',', '.');
        assertEquals(expected, actual);
    }

    @Test
    public void testComplicatedExpressionWithDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            new RegexCalculator().calculate("5+(2-3)/0");
        });
    }
}
