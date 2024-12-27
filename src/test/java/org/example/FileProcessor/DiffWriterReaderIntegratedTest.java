package org.example.FileProcessor;

import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.ZipDiffReaderDecorator;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.DecryptionDiffReaderDecorator;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.EncryptionDiffWriterDecorator;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.ZipArchivingDiffWriterDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiffWriterReaderIntegratedTest {
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

        DiffWriter dw = new EncryptionDiffWriterDecorator("12345",
                        new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();

        DiffReader dr = new DecryptionDiffReaderDecorator("12345",
                        new TxtDiffFileReader(file));
        toRead = dr.read();

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void ZipTxtWriteReadTest() throws IOException {
        File file = new File(PATH_RES + "ZipTxtWriteReadTest.txt");
        File zipFile = new File(PATH_RES + "ZipTxtWriteReadTest.zip");
        DiffWriter dw = new ZipArchivingDiffWriterDecorator(new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();

        DiffReader dr = new ZipDiffReaderDecorator(new TxtDiffFileReader(zipFile));
        toRead = dr.read();
        dr.close();

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(zipFile.delete());
    }


    @Test
    void ZipEncTxtWriteReadTest() throws IOException{
        File file = new File(PATH_RES + "ZipEncTxtWriteReadTest.txt");
        File zipEncFile = new File(PATH_RES + "ZipEncTxtWriteReadTest.zip");
        DiffWriter dw = new ZipArchivingDiffWriterDecorator(
                        new EncryptionDiffWriterDecorator("12345",
                        new TxtDiffFileWriter(file)));
        dw.write(toWrite);
        dw.close();

        DiffReader dr = new ZipDiffReaderDecorator(
                        new DecryptionDiffReaderDecorator("12345",
                        new TxtDiffFileReader(zipEncFile)));
        toRead = dr.read();
        dr.close();

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(zipEncFile.delete());
    }

    @Test
    void EncZipTxtWriteReadTest() throws IOException{
        File file = new File(PATH_RES + "EncZipTxtWriteReadTest.txt");
        File encZipFile = new File(PATH_RES + "EncZipTxtWriteReadTest.zip");

        try(DiffWriter dw = new EncryptionDiffWriterDecorator("12345",
                        new ZipArchivingDiffWriterDecorator(
                        new TxtDiffFileWriter(file)))) {
            dw.write(toWrite);
        }

        try(DiffReader dr = new DecryptionDiffReaderDecorator("12345",
                        new ZipDiffReaderDecorator(
                        new TxtDiffFileReader(encZipFile)))) {
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(encZipFile.delete());
    }
}

