package org.example.UIProcessor.ConsoleUI.Menus;

import org.example.UIProcessor.ConsoleUI.Commands.CalculateCommand;
import org.example.UIProcessor.ConsoleUI.Commands.ReadFileCommand;
import org.example.UIProcessor.ConsoleUI.Commands.Settings.SetSettingsMenuCommand;
import org.example.UIProcessor.ConsoleUI.Commands.ShowCurrentConfigurationCommand;
import org.example.UIProcessor.ConsoleUI.Commands.WriteFIleCommand;

public class MainMenu extends Menu {
    public MainMenu() {
        options.put(1, new ReadFileCommand());
        options.put(2, new WriteFIleCommand());
        options.put(3, new CalculateCommand());
        options.put(4, new SetSettingsMenuCommand());
        options.put(5, new ShowCurrentConfigurationCommand());
    }

    @Override
    protected String getMenuHeader() {
        return "--------Main Menu--------";
    }

    @Override
    protected String getOptionName(int optionKey) {
        return switch (optionKey) {
            case 1 -> "Read File";
            case 2 -> "Write File";
            case 3 -> "Calculate";
            case 4 -> "Settings";
            case 5 -> "Show Current Configuration";
            default -> "Unknown Option";
        };
    }
}
