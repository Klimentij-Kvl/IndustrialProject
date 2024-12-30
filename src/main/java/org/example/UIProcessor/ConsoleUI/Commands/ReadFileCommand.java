package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.IOException;

public class ReadFileCommand implements Command {
    @Override
    public void execute(){
        DataStorage dataStorage = DataStorage.getInstance();

        if (dataStorage.getInputFileFormat() != null) {
            try(DiffReader dr = new TxtDiffFileReader(dataStorage.getInputFileName() + dataStorage.getInputFileFormat());) {

                dataStorage.setInput(dr.read());
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
