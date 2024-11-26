package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.FileProcessor.DiffFileWriter.DiffFileWriter;

public class WriteFIleCommand implements Command {
    @Override
    public void execute() {
        Database database = Database.getInstance();

        if (database.getOutputFileFormat() != null) {
            DiffFileWriter diffFileWriter = new DiffFileWriter();
            diffFileWriter.setFileNameAndFormat(database.getOutputFileName() + "." + database.getInputFileFormat());
            diffFileWriter.write(database.getOutput());
        }
        else {
            System.out.println("No output file format specified");
        }
    }
}
