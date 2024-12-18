package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.InputStream;

public class JsonDiffFileReader extends SerializationDiffFileReader{
    public JsonDiffFileReader(InputStream is){
        super(is, new JsonMapper());
    }
}
