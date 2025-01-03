package org.example.FileProcessor.DiffReader.DiffFileReader;

import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.*;
import java.util.List;

public abstract class DiffFileReader implements DiffReader {
    private String path;

    public DiffFileReader(File file) {
        path = file.getPath();
    }

    public DiffFileReader(String fileName) throws IOException{
        this(fileName != null ? new File(fileName) : null);
    }

    public abstract List<String> read() throws IOException;

    @Override
    public void setPath(String path){
        this.path = path;
    }

    @Override
    public String getPath(){
        return path;
    }

    @Override
    public void close() throws IOException{}
}
