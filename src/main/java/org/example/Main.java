package org.example;

import org.example.DataBase.DataStorage;
import org.example.UIProcessor.ConsoleUI.ConsoleUI;
import org.example.UIProcessor.UIProcessor;

public class Main {
    public static void main(String[] args) {
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.setInputFileName("input");
        dataStorage.setOutputFileName("output");
        dataStorage.setInputFileFormat("yaml");
        dataStorage.setOutputFileFormat("yaml");
        UIProcessor ui = new ConsoleUI();
        ui.start();
    }
}