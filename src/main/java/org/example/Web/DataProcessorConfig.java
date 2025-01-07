package org.example.Web;

import org.example.DataProcessor.Calculator.CalculateExpression;
import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Calculator.LibraryCalculator;
import org.example.DataProcessor.Extracter.Extracter;
import org.example.DataProcessor.Extracter.NonRegexExtractor.NonRegexExtractor;
import org.example.DataProcessor.Extracter.RegexExtracter.RegexExtractor;
import org.example.DataProcessor.Replacer.NonRegexReplacer;
import org.example.DataProcessor.Replacer.RegexReplacer;
import org.example.DataProcessor.Replacer.Replacer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для DataProcessor и связанных пакетов.
 */
@Configuration
@ComponentScan(basePackages = {
        "org.example.DataProcessor.Calculator",
        "org.example.DataProcessor.Extracter.NonRegexExtractor",
        "org.example.DataProcessor.Extracter.RegexExtracter",
        "org.example.DataProcessor.Replacer",
        "org.example.Web"
})
public class DataProcessorConfig {

    @Bean("NonRegexExtractor")
    public Extracter nonRegexExtractor() {
        return new NonRegexExtractor();
    }

    @Bean("RegexExtractor")
    public Extracter regexExtractor() {
        return new RegexExtractor();
    }

    @Bean("NonRegexReplacer")
    public Replacer nonRegexReplacer() {
        return new NonRegexReplacer();
    }

    @Bean("RegexReplacer")
    public Replacer regexReplacer() {
        return new RegexReplacer();
    }

    @Bean("RegexCalculator")
    public Calculator calculator() {
        return new CalculateExpression();
    }

    @Bean("LibraryCalculator")
    public Calculator calculatorLibrary() {
        return new LibraryCalculator();
    }
}
