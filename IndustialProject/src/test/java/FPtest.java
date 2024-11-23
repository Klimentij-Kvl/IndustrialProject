package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

class FileProcessorTest {

    @Test
    void testReadFile() throws IOException {
        List<String> data = FileProcessor.readFile("test.yaml", "yaml");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("10 + 5"));
        assertTrue(data.contains("12 * 2"));
        assertTrue(data.contains("30 ÷ 5"));
    }
}
