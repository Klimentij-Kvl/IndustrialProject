package org.example.DataProcessor.RegexProcessor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegexProcessorTest {
    RegexProcessor regexProcessor = new RegexProcessor();
    @Test

    void testProcess() {
        assertEquals(List.of("Разность -2 и -10"," 2 нас удивила."),regexProcessor.process(List.of("Разность -5+3 и -10"," - (-2) нас удивила.")));
        assertEquals(List.of("Сегодня мы решали"," 15 , и это оказалось просто."),regexProcessor.process(List.of("Сегодня мы решали"," 10 + 5, и это оказалось просто.")));
    }

    @Test
    void testExtractExpressions() {
        List<TestCase> testCases = List.of(
                new TestCase(List.of("Сегодня мы решали 10 + 5, и это оказалось просто."), List.of("10+5")),
                new TestCase(List.of("Результат 12 * 2"," и 30 ÷ 5 был рассчитан за несколько минут."), List.of("12*2", "30÷5")),
                new TestCase(List.of("Чтобы найти общий результат,"," нужно было вычислить )((7 + 8) * 2."), List.of("(7+8)*2")),
                new TestCase(List.of("Выражение 5 * (3 + 2) - 7 ÷ (1 + 1) оказалось сложным."), List.of("5*(3+2)-7÷(1+1)")),
                new TestCase(List.of("Для точного результата 22/7 использовалось как приближение числа π."), List.of("22/7")),
                new TestCase(List.of("Разность -5 + 3 и -10"," - (-2) нас удивила."), List.of("-5+3","2")),
                new TestCase(List.of("Рассчитайте ((2 + 3) * (5 - 3)) / 4."), List.of("((2+3)*(5-3))/4")),
                new TestCase(List.of("Сегодня мы говорили о математике, но не решали никаких уравнений."), List.of()),
                new TestCase(List.of("Сложное вычисление 12 - 5 + (3 * 2) / 4 - 1."), List.of("12-5+(3*2)/4-1")),
                new TestCase(List.of("Рассчитайте    10   +    2    *   (  5 -  1 )"), List.of("10+2*(5-1)")),
                new TestCase(List.of("Разность -2 и -10"," - (-2) нас удивила."),List.of("2"))
        );

        for (TestCase testCase : testCases) {
            assertEquals(
                    testCase.expected,
                    FindExpression.find(testCase.input),
                    "Ошибка с примером \"" + testCase.input + "\"");
        }
    }

    @Test
    void calculateExpressions() {
    }

    @Test
    void replaceExpressionsInData() {
    }

    private static class TestCase {
        List<String> input;
        List<String> expected;

        TestCase(List<String> input, List<String> expected) {
            this.input = input;
            this.expected = expected;
        }
    }
}
