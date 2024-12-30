package org.example.FileProcessor.DiffReader.DiffFileReader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class SerializationDiffFileReader extends DiffFileReader{
    private final ObjectMapper mapper;

    protected SerializationDiffFileReader(File file, ObjectMapper mapper) throws IOException{
        super(file);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    protected SerializationDiffFileReader(String fileName, ObjectMapper mapper) throws IOException{
        super(fileName);
        this.mapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public List<String> read() throws IOException {
        return mapper.readValue(new File(super.getPath()), new TypeReference<>() {});
    }
}
