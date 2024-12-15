package org.example.FileProcessor;

import org.example.FileProcessor.StreamChooser.EncryptionDecorator;
import org.example.FileProcessor.StreamChooser.FileStreamChooser;
import org.example.FileProcessor.StreamChooser.StreamChooser;
import org.example.FileProcessor.StreamChooser.StreamChooserDecorator;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class StreamChooserTest {
    @Test
    void PlainStreamChooserTest() throws IOException {
        String toWrite = "roflokek lol kek";

        StreamChooser chooser = new FileStreamChooser();
        OutputStream out = chooser.output(new FileOutputStream("testFileStream.txt"));
        out.write(toWrite.getBytes());
        out.close();

        InputStream in = chooser.input(new FileInputStream("testFileStream.txt"));
        byte[] b = new byte[in.available()];
        assertNotEquals(-1, in.read(b));
        assertEquals(toWrite, new String(b, StandardCharsets.UTF_8));
    }

    @Test
    void EncStreamChooserTest() throws IOException {
        String toWrite = "roflokek lol kek";

        StreamChooserDecorator chooser = new EncryptionDecorator
                ("12345", new FileStreamChooser());
        OutputStream outputStream = chooser.output(new FileOutputStream("testEncChooser.txt"));
        outputStream.write(toWrite.getBytes());
        outputStream.close();

        InputStream in = chooser.input(new FileInputStream("testEncChooser.txt"));
        byte[] b = in.readAllBytes();
        assertEquals(toWrite, new String(b));
    }
}