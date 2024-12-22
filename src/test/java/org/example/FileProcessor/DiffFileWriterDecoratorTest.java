package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.FileProcessor.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.WriterDecorator.EncryptionDecorator;
import org.example.FileProcessor.WriterDecorator.ZipArchivingDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiffFileWriterDecoratorTest {
    private final TypeReference<List<String>> listStr = new TypeReference<>() {};
    private final String PATH_RES = "src/resources/";

    @Spy
    List<String> toWrite;
    List<String> toRead;

    @BeforeEach
    void init(){
        toWrite = new ArrayList<>();
        toRead = new ArrayList<>();
        toWrite.add("one two three");
        toWrite.add("four five six");
        toWrite.add("seven eight nine");
    }

    @Test
    void EncTxtWriteTest() throws Exception {
        File file = new File(PATH_RES + "EncTxtWriteTest.txt");
        DiffWriter dw = new EncryptionDecorator("12345", new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();

        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf("12345".getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try(FileInputStream fis = new FileInputStream(file);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            InputStreamReader ir = new InputStreamReader(cis);
            BufferedReader br = new BufferedReader(ir)
        ){
            String line;
            while((line = br.readLine()) != null){
                toRead.add(line);
            }
        }

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void ZipTxtWriteTest() throws IOException{
        File file = new File(PATH_RES + "ZipTextWriteTest.txt");
        DiffWriter dw = new ZipArchivingDecorator(new TxtDiffFileWriter(file));
        dw.write(toWrite);
        dw.close();
    }

    @Test
    void ZipEncTxtWriteTest() throws IOException{
        File file = new File(PATH_RES + "ZipEncWriteTest.txt");
        DiffWriter dw = new ZipArchivingDecorator(new EncryptionDecorator("12345", new TxtDiffFileWriter(file)));
        dw.write(toWrite);
        dw.close();
    }

    @Test
    void EncZipTxtWriteTest() throws Exception{
        File file = new File(PATH_RES + "EncZipWriteTest.txt");
        DiffWriter dw = new EncryptionDecorator("12345",
                                new ZipArchivingDecorator(
                                new TxtDiffFileWriter(file)));
        dw.write(toWrite);
        dw.close();

        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf("12345".getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try(FileInputStream fis = new FileInputStream(PATH_RES + "EncZipWriteTest.zip");
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            FileOutputStream fos = new FileOutputStream(PATH_RES + "EncZipWriteTest2.zip")
        ){
            byte[] b = new byte[4096];
            while(cis.read(b) != -1)
                fos.write(b);
        }
    }
}
