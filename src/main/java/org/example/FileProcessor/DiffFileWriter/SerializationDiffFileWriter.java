package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SerializationDiffFileWriter extends DiffFileWriter{

    private final ObjectMapper mapper;

    SerializationDiffFileWriter(OutputStream os, ObjectMapper mapper){
        super(os);
        this.mapper = mapper;
    }

    @Override
    public void write(String s) throws IOException {
        mapper.writeValue(output, s);
    }

    @Override
    public void write(List<String> list) throws IOException {
        mapper.writeValue(output, list);
    }
}
