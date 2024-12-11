package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.FileProcessor.DiffFileWriter;

import java.io.IOException;

public class WriteFIleCommand implements Command {
    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();

        if (dataStorage.getOutputFileFormat() != null) {
            DiffFileWriter diffFileWriter = new DiffFileWriter(dataStorage.getOutputFileName(), dataStorage.getOutputFileFormat());
            try {
                diffFileWriter.write(dataStorage.getOutput());
                System.out.println("\nFile successfully write");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("No output file format specified");
        }
    }
}
