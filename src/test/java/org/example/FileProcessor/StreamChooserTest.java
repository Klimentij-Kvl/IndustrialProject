package org.example.FileProcessor;

import org.example.FileProcessor.StreamChooser.*;
import org.example.FileProcessor.StreamChooser.EncryptionDecorator;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class StreamChooserTest {
    void CreateReadWriteFile(StreamChooserOld chooser) throws IOException{
        String toWrite = "roflokek lol kek";
        OutputStream out = chooser.output(new FileOutputStream("testEncChooser.txt"));
        out.write(toWrite.getBytes());
        out.close();

        InputStream in = chooser.input(new FileInputStream("testEncChooser.txt"));
        byte[] b = in.readAllBytes();
        assertEquals(toWrite, new String(b));
    }

    @Test
    void PlainStreamChooserTest() throws IOException {
        CreateReadWriteFile(new FileStreamChooser());
    }

    @Test
    void EncStreamChooserTest() throws IOException {
        CreateReadWriteFile(
                new EncryptionDecorator ("12345",
                        new FileStreamChooser()));
    }

    @Test
    void ArchStreamChooserTest() throws IOException {
        CreateReadWriteFile(new ArchivingDecorator("roflan", new FileStreamChooser()));
    }
}