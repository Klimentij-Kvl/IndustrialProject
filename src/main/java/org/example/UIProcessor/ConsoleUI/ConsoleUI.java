package org.example.UIProcessor.ConsoleUI;

import org.example.UIProcessor.ConsoleUI.Menus.MainMenu;
import org.example.UIProcessor.UIProcessor;

public class ConsoleUI implements UIProcessor {
    @Override
    public void start() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.handleInput();
    }
    @Override
    public void stop() {
    }
    @Override
    public String getInputFile() {
        return "";
    }

    @Override
    public String getOutputFile() {
        return "";
    }

}

