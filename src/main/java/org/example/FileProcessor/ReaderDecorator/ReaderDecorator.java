package org.example.FileProcessor.ReaderDecorator;


import org.example.FileProcessor.DiffReader;

import java.io.IOException;
import java.util.List;

public abstract class ReaderDecorator implements DiffReader {
    private final DiffReader _dr;

    ReaderDecorator(DiffReader dr){
        _dr = dr;
    }

    @Override
    public List<String> read() throws IOException{
        return _dr.read();
    }

    @Override
    public void close() throws IOException {
        _dr.close();
    }

    @Override
    public String getPath(){
        return _dr.getPath();
    }
}
