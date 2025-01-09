package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.DataProcessor.Calculator.CalculateExpression;
import org.example.DataProcessor.Extractor.RegexExtractor.RegexExtractor;
import org.example.DataProcessor.Replacer.RegexReplacer;
import org.example.DataProcessorFactory;

public class CalculateCommand implements Command {
    @Override
    public void execute() {
        DataProcessorFactory calculator = new DataProcessorFactory(
                new RegexExtractor(), new RegexReplacer(), new CalculateExpression());
        DataStorage dataStorage = DataStorage.getInstance();
        if (dataStorage.getInput() != null) {
            dataStorage.setOutput(calculator.process(dataStorage.getInput()));
            System.out.println("All expressions are calculated.");
        }
        else {
            System.out.println("File note selected or empty.");
        }
    }
}
