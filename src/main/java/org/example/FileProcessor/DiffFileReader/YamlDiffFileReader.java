package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.InputStream;

public class YamlDiffFileReader extends SerializationDiffFileReader{
    public YamlDiffFileReader(InputStream is){
        super(is, new YAMLMapper());
    }
}
