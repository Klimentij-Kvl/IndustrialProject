package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.DataProcessor.DataProcessor;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;

public class CalculateCommand implements Command {
    @Override
    public void execute() {
        DataProcessor calculator = new RegexProcessor();
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
