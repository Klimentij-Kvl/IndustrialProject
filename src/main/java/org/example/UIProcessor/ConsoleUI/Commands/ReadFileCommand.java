package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.IOException;

public class ReadFileCommand implements Command {
    @Override
    public void execute(){
        Database database = Database.getInstance();

        if (database.getInputFileFormat() != null) {
            try(DiffReader dr = new TxtDiffFileReader(database.getInputFileName() + database.getInputFileFormat());) {

                database.setInput(dr.read());
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
