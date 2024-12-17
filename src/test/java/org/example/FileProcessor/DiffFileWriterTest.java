package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DiffFileWriterTest {
    private final TypeReference<List<String>> listStr = new TypeReference<List<String>>() {};

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
    void TxtWriteTest() throws IOException {
        File file = new File("src/resources/txtDfwTest.txt");
        try(DiffFileWriter dfw = new TxtDiffFileWriter(new FileOutputStream(file))) {
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

    @Test
    void JsonWriteTest() throws IOException{
        File file = new File("src/resources/txtDfwTest.json");
        try(DiffFileWriter dfw = new JsonDiffFileWriter(new FileOutputStream(file))){
            dfw.write(toWrite);

            ObjectMapper mapper = new ObjectMapper();
            toRead = mapper.readValue(file, listStr);
        }
        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }
}