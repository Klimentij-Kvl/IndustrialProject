package org.example.FileProcessor.DiffFileReader;


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

        try(InputStreamReader isr = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(isr)) {

            String line;
            while((line = br.readLine()) != null)
                toRead.add(line);
        }
        return toRead;
    }
}
