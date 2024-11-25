package org.example.DataProcessor.RegexProcessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class FindExpressionTest {

    @Test
    void testFindComputableExpression() {
        String input1 = "Бим б, 1 + 5 Бом";
        List<String> expected1 = List.of(" 1 + 5 ");
        assertEquals(expected1, FindExpression.findComputableExpressions(input1), "Ошибка с примером \"" + input1 + "\"");

        String input2 = "Бара + 2 / 3 + бэмля-ля 8 / 6 + 1 Валера 22 + 2";
        List<String> expected2 = asList(" + 2 / 3 ", " 8 / 6 + 1 ", " 22 + 2");
        assertEquals(expected2, FindExpression.findComputableExpressions(input2), "Ошибка с примером \"" + input2 + "\"");

        String input3 = "т(ы5*3в";
        List<String> expected3 = List.of("5*3");
        assertEquals(expected3, FindExpression.findComputableExpressions(input3), "Ошибка с примером \"" + input3 + "\"");

        String input4 = "3 +++ 5 --- 2";
        List<String> expected4 = List.of("3 +++ 5 --- 2");
        assertEquals(expected4, FindExpression.findComputableExpressions(input4), "Ошибка с примером \"" + input4 + "\"");

        String input5 = " 10 * ((3 + 5) / 2 ";
        List<String> expected5 = List.of(" 10 * ((3 + 5) / 2 ");
        assertEquals(expected5, FindExpression.findComputableExpressions(input5), "Ошибка с примером \"" + input5 + "\"");

        String input6 = "1 + 2 - 3 * 4 / 5 + 6";
        List<String> expected6 = List.of("1 + 2 - 3 * 4 / 5 + 6");
        assertEquals(expected6, FindExpression.findComputableExpressions(input6), "Ошибка с примером \"" + input6 + "\"");
    }

    @ParameterizedTest
    @CsvSource({
            "1 3 5,135",
            "qwerty,qwerty",
            "1    +     5,1+5"
    })
    void testDeleteSpaces(String input, String expected) {
        assertEquals(expected, FindExpression.deleteSpaces(input));
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
            "10+5,10+5",
            "1+1++1,1+1+1",
            "5*+11, 5*11",
            "5*(+11, 5*(11",
            "5*7*+3, 5*7*3",
            "5*(+11, 5*(11"
    })
    void testRemoveUselessPlus(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.removeUselessPlus(input));
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

    @ParameterizedTest
    @CsvSource({
            "'10+5', '10+5'",
            "'-(53)', '-(53)'",
            "'(5+6)(7-10)', '(5+6)*(7-10)'",
            "'(7-(5+6))(7-10)(8+3/2)', '(7-(5+6))*(7-10)*(8+3/2)'"
    })
    void testAddMulBetweenBrackets(String input, String expectedOutput) {
        assertEquals(expectedOutput, FindExpression.addMulBetweenBrackets(input));
    }

    @Test
    void testFind() {
        List<String> input1 = asList("Бим б,  (-3/5)(7+8) Бом","Парарам");
        List<String> expected1 = List.of("(-3/5)*(7+8)");
        assertEquals(expected1, FindExpression.find(input1), "Ошибка с примером \"" + input1 + "\"");

        List<String> input2 = asList("Бара + 2 / 3 + бэмля-ля"," 8 / 6 + 1 Валера 22 + 2");
        List<String> expected2 = asList("2/3", "8/6+1", "22+2");
        assertEquals(expected2, FindExpression.find(input2), "Ошибка с примером \"" + input2 + "\"");

        List<String> input3 = List.of("Проверяй, (3+(3* 8) должно быть изменено на двадцать семь");
        List<String> expected3 = List.of("3+(3*8)");
        assertEquals(expected3, FindExpression.find(input3), "Ошибка с примером \"" + input3 + "\"");

        List<String> input4 = List.of("*- 3 +++ 5 -+-- 2)");
        List<String> expected4 = List.of("-3+5-2");
        assertEquals(expected4, FindExpression.find(input4), "Ошибка с примером \"" + input4 + "\"");

        List<String> input5 = List.of(") (+10) * ((3 + 5) / 2 ");
        List<String> expected5 = List.of("10*(3+5)/2");
        assertEquals(expected5, FindExpression.find(input5), "Ошибка с примером \"" + input5 + "\"");
    }
}
