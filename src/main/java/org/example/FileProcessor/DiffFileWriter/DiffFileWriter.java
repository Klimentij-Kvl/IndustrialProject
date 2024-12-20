package org.example.FileProcessor.DiffFileWriter;

import java.io.*;
import java.util.List;

public abstract class DiffFileWriter implements Closeable, Flushable {
    protected final OutputStream output;

    public DiffFileWriter(OutputStream os){
        output = os;
    }

    public DiffFileWriter(File file) throws IOException{
        output = new FileOutputStream(file);
    }

    public DiffFileWriter(String fileName) throws IOException{
        output = new FileOutputStream(fileName);
    }

    public abstract void write(String s) throws IOException;
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
