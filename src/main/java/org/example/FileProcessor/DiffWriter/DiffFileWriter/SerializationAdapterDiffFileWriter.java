package org.example.FileProcessor.DiffWriter.DiffFileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class SerializationAdapterDiffFileWriter extends DiffFileWriter{

    private final ObjectMapper mapper;

    protected SerializationAdapterDiffFileWriter(File file, ObjectMapper mapper) throws IOException{
        super(file);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected SerializationAdapterDiffFileWriter(String name, ObjectMapper mapper) throws IOException{
        super(name);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void write(String s) throws IOException {
        mapper.writeValue(new File(super.getPath()), s);
    }

    @Override
    public void write(List<String> list) throws IOException {
        mapper.writeValue(new File(super.getPath()), list);
    }
}
