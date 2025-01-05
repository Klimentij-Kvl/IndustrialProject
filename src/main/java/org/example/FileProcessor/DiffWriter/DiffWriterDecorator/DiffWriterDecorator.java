package org.example.FileProcessor.DiffWriter.DiffWriterDecorator;

import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class DiffWriterDecorator implements DiffWriter {

    private final DiffWriter _dw;

    public DiffWriterDecorator(DiffWriter dw){
        _dw = dw;
    }

    @Override
    public void write(String s) throws IOException{
        _dw.write(s);
    }

    @Override
    public void write(List<String> list) throws IOException{
        _dw.write(list);
    }

    @Override
    public void close() throws IOException{
        _dw.close();
    }

    @Override
    public String getPath(){
        return _dw.getPath();
    }
}
