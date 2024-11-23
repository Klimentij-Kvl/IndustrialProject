package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

class TextFileReaderTest {

    @Test
    void testRead() throws IOException {
        List<String> data = TextFileReader.read("test_input.txt");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("10 + 5"));
    }

    @Test
    void testExtractEquations() {
        String text = "Сегодня мы решали 10 + 5, и это оказалось просто.";
        List<String> equations = TextFileReader.extractEquations(text);
        assertEquals(1, equations.size());
        assertEquals("10 + 5", equations.get(0));
    }
}
