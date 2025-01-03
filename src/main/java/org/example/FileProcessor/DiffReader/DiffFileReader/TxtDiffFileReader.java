package org.example.FileProcessor.DiffReader.DiffFileReader;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtDiffFileReader extends DiffFileReader{

    public TxtDiffFileReader(File file) throws IOException{
        super(file);
    }

    public TxtDiffFileReader(String fileName) throws IOException{
        super(fileName);
    }

    @Override
    public List<String> read() throws IOException {
        List<String> toRead = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(super.getPath());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null)
                    toRead.add(line);
            }

        return toRead;
    }

    @Override
    public String getType(){return "txt";}
}
