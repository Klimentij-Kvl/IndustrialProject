package org.example.UIProcessor.ConsoleUI.Menus.Settings;

import org.example.DataBase.Database;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetJSONWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetTXTWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetXMLWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetYAMLWriterCommand;
import org.example.UIProcessor.ConsoleUI.Menus.Menu;

public class WriterSettingsMenu extends Menu {
    private static final String COLOR_GREEN = "\u001B[32m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";
    public WriterSettingsMenu() {
        options.put(1, new SetTXTWriterCommand());
        options.put(2, new SetJSONWriterCommand());
        options.put(3, new SetYAMLWriterCommand());
        options.put(4, new SetXMLWriterCommand());
    }
    @Override
    protected String getMenuHeader() {
        return "";
    }

    @Override
    protected String getOptionName(int optionKey) {
        Database db = Database.getInstance();
        String optionName = switch (optionKey) {
            case 1 -> "txt";
            case 2 -> "json";
            case 3 -> "yaml";
            case 4 -> "xml";
            default -> "Unknown Option";
        };

        if (optionName.equalsIgnoreCase(db.getOutputFileFormat())) {
            return COLOR_GREEN + optionName + COLOR_RESET;
        } else {
            return COLOR_RED + optionName + COLOR_RESET;
        }
    }
}
