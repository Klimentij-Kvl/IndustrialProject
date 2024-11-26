package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.Database;
import org.example.FileProcessor.DiffFileReader.*;

import java.io.IOException;
import java.io.StringReader;

public class ReadFileCommand implements Command {
    @Override
    public void execute(){
        Database database = Database.getInstance();

        String inputFormat = database.getInputFileFormat();
        if (inputFormat == null) {
            System.out.println("Input file format is not set.");
            return;
        }

        String filePath = "src/resources/" + database.getInputFileName() + "." + inputFormat;

        try {
            DiffFileReader reader = createReader(inputFormat);
            if (reader == null) {
                System.out.println("Unsupported input format: " + inputFormat);
                return;
            }

            database.setInput(reader.read(filePath));
            System.out.println("File successfully read.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    private DiffFileReader createReader(String format) {
        return switch (format.toLowerCase()) {
            case "txt" -> new TextFileReader();
            case "json" -> new JsonFileReader();
            case "xml" -> new XmlFileReader();
            case "yaml" -> new YamlFileReader();
            default -> null;
        };
    }
}
