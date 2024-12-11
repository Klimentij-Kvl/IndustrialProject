package org.example.UIProcessor.ConsoleUI.Commands.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Command;
import org.example.UIProcessor.ConsoleUI.Menus.Settings.CalculatorSettingsMenu;

public class SetCalculatorSettingsMenuCommand implements Command {
    @Override
    public void execute() {
        CalculatorSettingsMenu calculatorSettingsMenu = new CalculatorSettingsMenu();
        calculatorSettingsMenu.handleInput();
    }
}
