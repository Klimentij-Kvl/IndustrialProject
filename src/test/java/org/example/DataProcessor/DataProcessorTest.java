package org.example.DataProcessor;

import org.example.DataProcessor.Calculator.RegexCalculator;
import org.example.DataProcessor.Extractor.RegexExtractor;
import org.example.DataProcessor.Replacer.RegexReplacer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class DataProcessorTest {
    private DataProcessorFactory dataProcessor;

    @Test
    void RegexProcessTest(){
        dataProcessor = new DataProcessorFactory(
                new RegexExtractor(), new RegexReplacer(), new RegexCalculator());
        
        DataProcessorFactory.addFunction("abort", "x+y");
        assertEquals(List.of("Сумма 3 "),dataProcessor.process(List.of("Сумма abort(1,2)")));
        assertEquals(List.of("Разность -2 и -10"," 2 нас удивила."),dataProcessor.process(List.of("Разность -5+3 и -10"," - (-2) нас удивила.")));
        assertEquals(List.of("Рассчитайте 2,5 ."),dataProcessor.process(List.of("Рассчитайте ((2 + 3) * (5 - 3)) / 4.")));
        assertEquals(List.of("roflan"),dataProcessor.process(List.of("roflan")));
        assertEquals(List.of("Сегодня мы решали"," 15 , и это оказалось просто."),dataProcessor.process(List.of("Сегодня мы решали"," 10 + 5, и это оказалось просто.")));    }
}
