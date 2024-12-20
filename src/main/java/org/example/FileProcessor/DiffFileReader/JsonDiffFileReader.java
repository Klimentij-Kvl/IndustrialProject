package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JsonDiffFileReader extends SerializationDiffFileReader{
    public JsonDiffFileReader(InputStream is){
        super(is, new JsonMapper());
    }

    public JsonDiffFileReader(File file) throws IOException {
        super(file, new JsonMapper());
    }

    public JsonDiffFileReader(String fileName) throws IOException {
        super(fileName, new JsonMapper());
    }
}
