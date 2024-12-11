package org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader;

import org.example.DataBase.DataStorage;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetYAMLReaderCommand implements Command {
    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.setInputFileFormat("yaml");
    }
}
