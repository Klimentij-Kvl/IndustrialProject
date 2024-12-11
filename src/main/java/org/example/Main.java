package org.example;

import org.example.DataBase.Database;
import org.example.UIProcessor.ConsoleUI.ConsoleUI;
import org.example.UIProcessor.UIProcessor;

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        database.setInputFileName("input");
        database.setOutputFileName("output");
        database.setInputFileFormat("yaml");
        database.setOutputFileFormat("yaml");
        UIProcessor ui = new ConsoleUI();
        ui.start();
    }
}