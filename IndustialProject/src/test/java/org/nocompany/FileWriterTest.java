package org.nocompany;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    @Test
    void setNameAndFormatTest(){
        fileWriter writer = new fileWriter();
        Boolean b = writer.setFileNameAndFormat("output.txt");
        assertTrue(b);
        assertEquals("output", writer.fileName);
        assertEquals("txt", writer.format);
    }
}