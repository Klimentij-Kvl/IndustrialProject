package org.example.UIProcessor.ConsoleUI.Menus.Settings;

import org.example.DataBase.DataStorage;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetJSONReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetTXTReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetXMLReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetYAMLReaderCommand;
import org.example.UIProcessor.ConsoleUI.Menus.Menu;

public class ReaderSettingsMenu extends Menu {
    private static final String COLOR_GREEN = "\u001B[32m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";

    public ReaderSettingsMenu() {
        options.put(1, new SetTXTReaderCommand());
        options.put(2, new SetJSONReaderCommand());
        options.put(3, new SetYAMLReaderCommand());
        options.put(4, new SetXMLReaderCommand());
    }
    @Override
    protected String getMenuHeader() {
        return "-----Reader Settings-----";
    }

    @Override
    protected String getOptionName(int optionKey) {
        DataStorage db = DataStorage.getInstance();
        String optionName = switch (optionKey) {
            case 1 -> "txt";
            case 2 -> "json";
            case 3 -> "yaml";
            case 4 -> "xml";
            default -> "Unknown Option";
        };

        if (optionName.equalsIgnoreCase(db.getInputFileFormat())) {
            return COLOR_GREEN + optionName + COLOR_RESET;
        } else {
            return COLOR_RED + optionName + COLOR_RESET;
        }
    }
}
