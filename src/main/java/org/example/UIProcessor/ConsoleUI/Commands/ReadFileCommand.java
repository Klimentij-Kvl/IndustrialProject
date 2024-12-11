package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.FileProcessor.DiffFileReader;

import java.io.IOException;

public class ReadFileCommand implements Command {
    @Override
    public void execute(){
        DataStorage dataStorage = DataStorage.getInstance();

        if (dataStorage.getInputFileFormat() != null) {
            DiffFileReader diffFileReader = new DiffFileReader(dataStorage.getInputFileName(), dataStorage.getInputFileFormat());
            try {
                dataStorage.setInput(diffFileReader.read());
                System.out.println("\nFile successfully read");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("No Input file format specified");
        }
    }
}
