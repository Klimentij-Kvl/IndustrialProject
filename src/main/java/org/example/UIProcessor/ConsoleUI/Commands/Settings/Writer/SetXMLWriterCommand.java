package org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer;

import org.example.DataBase.DataStorage;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetXMLWriterCommand implements Command {
    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.setOutputFileFormat("xml");
    }
}
