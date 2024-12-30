package org.example.FileProcessor.DiffWriter.DiffFileWriter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class YamlDiffFileWriter extends SerializationAdapterDiffFileWriter {

    public YamlDiffFileWriter(File file) throws IOException {
        super(file, new YAMLMapper());
    }

    public YamlDiffFileWriter(String fileName) throws IOException {
        super(fileName, new YAMLMapper());
    }
}
