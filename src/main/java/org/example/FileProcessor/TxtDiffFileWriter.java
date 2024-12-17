package org.example.FileProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class TxtDiffFileWriter extends DiffFileWriter{
    private final OutputStream output;

    TxtDiffFileWriter(OutputStream os){
        output = os;
    }

    @Override
    public void write(List<String> list) throws IOException {
        for(String s : list)
            output.write((s+"\n").getBytes());
    }
}
