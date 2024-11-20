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

        assertEquals("5",plusnumber.CreateNumber());
        assertEquals("-5",minusnumber.CreateNumber());
        assertEquals("5",keknumber.CreateNumber());
    }

}