package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class SerializationDiffFileWriter extends DiffFileWriter{

    private final ObjectMapper mapper;

    protected SerializationDiffFileWriter(OutputStream os, ObjectMapper mapper){
        super(os);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected SerializationDiffFileWriter(File file, ObjectMapper mapper) throws IOException{
        super(file);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected SerializationDiffFileWriter(String fileName, ObjectMapper mapper) throws IOException{
        super(fileName);
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
