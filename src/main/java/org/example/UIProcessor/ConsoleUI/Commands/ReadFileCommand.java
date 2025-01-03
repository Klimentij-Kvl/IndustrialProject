package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.FileProcessor.DiffReader.DiffFileReader.JsonDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffFileReader.XmlDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffFileReader.YamlDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.IOException;

public class ReadFileCommand implements Command {
    private final String PATH_RES = "src/resources/";

    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();

        if (dataStorage.getInputFileFormat() != null) {
            try (DiffReader dr = createReader(dataStorage.getInputFileFormat(), dataStorage.getInputFileName())) {
                dataStorage.setInput(dr.read());
                System.out.println("\nFile successfully read");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("\nNo Input file format specified");
        }
    }

    private DiffReader createReader(String format, String fileName) throws IOException {
        return switch (format.toLowerCase()) {
            case "txt" -> new TxtDiffFileReader(PATH_RES + fileName + "." + format);
            case "yaml" -> new YamlDiffFileReader(PATH_RES + fileName + "." + format);
            case "json" -> new JsonDiffFileReader(PATH_RES + fileName + "." + format);
            case "xml" -> new XmlDiffFileReader(PATH_RES + fileName + "." + format);
            default -> throw new IllegalArgumentException("Unsupported file format: " + format);
        };
    }
}
