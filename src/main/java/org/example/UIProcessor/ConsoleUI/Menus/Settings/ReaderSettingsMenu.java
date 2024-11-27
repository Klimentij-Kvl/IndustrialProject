package org.example.UIProcessor.ConsoleUI.Menus.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetJSONReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetTXTReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetXMLReaderCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Reader.SetYAMLReaderCommand;
import org.example.UIProcessor.ConsoleUI.Menus.Menu;

public class ReaderSettingsMenu extends Menu {
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
        return switch (optionKey) {
            case 1 -> "txt";
            case 2 -> "json";
            case 3 -> "yaml";
            case 4 -> "xml";
            default -> "Unknown Option";
        };
    }
}
