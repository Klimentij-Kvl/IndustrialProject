package org.example.FileProcessor.DiffWriter.DiffWriterDecorator;

import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.IOException;
import java.util.List;

public abstract class ArchivingDiffWriterDecorator extends DiffWriterDecorator {

    public ArchivingDiffWriterDecorator(DiffWriter dw){
        super(dw);
    }

    public abstract void archive() throws IOException;

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
