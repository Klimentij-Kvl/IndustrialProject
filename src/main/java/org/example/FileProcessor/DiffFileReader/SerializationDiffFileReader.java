package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class SerializationDiffFileReader extends DiffFileReader{
    private final ObjectMapper mapper;

    protected SerializationDiffFileReader(InputStream os, ObjectMapper mapper){
        super(os);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<String> read() throws IOException {
        return mapper.readValue(input, new TypeReference<>() {});
    }
}
