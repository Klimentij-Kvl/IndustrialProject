package org.example.FileProcessor;

import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.ReaderDecorator.ZipReaderDecorator;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffReader.ReaderDecorator.DecryptionReaderDecorator;
import org.example.FileProcessor.DiffWriter.WriterDecorator.EncryptionDecorator;
import org.example.FileProcessor.DiffWriter.WriterDecorator.ZipArchivingDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiffFileIntegratedTest {
    private final String PATH_RES = "src/resources/";

    @Spy
    List<String> toWrite = new ArrayList<>();
    List<String> toRead = new ArrayList<>();

    @BeforeEach
    void init(){
        toWrite.add("one two three");
        toWrite.add("four five six");
        toWrite.add("seven eight nine");
    }

    @Test
    void EncTxtWriteReadTest() throws IOException {
        File file = new File(PATH_RES + "EncTxtWriteReadTest.txt");

        DiffWriter dw = new EncryptionDecorator("12345",
                        new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();

        DiffReader dr = new DecryptionReaderDecorator("12345",
                        new TxtDiffFileReader(file));
        toRead = dr.read();

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void ZipTxtWriteReadTest() throws IOException {
        File file = new File(PATH_RES + "ZipTxtWriteReadTest.txt");
        File zipFile = new File(PATH_RES + "ZipTxtWriteReadTest.zip");
        DiffWriter dw = new ZipArchivingDecorator(new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();

        DiffReader dr = new ZipReaderDecorator(new TxtDiffFileReader(zipFile));
        toRead = dr.read();
        dr.close();

        assertEquals(toWrite, toRead);
        assertTrue(zipFile.delete());
    }
}

