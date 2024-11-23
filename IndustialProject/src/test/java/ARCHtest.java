package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

class AchieveReaderTest {

    @Test
    void testRead() throws IOException {
        List<String> data = AchieveReader.read("test.zip");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("10 + 5"));
    }
}
