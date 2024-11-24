package org.example.FileProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.example.FileProcessor.DiffFileWriter.DiffFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class diffFileWriterTest {
    DiffFileWriter writer;

    /*@Mock
    List mockList;*/
    @Spy
    List mockList = new ArrayList<String>();

    @BeforeEach
    void setWriter(){
        writer = new DiffFileWriter();
        mockList.add("one, kek, chebyrek");
        mockList.add("two, lol, pomerol");
    }

    @Test
    void setNameAndFormatTest(){
        Boolean b = writer.setFileNameAndFormat("output.txt");
        assertTrue(b);
        assertEquals("output", writer.fileName);
        assertEquals("txt", writer.format);
    }

    @Test
    void plainTextWriterTest() throws IOException{
        writer.setFileNameAndFormat("writerTest.txt");
        writer.write(mockList);
        File file = new File("writerTest.txt");
        Scanner sc = new Scanner(file);
        List<String> arr = new ArrayList<>();
        while(sc.hasNextLine()){
            arr.add(sc.nextLine());
        }
        assertEquals(mockList, arr);
        sc.close();
        file.delete();
    }

    @Test
    void jsonWriterTest() throws IOException {
        writer.setFileNameAndFormat("writerTest.json");
        writer.write(mockList);
        File file = new File("writerTest.json");
        ArrayList<String> arr = new ObjectMapper().readValue(file, ArrayList.class);
        assertEquals(mockList, arr);
        file.delete();
    }

    @Test
    void xmlWriterTest() throws IOException {
        writer.setFileNameAndFormat("writerTest.xml");
        writer.write(mockList);
        File file = new File("writerTest.xml");
        ArrayList<String> arr = new XmlMapper().readValue(file, ArrayList.class);
        assertEquals(mockList, arr);
        file.delete();
    }

    @Test
    void yamlWriterTest() throws IOException {
        writer.setFileNameAndFormat("writerTest.yaml");
        writer.write(mockList);
        File file = new File("writerTest.yaml");
        ArrayList<String> arr = new YAMLMapper().readValue(file, ArrayList.class);
        assertEquals(mockList, arr);
        file.delete();
    }
}