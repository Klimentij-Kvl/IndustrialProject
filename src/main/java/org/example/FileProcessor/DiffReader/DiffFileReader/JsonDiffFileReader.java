package org.example.FileProcessor.DiffReader.DiffFileReader;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class JsonDiffFileReader extends SerializationDiffFileReader{
    public JsonDiffFileReader(File file) throws IOException {
        super(file, new JsonMapper());
    }

    public JsonDiffFileReader(String fileName) throws IOException {
        super(fileName, new JsonMapper());
    }

    @Override
    public String getType(){return "json";}
}
