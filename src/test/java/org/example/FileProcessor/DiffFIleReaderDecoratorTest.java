package org.example.FileProcessor;

import org.example.FileProcessor.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.ReaderDecorator.DecryptionReaderDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DiffFIleReaderDecoratorTest {
    private final String PATH_RES  = "src/resources/";

    @Spy
    List<String> toWrite = new ArrayList<>();
    List<String> toRead = new ArrayList<>();

    @BeforeEach
    void init(){
        toWrite.add("one two three");
        toWrite.add("four five six");
        toWrite.add("seven eight nine");
    }

    void WriteTxt(File writeFile) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(writeFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw)
        ){
            for(String s : toWrite){
                bw.write(s + "\n");
            }
        }
    }

    void Encrypt(File readFile, File writeFile) throws Exception{
        SecretKey secretKey = new SecretKeySpec
                (Arrays.copyOf("12345".getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        try(CipherOutputStream  cis = new CipherOutputStream
                (new FileOutputStream(writeFile), cipher);
            FileInputStream fis = new FileInputStream(readFile)
        ){
            byte[] b = fis.readAllBytes();
            cis.write(b);
        }
    }

    @Test
    void DecrTxtReaderTest() throws Exception{
        File txtFile = new File(PATH_RES + "DecrTxtReaderTest.txt");
        File encFile = new File(PATH_RES + "2DecrTxtReaderTest.txt");
        WriteTxt(txtFile);
        Encrypt(txtFile, encFile);

        DiffReader dr = new DecryptionReaderDecorator("12345", new TxtDiffFileReader(encFile));
        toRead = dr.read();

        assertEquals(toWrite, toRead);
        assertTrue(txtFile.delete());
        assertTrue(encFile.delete());
    }
}
