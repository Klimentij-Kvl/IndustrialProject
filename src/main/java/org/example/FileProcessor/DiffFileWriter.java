package org.example.FileProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class DiffFileWriter implements Closeable {
    private OutputStream output;

    abstract void write(List<String> list) throws IOException;

    @Override
    public void close(){

    }
}
