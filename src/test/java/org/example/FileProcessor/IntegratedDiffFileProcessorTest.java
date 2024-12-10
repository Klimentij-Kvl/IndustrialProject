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
    final String INTEGRATED_TEST = "integratedTest";
    @Spy
    List<String> read = new ArrayList<>();

    @BeforeEach
    void init(){
        read.add("one two three");
        read.add("five six seven");
        read.add("eight four nine");
    }

    void SimpleFileWritingAndReadingFunc(String format) throws IOException{
        File file = new File(PATH_RES + INTEGRATED_TEST + "." + format);
        DiffFileWriter dfw = new DiffFileWriter(INTEGRATED_TEST, format);
        DiffFileReader dfr = new DiffFileReader(INTEGRATED_TEST, format);
        dfw.write(read);
        List<String> wrote = dfr.read();
        assertEquals(read, wrote);
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
}
