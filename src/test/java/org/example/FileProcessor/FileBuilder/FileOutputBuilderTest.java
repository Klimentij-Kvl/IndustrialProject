package org.example.FileProcessor.FileBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileOutputBuilderTest {
    @Spy
    List<String> writeList = new ArrayList<>();

    @BeforeEach
    void initList(){
        writeList.add("one two three");
        writeList.add("four five six");
        writeList.add("seven eight nine");
    }

    @Test
    void PlainOutputFileBuilderTest() throws IOException {
        FileOutputBuilder builder = new FileOutputBuilder
                (writeList, "src/resources/FileOutputBuilderTest.txt");
        builder.Build();

        List<String> readList = new ArrayList<>();
        for(Scanner sc = new Scanner(new File("src/resources/FileOutputBuilderTest.txt"));
            sc.hasNextLine();
            readList.add(sc.nextLine()));
        assertEquals(writeList, readList);
    }

    @Test
    void EncFileOutputTest() throws Exception {
        FileOutputBuilder builder = new FileOutputBuilder
                (writeList, "src/resources/EncFileOutputBuilderTest.txt");
        builder.BuildEncrypting("1234567890123456");
        builder.Build();

        List<String> readList = null;
        try (FileInputStream fis = new FileInputStream("src/resources/EncFileOutputBuilderTest.txt")) {
            SecretKey secretKey = new SecretKeySpec(Arrays.copyOf("123456789012345".getBytes(), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec("123456789012345".getBytes()));
            try (
                    CipherInputStream cis = new CipherInputStream(fis, cipher);
            ) {
                readList = new ArrayList<>();

                System.out.println(new String(cis.readAllBytes()));
            }
        }
        assertNotNull(readList);
    }
}