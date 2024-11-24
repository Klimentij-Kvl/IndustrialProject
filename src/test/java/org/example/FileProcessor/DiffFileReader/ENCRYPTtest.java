package org.example.FileProcessor.DiffFileReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.IOException;
import java.security.*;
import java.util.*;

class EncryptedFileReaderTest {

    @Test
    void testRead() throws IOException, GeneralSecurityException {
        // Предположим, что у нас есть ключ и вектор инициализации для AES
        SecretKey secretKey = new SecretKeySpec("1234567890123456".getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec("1234567890123456".getBytes());

        List<String> data = EncryptedFileReader.read("encrypted.txt", secretKey, iv);
        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("10 + 5"));
    }
}
