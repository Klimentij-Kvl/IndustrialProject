package org.example.FileProcessor.DiffFileReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class XmlFileReaderTest {

    @Test
    void testRead() {
        List<String> data = XmlFileReader.read("test_input.xml");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("(7 + 8) * 2"));
    }

    @Test
    void testExtractEquations() {
        String text = "Чтобы найти общий результат, нужно было вычислить (7 + 8) * 2.";
        List<String> equations = XmlFileReader.extractEquations(text);
        assertEquals(1, equations.size());
        assertEquals("(7 + 8) * 2", equations.get(0));
    }
}
