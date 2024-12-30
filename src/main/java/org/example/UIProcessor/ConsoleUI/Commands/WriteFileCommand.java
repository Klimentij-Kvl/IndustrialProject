package org.example.UIProcessor.ConsoleUI.Commands;

import org.example.DataBase.DataStorage;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.JsonDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.XmlDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.YamlDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.File;
import java.io.IOException;

public class WriteFileCommand implements Command {
    private final String PATH_RES = "src/resources/";

    @Override
    public void execute() {
        DataStorage dataStorage = DataStorage.getInstance();

        if (dataStorage.getInputFileFormat() != null) {
            try (DiffWriter dr = createWriter(dataStorage.getInputFileFormat(), dataStorage.getOutputFileName())) {
                dr.write(dataStorage.getOutput());
                System.out.println("\nFile successfully write");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("\nNo Output file format specified");
        }
    }

    private DiffWriter createWriter(String format, String fileName) throws IOException {
        return switch (format.toLowerCase()) {
            case "txt" -> new TxtDiffFileWriter(PATH_RES + fileName + "." + format);
            case "yaml" -> new YamlDiffFileWriter(PATH_RES + fileName + "." + format);
            case "json" -> new JsonDiffFileWriter(PATH_RES + fileName + "." + format);
            case "xml" -> new XmlDiffFileWriter(PATH_RES + fileName + "." + format);
            default -> throw new IllegalArgumentException("Unsupported file format: " + format);
        };
    }
}
