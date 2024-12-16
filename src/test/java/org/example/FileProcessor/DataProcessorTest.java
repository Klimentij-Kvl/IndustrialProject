package org.example.FileProcessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataProcessorTest {
    @Spy
    List<String> writeList = new ArrayList<>();

    @BeforeEach
    void init(){
        writeList.add("one two three");
        writeList.add("four five six");
        writeList.add("seven eight nine");
    }

    @Test
    void TxtReadWriteTest(){
        DataProcessor dp = new DiffFileProcessor("test.txt");
        dp.writeData(writeList);
        List<String> readList = dp.readData();
        assertEquals(writeList, readList);
    }

    /*@Test
    void EncodeTxtReadWriteTest(){
        DataProcessorDecorator encoded = new EncryptionDecorator(
                                            "1234567890123456",
                                    new DiffFileProcessor("EncTest.txt"));

        encoded.writeData(writeList);
        List<String> readList = encoded.readData();
        assertEquals(readList, writeList);
    }*/
}
