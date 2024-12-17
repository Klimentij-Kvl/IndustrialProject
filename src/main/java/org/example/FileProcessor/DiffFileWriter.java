package org.example.FileProcessor;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class DiffFileWriter implements Closeable, Flushable {
    protected final OutputStream output;

    public DiffFileWriter(OutputStream os){
        output = os;
    }

    public abstract void write(List<String> list) throws IOException;

    @Override
    public void close() throws IOException{
        output.close();
    }

    @Override
    public void flush() throws IOException{
        output.flush();
    }
}
