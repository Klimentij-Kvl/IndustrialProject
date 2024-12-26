package org.example.FileProcessor.DiffWriter.WriterDecorator;

import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.IOException;
import java.util.List;

public abstract class ArchivingDecorator extends WriterDecorator{
    protected String path;

    public ArchivingDecorator(DiffWriter dw){
        super(dw);
        path = super.getPath();
    }

    public abstract void archive() throws IOException;

    @Override
    public String getPath(){
        return path;
    }

    @Override
    public void write(String s) throws IOException{
        super.write(s);
        archive();
    }

    @Override
    public void write(List<String> list) throws IOException {
        super.write(list);
        archive();
    }
}
