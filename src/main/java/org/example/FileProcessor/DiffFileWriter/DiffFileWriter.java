package org.example.FileProcessor.DiffFileWriter;

import org.example.FileProcessor.DiffWriter;

import java.io.*;
import java.util.List;

public abstract class DiffFileWriter implements DiffWriter, Flushable {
    protected final FileOutputStream output;
    private final String path;

    public DiffFileWriter(File file) throws IOException{
        output = new FileOutputStream(file);
        path = file.getPath();
    }

    public DiffFileWriter(String name) throws IOException{
        this(name != null ? new File(name) : null);
    }

    @Override
    public abstract void write(String s) throws IOException;
    @Override
    public abstract void write(List<String> list) throws IOException;

    @Override
    public String getPath(){
        return path;
    }

    @Override
    public void close() throws IOException { output.close(); }

    @Override
    public void flush() throws IOException{ output.flush(); }
}
