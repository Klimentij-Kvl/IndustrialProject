package org.example.FileProcessor.DiffFileReader;

import org.example.FileProcessor.DiffReader;

import java.io.*;
import java.util.List;

public abstract class DiffFileReader implements Closeable, DiffReader {
    private final String path;

    protected final InputStream input;

    public DiffFileReader(File file) throws IOException{
        input = new FileInputStream(file);
        path = file.getPath();
    }

    public DiffFileReader(String fileName) throws IOException{
        this(fileName != null ? new File(fileName) : null);
    }

    public abstract List<String> read() throws IOException;

    @Override
    public void close() throws IOException {
        input.close();
    }

    @Override
    public String getPath(){
        return path;
    }
}
