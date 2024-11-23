package org.example.FileProcessor.FileReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

class JsonFileReaderTest {

    @Test
    void testRead() throws IOException {
        List<String> data = JsonFileReader.read("test_input.json");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("12 * 2"));
        assertTrue(data.contains("30 ÷ 5"));
    }

    @Test
    void testExtractEquations() {
        String text = "Результат 12 * 2, и 30 ÷ 5 был рассчитан за несколько минут.";
        List<String> equations = JsonFileReader.extractEquations(text);
        assertEquals(2, equations.size());
        assertEquals("12 * 2", equations.get(0));
        assertEquals("30 ÷ 5", equations.get(1));
    }
}
