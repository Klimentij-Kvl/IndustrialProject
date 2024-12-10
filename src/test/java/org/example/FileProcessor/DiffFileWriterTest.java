package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class DiffFileWriterTest {
    TypeReference<ArrayList<String>> ArListStr = new TypeReference<>() {};
    private final String PATH_RES = "src/resources/";

    @Spy
    List<String> wrote = new ArrayList<>();

    @BeforeEach
    void setWriter(){
        wrote.add("one, kek, chebyrek");
        wrote.add("two, lol, pomerol");
    }

    ObjectMapper makeObjectMapper(String format){
        return switch (format) {
            case "json" -> new ObjectMapper();
            case "xml" -> new XmlMapper();
            case "yaml" -> new YAMLMapper();
            default -> null;
        };
    }

    void SimpleFileWritingFunc(String format) throws IOException {
        DiffFileWriter dfw = new DiffFileWriter("writerTest", format);
        dfw.write(wrote);
        File file = new File(PATH_RES +"writerTest." + format);
        List<String> arr;
        if(format.equals("txt")){
            Scanner sc = new Scanner(file);
            arr = new ArrayList<>();
            while(sc.hasNextLine()){
                arr.add(sc.nextLine());
            }
            sc.close();
        }
        else arr = makeObjectMapper(format).readValue(file, ArListStr);
        assertEquals(wrote, arr);
        assertTrue(file.delete());
    }

    @Test
    void TxtSimpleWriteTest() throws IOException{
        SimpleFileWritingFunc("txt");
    }

    @Test
    void JsonSimpleWriteTest() throws IOException {
        SimpleFileWritingFunc("json");
    }

    @Test
    void XmlSimpleWriteTest() throws IOException {
        SimpleFileWritingFunc("xml");
    }

    @Test
    void YamlSimpleWriteTest() throws IOException {
        SimpleFileWritingFunc("yaml");
    }
}