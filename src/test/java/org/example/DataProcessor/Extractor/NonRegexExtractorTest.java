package org.example.DataProcessor.Extractor;

import org.example.DataProcessor.Extractor.NonRegexExtractor.NonRegexExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NonRegexExtractorTest {
    private final NonRegexExtractor nre = new NonRegexExtractor();
    
    private static class TestCase {
        List<String> input;
        List<String> expected;

        TestCase(List<String> input, List<String> expected) {
            this.input = input;
            this.expected = expected;
        }
    }

    @Test
    void testExtractExpressions() {
        List<TestCase> testCases = List.of(
                new TestCase(List.of("Сегодня мы решали 10+5, и это оказалось просто."), List.of("10+5")),
                new TestCase(List.of("Результат 12 * 2 + 3"," и 30 ÷ 5 был рассчитан за несколько минут."), List.of("12*2+3", "30÷5")),
                new TestCase(List.of("Для точного результата 22/7 использовалось как приближение числа π."), List.of("22/7")),
                new TestCase(List.of("Сегодня мы говорили о математике, но не решали никаких уравнений."), List.of()),
                new TestCase(List.of("Выражение 5 * (3 + 2) - 7 ÷ (1 + 1) оказалось сложным."), List.of("5*(3+2)-7÷(1+1)")),
                new TestCase(List.of("10+5 11+3"), List.of("10+511+3"))
        );

        for (TestCase testCase : testCases) {
            assertEquals(
                    testCase.expected,
                    nre.extract(testCase.input),
                    "Ошибка с примером \"" + testCase.input + "\"");
        }
    }
}
