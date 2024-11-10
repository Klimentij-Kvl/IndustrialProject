package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class FindExpressionTest {
    String input1 = "Бим-бим, Бам 1 + 5 Бом-Бум";
    String input2 = "Бара-Бери 2 / 3 + бэм бэм" +
            "ля-ля 8 / 6 + 1 Валера 22 + 2";
    String input3 = "тафыотвлофы5*3вфджыбвфжыд";

    @Test
    void testFindExpression() {
        List<String> expected1 = Arrays.asList("1+5");
        assertEquals(expected1, FindExpression.Find(input1), "Ошибка с примером \"" + input1 + "\"");

        List<String> expected2 = Arrays.asList("2/3", "8/6+1", "22+2");
        assertEquals(expected2, FindExpression.Find(input2), "Ошибка с примером \"" + input2 + "\"");
    }
}
