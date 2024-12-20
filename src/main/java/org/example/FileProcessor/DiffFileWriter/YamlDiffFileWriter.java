package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class YamlDiffFileWriter extends SerializationDiffFileWriter{
    public YamlDiffFileWriter(OutputStream os){
        super(os, new YAMLMapper());
    }

    public YamlDiffFileWriter(File file) throws IOException {
        super(file, new YAMLMapper());
    }

    public YamlDiffFileWriter(String fileName) throws IOException {
        super(fileName, new YAMLMapper());
    }
}
