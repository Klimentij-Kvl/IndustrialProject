package org.example.FileProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class JsonDiffFileWriter extends DiffFileWriter{
    private final OutputStream output;
    private final ObjectMapper mapper;

    public JsonDiffFileWriter(OutputStream os){
        output = os;
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void write(List<String> list) throws IOException {
        mapper.writeValue(output, list);
    }
}
