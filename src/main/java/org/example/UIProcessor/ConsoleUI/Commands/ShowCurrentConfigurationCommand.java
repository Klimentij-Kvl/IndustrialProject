package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;

public class ShowCurrentConfigurationCommand implements Command{
    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();
        System.out.println();
        System.out.println("Current configuration:\n" +
                "-------Input-------\n" +
                "Filename: " + dataStorage.getInputFileName() +"\n" +
                "FileFormat: " + dataStorage.getInputFileFormat() +"\n" +
                "-------Output--------\n" +
                "Filename: " + dataStorage.getOutputFileName() + "\n" +
                "FileFormat: " + dataStorage.getOutputFileFormat());
    }
}
