package org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader;

import org.example.DataBase.Database;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetXMLReaderCommand implements Command {
    @Override
    public void execute() {
        Database database = Database.getInstance();
        database.setInputFileFormat("xml");
    }
}
