package org.example.UIProcessor.ConsoleUI.Menus;

import org.example.UIProcessor.ConsoleUI.Commands.Settings.SetCalculatorSettingsMenuCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.SetReaderSettingsMenuCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.SetWriterSettingsMenuCommand;

public class SettingsMenu extends Menu{
    public SettingsMenu(){
        options.put(1, new SetReaderSettingsMenuCommand());
        options.put(2, new SetWriterSettingsMenuCommand());
        options.put(3, new SetCalculatorSettingsMenuCommand());
    }
    @Override
    protected String getMenuHeader() {
        return "--------Setting Menu--------";
    }

    @Override
    protected String getOptionName(int optionKey) {
        return switch (optionKey) {
            case 1 -> "Reader Settings";
            case 2 -> "Writer Settings";
            case 3 -> "Calculator Settings";
            default -> "Unknown Option";
        };
    }
}
