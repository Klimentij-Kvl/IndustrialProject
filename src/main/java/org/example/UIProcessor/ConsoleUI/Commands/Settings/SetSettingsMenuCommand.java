package org.example.UIProcessor.ConsoleUI.Commands.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Command;
import org.example.UIProcessor.ConsoleUI.Menus.SettingsMenu;

public class SetSettingsMenuCommand implements Command {
    @Override
    public void execute() {
        SettingsMenu settingsMenu = new SettingsMenu();
        settingsMenu.handleInput();
    }
}
