package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.OutputStream;

public class JsonDiffFileWriter extends SerializationDiffFileWriter{
    public JsonDiffFileWriter(OutputStream os){
        super(os, new JsonMapper());
    }
}
