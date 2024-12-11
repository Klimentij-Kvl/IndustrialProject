package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.FileProcessor.DiffFileWriter;

import java.io.IOException;

public class WriteFIleCommand implements Command {
    @Override
    public void execute() {
        Database database = Database.getInstance();

        if (database.getOutputFileFormat() != null) {
            DiffFileWriter diffFileWriter = new DiffFileWriter(database.getOutputFileName(), database.getOutputFileFormat());
            try {
                diffFileWriter.write(database.getOutput());
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
