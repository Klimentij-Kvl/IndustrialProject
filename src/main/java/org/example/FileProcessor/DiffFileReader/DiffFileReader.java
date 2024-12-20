package org.example.FileProcessor.DiffFileReader;

import java.io.*;
import java.util.List;

public abstract class DiffFileReader implements Closeable {
    protected final InputStream input;

    public DiffFileReader(InputStream is){
        input = is;
    }

    public DiffFileReader(File file) throws IOException{
        input = new FileInputStream(file);
    }

    public DiffFileReader(String fileName) throws IOException{
        input = new FileInputStream(fileName);
    }

    public abstract List<String> read() throws IOException;

    @Override
    public void close() throws IOException {
        input.close();
    }
}
