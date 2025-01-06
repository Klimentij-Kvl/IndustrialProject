package org.example;

import org.example.DataBase.DataStorage;
import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Extracter.Extracter;
import org.example.DataProcessor.Replacer.Replacer;

import java.util.List;

public class DataProcessorFactory {
    private final Extracter extracter;
    private final Replacer replacer;
    private final Calculator calculator;
    private static final DataStorage dataStorage = DataStorage.getInstance();

    public static void addFunction(String functionName, String formula){
        dataStorage.addFunction(functionName, formula);
    }

    public DataProcessorFactory(Extracter extracter, Replacer replacer, Calculator calculator){
        this.extracter = extracter;
        this.replacer = replacer;
        this.calculator = calculator;
    }

    public List<String> process(List<String> rawList){
        List<String> expressions = extracter.extract(rawList);
        expressions = calculator.calculate(expressions);
        return replacer.replace(rawList, expressions);
    }
}
