package org.example.FileProcessor;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegratedFileProcessorTest {
    final String PATH_RES = "src/resources/";
    final String INTEGRATED_TEST = "integratesTest";
    @Spy
    List<String> mockList = new ArrayList<>();

    @BeforeEach
    void init(){
        mockList.add("one two three");
        mockList.add("five six seven");
        mockList.add("eight four nine");
    }

    @Test
    void WriteReadTxtTest() throws IOException {
        File file = new File(PATH_RES + INTEGRATED_TEST + ".txt");
        DiffFileWriter.Instance(INTEGRATED_TEST, "txt").write(mockList);
        assertEquals(mockList, DiffFileReader.Instance(INTEGRATED_TEST, "txt").read());
        assertTrue(file.delete());
    }
    @Test
    void WriteReadJsonTest() throws IOException {
        File file = new File(PATH_RES + INTEGRATED_TEST + ".json");
        DiffFileWriter.Instance(INTEGRATED_TEST, "json").write(mockList);
        assertEquals(mockList, DiffFileReader.Instance(INTEGRATED_TEST, "json").read());
        assertTrue(file.delete());
    }
    @Test
    void WriteReadXmlTest() throws IOException {
        File file = new File(PATH_RES + INTEGRATED_TEST + ".xml");
        DiffFileWriter.Instance(INTEGRATED_TEST, "xml").write(mockList);
        assertEquals(mockList, DiffFileReader.Instance(INTEGRATED_TEST, "xml").read());
        assertTrue(file.delete());
    }
    @Test
    void WriteReadYamlTest() throws IOException {
        File file = new File(PATH_RES + INTEGRATED_TEST + ".yaml");
        DiffFileWriter.Instance(INTEGRATED_TEST, "yaml").write(mockList);
        assertEquals(mockList, DiffFileReader.Instance(INTEGRATED_TEST, "yaml").read());
        assertTrue(file.delete());
    }
}
