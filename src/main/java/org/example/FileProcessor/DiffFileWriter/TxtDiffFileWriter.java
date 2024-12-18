package org.example.FileProcessor.DiffFileWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TxtDiffFileWriter extends DiffFileWriter{

    public TxtDiffFileWriter(OutputStream os){
        super(os);
    }

    @Override
    public void write(String s) throws IOException {
        output.write(s.getBytes());
    }

    @Override
    public void write(List<String> list) throws IOException {
        for(String s : list)
            output.write((s+"\n").getBytes());
    }
}
