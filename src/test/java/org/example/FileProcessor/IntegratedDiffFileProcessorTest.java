package org.example.FileProcessor;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegratedDiffFileProcessorTest {
    final String PATH_RES = "src/resources/";
    final String SIMPLE_FILE_NAME = "integratedTest";
    final String ARCHIVE_FILE_NAME = "archiveIntegratedTest";
    final String ENCRYPT_FILE_NAME = "encryptIntegratedTest";
    @Spy
    List<String> writeList = new ArrayList<>();

    @BeforeEach
    void init(){
        writeList.add("one two three");
        writeList.add("five six seven");
        writeList.add("eight four nine");
    }

    void SimpleFileWritingAndReadingFunc(String format) throws IOException{
        File file = new File(PATH_RES + SIMPLE_FILE_NAME + "." + format);
        DiffFileWriter dfw = new DiffFileWriter(SIMPLE_FILE_NAME, format);
        DiffFileReader dfr = new DiffFileReader(SIMPLE_FILE_NAME, format);
        dfw.write(writeList);
        List<String> readList = dfr.read();
        assertEquals(writeList, readList);
        assertTrue(file.delete());
    }

    @Test
    void TxtSimpleWriteReadTest() throws IOException {
        SimpleFileWritingAndReadingFunc("txt");
    }
    @Test
    void JsonSimpleWriteReadTest() throws IOException {
        SimpleFileWritingAndReadingFunc("json");
    }
    @Test
    void XmlSimpleWriteReadTest() throws IOException {
        SimpleFileWritingAndReadingFunc("xml");
    }
    @Test
    void YamlSimpleWriteReadTest() throws IOException {
        SimpleFileWritingAndReadingFunc("yaml");
    }

    /*void ArchiveFileWritingAndReadingFunc(String format) throws IOException{
        File file = new File(PATH_RES + ARCHIVE_FILE_NAME + "." + format);
        File zFile = new File(PATH_RES + ARCHIVE_FILE_NAME + ".zip");
        DiffFileWriter dfw = new DiffFileWriter(ARCHIVE_FILE_NAME, format);
        DiffFileReader dfr = new DiffFileReader(ARCHIVE_FILE_NAME, format);
        dfw.write(writeList);
        dfw.archive(ARCHIVE_FILE_NAME);
        assertTrue(file.delete());

        assertTrue(dfr.dearchive());
        List<String> readList = dfr.read();
        assertEquals(writeList, readList);
        assertTrue(file.delete());
        assertTrue(zFile.delete());
    }*/

    @Test
    void ArchiveTxtWriteReadTest() throws IOException {

    }
    /*@Test
    void ArchiveJsonWriteReadTest() throws IOException {
        ArchiveFileWritingAndReadingFunc("json");
    }
    @Test
    void ArchiveXmlWriteReadTest() throws IOException {
        ArchiveFileWritingAndReadingFunc("xml");
    }
    @Test
    void ArchiveYamlWriteReadTest() throws IOException {
        ArchiveFileWritingAndReadingFunc("yaml");
    }

    @Test
    void EncryptTxtWriteReadTest() throws Exception {
        DiffFileWriter dfw = new DiffFileWriter(ENCRYPT_FILE_NAME, "txt");
        DiffFileReader dfr = new DiffFileReader(ENCRYPT_FILE_NAME, "txt");
        dfw.write(writeList);
        dfw.encrypt("12345");

        dfr.decrypt("12345");
        List<String> readList = dfr.read();

        assertEquals(writeList, readList);
    }*/
}
