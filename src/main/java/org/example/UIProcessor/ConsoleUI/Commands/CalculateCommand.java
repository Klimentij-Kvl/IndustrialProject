package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.DataProcessor.DataProcessor;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;

public class CalculateCommand implements Command {
    @Override
    public void execute() {
        DataProcessor calculator = new RegexProcessor();
        Database database = Database.getInstance();
        if (database.getInput() != null) {
            database.setOutput(calculator.process(database.getInput()));
            System.out.println("All expressions are calculated.");
        }
        else {
            System.out.println("File note selected or empty.");
        }
    }
}
