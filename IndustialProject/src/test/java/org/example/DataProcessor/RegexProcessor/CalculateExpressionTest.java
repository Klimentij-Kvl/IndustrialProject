package org.example.DataProcessor.RegexProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.beans.Expression;
import static org.junit.jupiter.api.Assertions.*;

class CalculateExpressionTest {

    @Test
    public void testCalculateSimpleExpression(){
        CalculateSimpleExpression exprSum = new CalculateSimpleExpression("2+3");
        assertEquals(5, exprSum.simpleCalculator());

        CalculateSimpleExpression exprMinus = new CalculateSimpleExpression("2-3");
        assertEquals(-1, exprMinus.simpleCalculator());

        CalculateSimpleExpression exprMul = new CalculateSimpleExpression("2*3");
        assertEquals(6, exprMul.simpleCalculator());

        CalculateSimpleExpression exprMul2 = new CalculateSimpleExpression("2*-3");
        assertEquals(-6, exprMul2.simpleCalculator());

        CalculateSimpleExpression exprDiv = new CalculateSimpleExpression("6/3");
        assertEquals(2, exprDiv.simpleCalculator());

        CalculateSimpleExpression exprDivByO = new CalculateSimpleExpression("6/0");
        assertThrows(ArithmeticException.class,exprDivByO::simpleCalculator);

        CalculateSimpleExpression wrongOperator = new CalculateSimpleExpression("6j3");
        assertThrows(IllegalArgumentException.class,wrongOperator::simpleCalculator);

        CalculateSimpleExpression wrongOperand = new CalculateSimpleExpression("6/j");
        assertThrows(IllegalArgumentException.class,wrongOperand::simpleCalculator);
    }

//Waiting to be DELETED:
/*    @Test
    void testSignetCymbol(){
        Number plusnumber = new Number("+5");
        Number minusnumber = new Number("-5");
        Number keknumber = new Number("5");
        assertEquals(5, plusnumber.getNumber());
        assertEquals(-5,minusnumber.getNumber());
        assertEquals(5,keknumber.getNumber());
    }

    @Test
    void testSimpleAddition(){
        Sum twoplusthree = new Sum("2+3");
        Sum twominusthree = new Sum("2-3");
        Sum threeminustwo = new Sum("3-2");
        Sum minustwominusthree = new Sum("-2-3");
        assertEquals(5,twoplusthree.CalculateSum());
        assertEquals(-1,twominusthree.CalculateSum());
        assertEquals(1,threeminustwo.CalculateSum());
        assertEquals(-5,minustwominusthree.CalculateSum());
    }

    @Test
    public void testMultiplication(){
        Mul twoandthree = new Mul("2*3");
        assertEquals(6,twoandthree.CalculateMul());
    }

    @Test
    public void testDivizion() {
        Div sixbytwo = new Div("6/2");

        assertEquals(3, sixbytwo.CalculateDiv());

    }

    @Test
    public void testDivisionByZero() {
        Div tenbyO = new Div("10/0");

        // Проверяем, что метод вызывает ArithmeticException
        assertThrows(ArithmeticException.class, tenbyO::CalculateDiv);
    }*/
}
