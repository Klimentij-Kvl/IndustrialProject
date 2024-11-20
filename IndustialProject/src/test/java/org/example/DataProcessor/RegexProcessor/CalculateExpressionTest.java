package org.example.DataProcessor.RegexProcessor;

import org.junit.jupiter.api.Test;
import java.beans.Expression;
import static org.junit.jupiter.api.Assertions.*;

class CalculateExpressionTest {


    @Test
    void testSignetCymbol(){
        Number plusnumber = new Number("+5");
        Number minusnumber = new Number("-5");
        Number keknumber = new Number("5");
        assertEquals(5, plusnumber.getNumber());
        assertEquals(-5,minusnumber.getNumber());
        assertEquals(5,keknumber.getNumber());

        /*Number four = new Number("4");
        assertEquals("10", four.Plus("6"));*/
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
}