package org.example.FileProcessor.DiffWriter.DiffFileWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TxtDiffFileWriter extends DiffFileWriter{

    public TxtDiffFileWriter(File file) throws IOException{
        super(file);
    }

    public TxtDiffFileWriter(String name) throws IOException{
        super(name);
    }

    @Override
    public void write(String s) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(super.getPath())) {
            fos.write(s.getBytes());
        }
    }

    @Override
    public void write(List<String> list) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(super.getPath())) {
            for (String s : list)
                fos.write((s + "\n").getBytes());
        }
    }
}
