package org.example.UIProcessor.ConsoleUI.Menus.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetJSONWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetTXTWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetXMLWriterCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.Writer.SetYAMLWriterCommand;
import org.example.UIProcessor.ConsoleUI.Menus.Menu;

public class WriterSettingsMenu extends Menu {
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
        return switch (optionKey) {
            case 1 -> "txt";
            case 2 -> "json";
            case 3 -> "yaml";
            case 4 -> "xml";
            default -> "Unknown Option";
        };
    }
}
