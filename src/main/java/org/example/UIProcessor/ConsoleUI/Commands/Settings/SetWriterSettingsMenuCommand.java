package org.example.UIProcessor.ConsoleUI.Commands.Settings;

import org.example.UIProcessor.ConsoleUI.Commands.Command;
import org.example.UIProcessor.ConsoleUI.Menus.Settings.WriterSettingsMenu;

public class SetWriterSettingsMenuCommand implements Command {
    @Override
    public void execute() {
        WriterSettingsMenu writerSettingsMenu = new WriterSettingsMenu();
        writerSettingsMenu.handleInput();
    }
}
