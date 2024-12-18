package org.example.FileProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.example.FileProcessor.DiffFileReader.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiffFileReaderTest {
    private final String PATH_RES = "src/resources/";

    @Spy
    List<String> toWrite;
    List<String> toRead;

    @BeforeEach
    void init(){
        toWrite = new ArrayList<>();
        toRead = new ArrayList<>();
        toWrite.add("one two three");
        toWrite.add("four five six");
        toWrite.add("seven eight nine");
    }

    @Test
    void TxtDiffFileReader() throws IOException {
        File file = new File(PATH_RES + "txtReaderTest.txt");
        try(FileOutputStream fos = new FileOutputStream(file)){
            for(String s : toWrite){
                fos.write((s+"\n").getBytes());
            }
        }

        try(FileInputStream fis = new FileInputStream(file);
            DiffFileReader dfr = new TxtDiffFileReader(fis)){
            toRead = dfr.read();
        }

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    void SerializationWriteRead(File file, ObjectMapper mapper, DiffFileReader dfr) throws IOException{
        mapper.writeValue(file, toWrite);

        toRead = dfr.read();
        dfr.close();

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void JsonDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "jsonReadTest.json");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file, new JsonMapper(),
                new JsonDiffFileReader(new FileInputStream(file)));
    }

    @Test
    void XmlDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "xmlReadTest.xml");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file,new XmlMapper(),
                new XmlDiffFileReader(new FileInputStream(file)));
    }

    @Test
    void YamlDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "yamlReadTest.yaml");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file, new YAMLMapper(),
                new YamlDiffFileReader(new FileInputStream(file)));
    }

}
