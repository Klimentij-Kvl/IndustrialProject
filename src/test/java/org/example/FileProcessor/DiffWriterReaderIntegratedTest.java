package org.example.FileProcessor;

import org.example.FileProcessor.DiffReader.DiffFileReader.JsonDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffFileReader.YamlDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.TarDearchivingDiffReaderDecorator;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.ZipDearchivingDiffReaderDecorator;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.JsonDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.YamlDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.DecryptionDiffReaderDecorator;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.EncryptionDiffWriterDecorator;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.TarArchivingDiffWriterDecorator;
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

        DiffReader dr = new ZipDearchivingDiffReaderDecorator(new TxtDiffFileReader(zipFile));
        toRead = dr.read();
        dr.close();

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(zipFile.delete());
    }

    @Test
    void TarTxtWriteReadTest() throws IOException{
        File file = new File(PATH_RES + "TarTxtWriteReadTest.txt");
        File tarFile = new File(PATH_RES + "TarTxtWriteReadTest.tar");
        try(DiffWriter dw =  new TarArchivingDiffWriterDecorator(new TxtDiffFileWriter(file))){
            dw.write(toWrite);
        }
        try(DiffReader dr = new TarDearchivingDiffReaderDecorator(new TxtDiffFileReader(tarFile))){
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        assertFalse(file.delete());
        assertTrue(tarFile.delete());
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

        DiffReader dr = new ZipDearchivingDiffReaderDecorator(
                        new DecryptionDiffReaderDecorator("12345",
                        new TxtDiffFileReader(zipEncFile)));
        toRead = dr.read();
        dr.close();

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(zipEncFile.delete());
    }

    @Test
    void ZipEncYamlWriteReadTest() throws IOException{
        File file = new File(PATH_RES + "ZipEncYamlWriteReadTest.yaml");
        File zipEncFile = new File(PATH_RES + "ZipEncYamlWriteReadTest.zip");
        try(DiffWriter dw = new ZipArchivingDiffWriterDecorator(
                            new EncryptionDiffWriterDecorator("12345",
                            new YamlDiffFileWriter(file)))) {
            dw.write(toWrite);
        }
        try(DiffReader dr = new ZipDearchivingDiffReaderDecorator(
                new DecryptionDiffReaderDecorator("12345",
                        new YamlDiffFileReader(zipEncFile)))) {
            toRead = dr.read();
        }
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
                        new ZipDearchivingDiffReaderDecorator(
                        new TxtDiffFileReader(encZipFile)))) {
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(encZipFile.delete());
    }

    @Test
    void EncZipJsonWriteReadTest() throws IOException{
        File file = new File(PATH_RES + "EncZipJsonWriteReadTest.json");
        File encZipFile = new File(PATH_RES + "EncZipJsonWriteReadTest.zip");
        try(DiffWriter dw = new EncryptionDiffWriterDecorator("12345",
                            new ZipArchivingDiffWriterDecorator(
                            new JsonDiffFileWriter(file)))) {
            dw.write(toWrite);
        }
        try(DiffReader dr = new DecryptionDiffReaderDecorator("12345",
                            new ZipDearchivingDiffReaderDecorator(
                            new JsonDiffFileReader(encZipFile)))) {
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        assertFalse(file.exists());
        assertTrue(encZipFile.delete());
    }
}

