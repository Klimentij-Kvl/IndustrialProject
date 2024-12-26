package org.example.FileProcessor.DiffReader.DiffFileReader;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class YamlDiffFileReader extends SerializationDiffFileReader{
    public YamlDiffFileReader(File file) throws IOException {
        super(file, new YAMLMapper());
    }

    public YamlDiffFileReader(String fileName) throws IOException {
        super(fileName, new YAMLMapper());
    }
}
