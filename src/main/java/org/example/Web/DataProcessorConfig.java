package org.example.Web;

import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Calculator.LibraryCalculator;
import org.example.DataProcessor.Calculator.RegexCalculator;
import org.example.DataProcessor.Extractor.Extractor;
import org.example.DataProcessor.Extractor.NonRegexExtractor;
import org.example.DataProcessor.Extractor.RegexExtractor;
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
        "org.example.DataProcessor.Replacer",
        "org.example.DataProcessor.Extractor",
        "org.example.Web"
})
public class DataProcessorConfig {

    @Bean("NonRegexExtractor")
    public Extractor nonRegexExtractor() {
        return new NonRegexExtractor();
    }

    @Bean("RegexExtractor")
    public Extractor regexExtractor() {
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
        return new RegexCalculator();
    }

    @Bean("LibraryCalculator")
    public Calculator calculatorLibrary() {
        return new LibraryCalculator();
    }
}
