package org.example.FileProcessor;

import org.example.FileProcessor.OutputStreamBuilder.FileEncrypterDecrypter;
import org.example.FileProcessor.OutputStreamBuilder.OutputStreamBuilder;
import org.junit.jupiter.api.Test;

import javax.crypto.CipherInputStream;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class OutputStreamBuilderTest {
    private final String PATH_RES = "src/resources/";

    @Test
    void PlainOutput() throws IOException {
        File file = new File(PATH_RES + "PlainOutStrBuilderTest.txt");
        String toWrite = "roflokek lol kek chebyrek";

        OutputStreamBuilder osb = new OutputStreamBuilder(file);
        OutputStream os = osb.getResult();
        os.write(toWrite.getBytes());
        os.close();

        InputStream is = new FileInputStream(file);
        assertEquals(toWrite, new String(is.readAllBytes()));
        is.close();
        assertTrue(file.delete());
    }

    @Test
    void EncOutput() throws IOException {
        File file = new File(PATH_RES + "EncOutStrBuilderTest.txt");
        String toWrite = "roflokek lol kek chebyrek";

        OutputStreamBuilder osb = new OutputStreamBuilder(file);
        osb.BuildEncryption("12345");
        OutputStream os = osb.getResult();
        os.write(toWrite.getBytes());
        os.close();

        FileEncrypterDecrypter fed = new FileEncrypterDecrypter("12345");
        FileInputStream fis = new FileInputStream(file);
        InputStream is = new CipherInputStream(fis, fed.decrCipher());
        assertEquals(toWrite, new String(is.readAllBytes()));
        is.close();
        fis.close();
        assertTrue(file.delete());
    }
}