package org.example.FileProcessor.DiffFileReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

class AchieveReaderTest {

    @Test
    void testRead() throws IOException {
        ArchieveReader archieveReader = new ArchieveReader();
        List<String> data = ArchieveReader.read("test.zip");
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("10 + 5"));
    }
}
