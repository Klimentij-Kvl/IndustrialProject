package org.example.DataProcessor;

import org.example.DataBase.DataStorage;
import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Extractor.Extractor;
import org.example.DataProcessor.Replacer.Replacer;

import java.util.List;

public class DataProcessorFactory {
    private final Extractor extractor;
    private final Replacer replacer;
    private final Calculator calculator;

    public static void addFunction(String functionName, String formula){
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.addFunction(functionName, formula);
    }

    public DataProcessorFactory(Extractor extractor, Replacer replacer, Calculator calculator){
        this.extractor = extractor;
        this.replacer = replacer;
        this.calculator = calculator;
    }

    public List<String> process(List<String> rawList){
        List<String> expressions = extractor.extract(rawList);
        expressions = calculator.calculate(expressions);
        return replacer.replace(rawList, expressions);
    }
}
