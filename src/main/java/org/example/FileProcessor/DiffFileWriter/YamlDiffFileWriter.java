package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.OutputStream;

public class YamlDiffFileWriter extends SerializationDiffFileWriter{
    public YamlDiffFileWriter(OutputStream os){
        super(os, new YAMLMapper());
    }
}
