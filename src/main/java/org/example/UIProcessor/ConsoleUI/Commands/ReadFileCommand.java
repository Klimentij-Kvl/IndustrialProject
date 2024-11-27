package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.FileProcessor.DiffFileReader;

import java.io.IOException;

public class ReadFileCommand implements Command {
    @Override
    public void execute(){
        Database database = Database.getInstance();

        if (database.getInputFileFormat() != null) {
            DiffFileReader diffFileReader = new DiffFileReader(database.getInputFileName(), database.getInputFileFormat());
            try {
                database.setInput(diffFileReader.read());
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
