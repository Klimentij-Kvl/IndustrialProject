package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;

public class ShowCurrentConfigurationCommand implements Command{
    @Override
    public void execute() {
        Database database = Database.getInstance();
        System.out.println();
        System.out.println("Current configuration:\n" +
                "-------Input-------\n" +
                "Filename: " + database.getInputFileName() +"\n" +
                "FileFormat: " + database.getInputFileFormat() +"\n" +
                "-------Output--------\n" +
                "Filename: " + database.getOutputFileName() + "\n" +
                "FileFormat: " + database.getOutputFileFormat());
    }
}
