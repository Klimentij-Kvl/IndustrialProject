package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
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


public class DiffFileWriterTest {
    TypeReference<ArrayList<String>> ArListStr = new TypeReference<ArrayList<String>>() {};

    ObjectMapper makeObjectMapper(String format){
        return switch (format) {
            case "json" -> new ObjectMapper();
            case "xml" -> new XmlMapper();
            case "yaml" -> new YAMLMapper();
            default -> null;
        };
    }

    List<String> makeFileAndReadListFromFile(String format) throws IOException {
        DiffFileWriter writer = DiffFileWriter.Instance("writerTest", format);
        writer.write(mockList);
        String PATH_RES = "src/resources/";
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
        if(!file.delete()) return null;
        return arr;
    }
    @Spy
    List<String> mockList = new ArrayList<String>();

    @BeforeEach
    void setWriter(){
        mockList.add("one, kek, chebyrek");
        mockList.add("two, lol, pomerol");
    }

    @Test
    void plainTextWriterTest() throws IOException{
        assertEquals(mockList, makeFileAndReadListFromFile("txt"));
    }

    @Test
    void jsonWriterTest() throws IOException {
        assertEquals(mockList, makeFileAndReadListFromFile("json"));
    }

    @Test
    void xmlWriterTest() throws IOException {
        assertEquals(mockList, makeFileAndReadListFromFile("xml"));
    }

    @Test
    void yamlWriterTest() throws IOException {
        assertEquals(mockList, makeFileAndReadListFromFile("yaml"));
    }
}