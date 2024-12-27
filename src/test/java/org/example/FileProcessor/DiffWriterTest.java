package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.*;
import org.example.FileProcessor.DiffWriter.DiffWriter;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.EncryptionDiffWriterDecorator;
import org.example.FileProcessor.DiffWriter.DiffWriterDecorator.ZipArchivingDiffWriterDecorator;
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
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;
class DiffWriterTest {
    private final TypeReference<List<String>> listStr = new TypeReference<>() {
    };
    private final String PATH_RES = "src/resources/";

    @Spy
    List<String> toWrite;
    List<String> toRead;

    @BeforeEach
    void init() {
        toWrite = new ArrayList<>();
        toRead = new ArrayList<>();
        toWrite.add("one two three");
        toWrite.add("four five six");
        toWrite.add("seven eight nine");
    }

    @Test
    void TxtDiffFileWriteTest() throws IOException {
        File file = new File(PATH_RES + "txtDfwTest.txt");
        try (DiffFileWriter dfw = new TxtDiffFileWriter(file)) {
            dfw.write(toWrite);

            try (
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr)
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    toRead.add(line);
                }
            }
        }
        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    void SerializationWriteRead(File file, ObjectMapper mapper, DiffFileWriter dfw) throws IOException {
        dfw.write(toWrite);
        dfw.close();

        toRead = mapper.readValue(file, listStr);

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void JsonDiffFileWriteTest() throws IOException {
        File file = new File(PATH_RES + "jsonDfwTest.json");
        DiffFileWriter jsonDfw = new JsonDiffFileWriter(file);
        SerializationWriteRead(file, new JsonMapper(), jsonDfw);
    }

    @Test
    void XmlDiffFileWriteTest() throws IOException {
        File file = new File(PATH_RES + "xmlDfwTest.xml");
        DiffFileWriter xmlDfw = new XmlDiffFileWriter(file);
        SerializationWriteRead(file, new XmlMapper(), xmlDfw);
    }

    @Test
    void YamlDiffFileWriteTest() throws IOException {
        File file = new File(PATH_RES + "yamlDfwTest.yaml");
        DiffFileWriter yamlDfw = new YamlDiffFileWriter(file);
        SerializationWriteRead(file, new YAMLMapper(), yamlDfw);
    }

    List<String> TxtRead(File inFile) throws IOException {
        List<String> list = new ArrayList<>();
        try (
                FileInputStream fis = new FileInputStream(inFile);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }

        return list;
    }

    void Decrypt(File inFile, File outFile) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf("12345".getBytes(), 16), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream fis = new FileInputStream(inFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             FileOutputStream fos = new FileOutputStream(outFile)
        ) {
            byte[] b = cis.readAllBytes();
            fos.write(b);
        }
    }

    void DeZip(File inFile, File outFile) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(inFile));
             FileOutputStream fos = new FileOutputStream(outFile)) {
            zis.getNextEntry();
            for (int c = zis.read(); c != -1; c = zis.read()) {
                fos.write(c);
            }
            fos.flush();
            zis.closeEntry();
        }
    }

    @Test
    void EncTxtWriteTest() throws Exception {
        File encFile = new File(PATH_RES + "EncTxtWriteTest.txt");
        try (DiffWriter dw = new EncryptionDiffWriterDecorator("12345", new TxtDiffFileWriter(encFile))) {
            dw.write(toWrite);
        }

        File txtFile = new File(PATH_RES + "EncTxtWriteTest2.txt");

        Decrypt(encFile, txtFile);
        toRead = TxtRead(txtFile);

        assertEquals(toWrite, toRead);
        assertTrue(txtFile.delete());
        assertTrue(encFile.delete());
    }

    @Test
    void ZipTxtWriteTest() throws IOException {
        File txtFile = new File(PATH_RES + "ZipTxtWriteTest.txt");
        try (DiffWriter dw = new ZipArchivingDiffWriterDecorator(new TxtDiffFileWriter(txtFile))) {
            dw.write(toWrite);
        }

        File zipFile = new File(PATH_RES + "ZipTxtWriteTest.zip");

        DeZip(zipFile, txtFile);
        toRead = TxtRead(txtFile);

        assertTrue(txtFile.delete());
        assertTrue(zipFile.delete());
        assertEquals(toWrite, toRead);
    }

    @Test
    void ZipEncTxtWriteTest() throws Exception {
        File txtFile = new File(PATH_RES + "ZipEncWriteTest.txt");
        try (DiffWriter dw = new ZipArchivingDiffWriterDecorator(new EncryptionDiffWriterDecorator("12345", new TxtDiffFileWriter(txtFile)))) {
            dw.write(toWrite);
        }

        File zipEncFile = new File(PATH_RES + "ZipEncWriteTest.zip");
        File encFile = new File(PATH_RES + "ZipEncWriteTest2.txt");

        DeZip(zipEncFile, encFile);
        Decrypt(encFile, txtFile);
        toRead = TxtRead(txtFile);

        assertEquals(toWrite, toRead);
        assertTrue(zipEncFile.delete());
        assertTrue(encFile.delete());
        assertTrue(txtFile.delete());
    }

    @Test
    void EncZipTxtWriteTest() throws Exception {
        File txtFile = new File(PATH_RES + "EncZipWriteTest.txt");
        try (DiffWriter dw = new EncryptionDiffWriterDecorator("12345",
                            new ZipArchivingDiffWriterDecorator(
                            new TxtDiffFileWriter(txtFile)))) {
            dw.write(toWrite);
        }

        File encZipFile = new File(PATH_RES + "EncZipWriteTest.zip");
        File zipFile = new File(PATH_RES + "EncZipWriteTest2.zip");

        Decrypt(encZipFile, zipFile);
        DeZip(zipFile, txtFile);
        toRead = TxtRead(txtFile);

        assertEquals(toWrite, toRead);
        assertTrue(encZipFile.delete());
        assertTrue(zipFile.delete());
        assertTrue(txtFile.delete());

    }
}