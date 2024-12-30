package org.example.FileProcessor.DiffReader.DiffReaderDecorator;

import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.IOException;
import java.util.List;

public abstract class DearchivingDiffReaderDecorator extends DiffReaderDecorator {
   // private final DiffReader _dr;

    public DearchivingDiffReaderDecorator(DiffReader dr){
        super(dr);
    }

    protected abstract void dearchive() throws IOException;

    @Override
    public List<String> read() throws IOException{
        dearchive();
        return super.read();
    }

    @Override
    public String getPath(){
        return super.getPath();
    }

    @Override
    public void setPath(String path){
        super.setPath(path);
    }
}
