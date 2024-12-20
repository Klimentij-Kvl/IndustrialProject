package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class JsonDiffFileWriter extends SerializationDiffFileWriter{
    public JsonDiffFileWriter(OutputStream os){
        super(os, new JsonMapper());
    }

    public JsonDiffFileWriter(File file) throws IOException {
        super(file, new JsonMapper());
    }

    public JsonDiffFileWriter(String fileName) throws IOException {
        super(fileName, new JsonMapper());
    }
}