package org.example.FileProcessor.DiffFileReader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class DiffFileReader implements Closeable {
    protected final InputStream input;

    public DiffFileReader(InputStream is){
        input = is;
    }

    public abstract List<String> read() throws IOException;

    @Override
    public void close() throws IOException {
        input.close();
    }
}
