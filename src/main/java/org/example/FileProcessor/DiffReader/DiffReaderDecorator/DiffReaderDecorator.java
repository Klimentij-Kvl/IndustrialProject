package org.example.FileProcessor.DiffReader.DiffReaderDecorator;


import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class DiffReaderDecorator implements DiffReader {
    private final DiffReader _dr;
    private String path;

    DiffReaderDecorator(DiffReader dr){
        _dr = dr;
        path = "";
    }

    @Override
    public List<String> read() throws IOException{
        return _dr.read();
    }

    @Override
    public String getPath(){
        return _dr.getPath();
    }

    @Override
    public void setPath(String path){
        this.path = path;
        _dr.setPath(path);
    }

    @Override
    public void close() throws IOException{
        new File(path).delete();
        _dr.close();
    }

    public String getNextType(){return _dr.getType();}

}
