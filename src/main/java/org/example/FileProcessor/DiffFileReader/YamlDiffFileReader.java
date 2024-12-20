package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class YamlDiffFileReader extends SerializationDiffFileReader{
    public YamlDiffFileReader(InputStream is){
        super(is, new YAMLMapper());
    }

    public YamlDiffFileReader(File file) throws IOException {
        super(file, new YAMLMapper());
    }

    public YamlDiffFileReader(String fileName) throws IOException {
        super(fileName, new YAMLMapper());
    }
}
