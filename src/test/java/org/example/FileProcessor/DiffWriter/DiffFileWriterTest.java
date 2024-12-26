package org.example.FileProcessor.DiffWriter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DiffFileWriterTest {
    private final TypeReference<List<String>> listStr = new TypeReference<>() {};
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
    void TxtDiffFileWriteTest() throws IOException {
        File file = new File(PATH_RES + "txtDfwTest.txt");
        try(DiffFileWriter dfw = new TxtDiffFileWriter(file)){
            dfw.write(toWrite);

            try (
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr)
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    toRead.add(line);
                }
            }
        }
        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    void SerializationWriteRead(File file, ObjectMapper mapper, DiffFileWriter dfw) throws IOException{
        dfw.write(toWrite);
        dfw.close();

        toRead = mapper.readValue(file, listStr);

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void JsonDiffFileWriteTest() throws IOException{
        File file = new File(PATH_RES + "jsonDfwTest.json");
        DiffFileWriter jsonDfw = new JsonDiffFileWriter(file);
        SerializationWriteRead(file, new JsonMapper(), jsonDfw);
    }

    @Test
    void XmlDiffFileWriteTest() throws IOException{
        File file = new File(PATH_RES + "xmlDfwTest.xml");
        DiffFileWriter xmlDfw = new XmlDiffFileWriter(file);
        SerializationWriteRead(file, new XmlMapper(), xmlDfw);
    }

    @Test
    void YamlDiffFileWriteTest() throws IOException{
        File file = new File(PATH_RES + "yamlDfwTest.yaml");
        DiffFileWriter yamlDfw = new YamlDiffFileWriter(file);
        SerializationWriteRead(file, new YAMLMapper(), yamlDfw);
    }
}