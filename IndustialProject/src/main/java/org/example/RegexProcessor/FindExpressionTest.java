package org.example.RegexProcessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class FindExpressionTest {
    String input1 = "Бим б, 1 + 5 Бом";
    String input2 = "Бара + 2 / 3 + бэм" +
            "ля-ля 8 / 6 + 1 Валера 22 + 2";
    String input3 = "т(ы5*3в";

    @Test
    void testFindComputableExpression() {
        List<String> expected1 = Arrays.asList(" 1 + 5 ");
        assertEquals(expected1, FindExpression.findComputableExpressions(input1), "Ошибка с примером \"" + input1 + "\"");

        List<String> expected2 = Arrays.asList(" + 2 / 3 ", " 8 / 6 + 1 ", " 22 + 2");
        assertEquals(expected2, FindExpression.findComputableExpressions(input2), "Ошибка с примером \"" + input2 + "\"");
    }

    @Test
    void testDeleteSpaces() {
        assertEquals("Бимб,1+5Бом", FindExpression.deleteSpaces(input1));
        assertEquals("Бара+2/3+бэмля-ля8/6+1Валера22+2", FindExpression.deleteSpaces(input2));
        assertEquals("т(ы5*3в", FindExpression.deleteSpaces(input3));
    }

    @ParameterizedTest
    @CsvSource({
            "'(10)', '10'",
            "'(-11)', '-11'",
            "'(+-9)', '+-9'",
            "'-1', '-1'",
            "'((+9))', '+9'",
            "'(+-)', '+-'"
    })
    void testDeleteSingleObjectBrackets(String input, String expected) {
        assertEquals(expected, FindExpression.deleteSingleObjectBrackets(input));
    }

    @ParameterizedTest
    @CsvSource({
            "(10), [10]",
            "((6)), [[6]]",
            "(6+1)), [6+1])",
            ")(1+2)(, )[1+2]("
    })
    void testReplaceBrackets(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.saveUsefulBrackets(input));
    }

    @ParameterizedTest
    @CsvSource({
            "(10), 10",
            "(())) (,' '",
            "(()),''"
    })
    void testDeleteAllBrackets(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.deleteAllBrackets(input));
    }

    @ParameterizedTest
    @CsvSource({
            "[10], (10)",
            "[[6]], ((6))",
            "[6+1]], (6+1)]",
            "][1+2][, ](1+2)["
    })
    void testReturnUsefulBrackets(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.returnUsefulBrackets(input));
    }

    @ParameterizedTest
    @CsvSource({
            "--, +",
            "-+-, ++",
            "-1-, -1-",
            "-(-, -(-",
            "-*-, -*-"
    })
    void testConvertRedundantMinuses(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.convertRedundantMinuses(input));
    }

    @ParameterizedTest
    @CsvSource({
            "+10, 10",
            "5*+11, 5*11",
            "5*(+11, 5*(11",
            "5*7*+3, 5*7*3",
            "5*(+11, 5*(11"
    })
    void testRemoveUslessPlus(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.removeUslessPlus(input));
    }

    @ParameterizedTest
    @CsvSource({
            "'++10', '+10'",
            "'+10', '+10'",
            "'10++', '10+'",
            "'10+++5', '10+5'",
            "'+5++10', '+5+10'",
            "'+++', '+'",
            "'5', '5'",
            "'-10+10', '-10+10'"
    })
    void testRemoveRedundantPluses(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.removeRedundantPluses(input));
    }
    @ParameterizedTest
    @CsvSource({
            "'10+-5', '10-5'",
            "'-5+3', '-5+3'",
            "'+5*-+10', '+5*-10'",
            "'10-5', '10-5'",
            "'+5+-+10', '+5-10'",
            "'*+-10', '*-10'"
    })
    void testChooseSign(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.chooseSign(input));
    }
}
