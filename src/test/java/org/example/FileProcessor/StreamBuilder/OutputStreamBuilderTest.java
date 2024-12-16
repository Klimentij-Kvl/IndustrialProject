package org.example.FileProcessor.StreamBuilder;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class OutputStreamBuilderTest {
    @Test
    void createPlainStream() {
        String toWrite = "roflan kek lol chebyrek";
        try{
            OutputStreamBuilder builder = new OutputStreamBuilder("builderTest.txt");
            OutputStream out = builder.BuildStream();
            out.write(toWrite.getBytes());
            out.close();
            InputStream in = new FileInputStream("builderTest.txt");
            assertEquals(toWrite, new String(in.readAllBytes()));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}