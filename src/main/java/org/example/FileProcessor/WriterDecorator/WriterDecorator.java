package org.example.FileProcessor.WriterDecorator;

import org.example.FileProcessor.DiffWriter;

import java.io.IOException;
import java.util.List;

public class WriterDecorator implements DiffWriter {

    private final DiffWriter _dw;

    public WriterDecorator(DiffWriter dw){
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
    public String getPath() {
        return _dw.getPath();
    }

    @Override
    public void close() throws IOException{
        _dw.close();
    }
}
