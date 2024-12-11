package org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer;

import org.example.DataBase.Database;
import org.example.UIProcessor.ConsoleUI.Commands.Command;

public class SetXMLWriterCommand implements Command {
    @Override
    public void execute() {
        Database database = Database.getInstance();
        database.setOutputFileFormat("xml");
    }
}
