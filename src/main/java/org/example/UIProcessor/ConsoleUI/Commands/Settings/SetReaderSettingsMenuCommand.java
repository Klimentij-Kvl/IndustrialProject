package org.example.UIProcessor.ConsoleUI.Commands.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Command;
import org.example.UIProcessor.ConsoleUI.Menus.Settings.ReaderSettingsMenu;

public class SetReaderSettingsMenuCommand implements Command {
    @Override
    public void execute() {
        ReaderSettingsMenu readerSettingsMenu = new ReaderSettingsMenu();
        readerSettingsMenu.handleInput();
    }
}
