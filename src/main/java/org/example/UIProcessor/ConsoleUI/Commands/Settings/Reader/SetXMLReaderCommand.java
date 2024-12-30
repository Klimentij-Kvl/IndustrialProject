package org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader;

import org.example.DataBase.DataStorage;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetXMLReaderCommand implements Command {
    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.setInputFileFormat("xml");
    }
}
