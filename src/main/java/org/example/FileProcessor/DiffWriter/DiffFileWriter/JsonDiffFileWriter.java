package org.example.FileProcessor.DiffWriter.DiffFileWriter;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class JsonDiffFileWriter extends SerializationAdapterDiffFileWriter {

    public JsonDiffFileWriter(File file) throws IOException {
        super(file, new JsonMapper());
    }

    public JsonDiffFileWriter(String fileName) throws IOException {
        super(fileName, new JsonMapper());
    }
}