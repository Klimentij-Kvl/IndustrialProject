package org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader;

import org.example.DataBase.Database;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetJSONReaderCommand implements Command {
    @Override
    public void execute() {
        Database database = Database.getInstance();
        database.setInputFileFormat("json");
    }
}
