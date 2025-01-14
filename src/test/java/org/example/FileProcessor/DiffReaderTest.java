package org.example.FileProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.example.FileProcessor.DiffReader.DiffFileReader.*;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.DecryptionDiffReaderDecorator;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.TarDearchivingDiffReaderDecorator;
import org.example.FileProcessor.DiffReader.DiffReaderDecorator.ZipDearchivingDiffReaderDecorator;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
public class DiffReaderTest {
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
    void TxtDiffFileReader() throws IOException {
        File file = new File(PATH_RES + "txtReaderTest.txt");
        try(FileOutputStream fos = new FileOutputStream(file)){
            for(String s : toWrite){
                fos.write((s+"\n").getBytes());
            }
        }

        try(DiffFileReader dfr = new TxtDiffFileReader(file)) {
            toRead = dfr.read();
        }

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    void SerializationWriteRead(File file, ObjectMapper mapper, DiffFileReader dfr) throws IOException{
        mapper.writeValue(file, toWrite);

        toRead = dfr.read();

        assertEquals(toWrite, toRead);
        assertTrue(file.delete());
    }

    @Test
    void JsonDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "jsonReadTest.json");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file, new JsonMapper(),
                new JsonDiffFileReader(file));
    }

    @Test
    void XmlDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "xmlReadTest.xml");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file,new XmlMapper(),
                new XmlDiffFileReader(file));
    }

    @Test
    void YamlDiffFileReaderTest() throws IOException{
        File file = new File(PATH_RES + "yamlReadTest.yaml");
        assertTrue(file.createNewFile());
        SerializationWriteRead(file, new YAMLMapper(),
                new YamlDiffFileReader(file));
    }


    void TxtWrite(File writeFile) throws IOException {
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
        try(CipherOutputStream cis = new CipherOutputStream
                (new FileOutputStream(writeFile), cipher);
            FileInputStream fis = new FileInputStream(readFile)
        ){
            byte[] b = fis.readAllBytes();
            cis.write(b);
        }
    }

    void Zip(File readFile, File writeFile) throws IOException{
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(writeFile));
            FileInputStream fis = new FileInputStream(readFile)
        ){
            zos.putNextEntry(new ZipEntry(readFile.getName()));
            byte[] b = fis.readAllBytes();
            zos.write(b);
            zos.closeEntry();
        }
    }

    void Tar(File readFile, File writeFile) throws IOException{

        try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(writeFile));
             FileInputStream fis = new FileInputStream(readFile)
        ) {
            tos.putArchiveEntry(new TarArchiveEntry(readFile, readFile.getName()));
            byte[] b =  fis.readAllBytes();
            tos.write(b);
            tos.closeArchiveEntry();
        }
    }

    @Test
    void DecryptTxtReaderTest() throws Exception {
        File txtFile = new File(PATH_RES + "DecryptTxtReaderTest.txt");
        File encFile = new File(PATH_RES + "2DecryptTxtReaderTest.txt");
        TxtWrite(txtFile);
        Encrypt(txtFile, encFile);

        try(DiffReader dr = new DecryptionDiffReaderDecorator("12345",
                new TxtDiffFileReader(encFile))) {
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        assertTrue(txtFile.delete());
        assertTrue(encFile.delete());
    }

    @Test
    void ZipTxtReaderTest() throws IOException{
        File txtFile = new File(PATH_RES + "newZipTxtReaderTest.txt");
        File zipFile = new File(PATH_RES + "ZipTxtReaderTest.zip");
        TxtWrite(txtFile);
        Zip(txtFile, zipFile);

        try(DiffReader dr = new ZipDearchivingDiffReaderDecorator(new TxtDiffFileReader(zipFile))) {
            toRead = dr.read();
        }
        assertEquals(toWrite, toRead);
        assertFalse(txtFile.exists());
        assertTrue(zipFile.delete());
    }

    @Test
    void TarTxtReaderTest() throws IOException{
        File txtFile = new File(PATH_RES + "newTarTxtReaderTest.txt");
        File tarFile = new File(PATH_RES + "TarTxtReaderTest.tar");
        TxtWrite(txtFile);
        Tar(txtFile, tarFile);
        try(DiffReader dr = new TarDearchivingDiffReaderDecorator(new TxtDiffFileReader(tarFile))){
            toRead = dr.read();
        }
        assertEquals(toWrite, toRead);
        assertFalse(txtFile.exists());
        assertTrue(tarFile.delete());
    }

    @Test
    void DecryptZipReaderTest() throws Exception{
        File txtFile = new File(PATH_RES + "newDecryptZipReaderTest.txt");
        File encFile = new File(PATH_RES + "2newDecryptZipReaderTest.txt");
        File zipFile = new File(PATH_RES + "DecryptZipReaderTest.zip");

        TxtWrite(txtFile);
        Encrypt(txtFile, encFile);
        Zip(encFile, zipFile);

        try(DiffReader dr =
                new ZipDearchivingDiffReaderDecorator(
                        new DecryptionDiffReaderDecorator(
                                "12345",  new TxtDiffFileReader(zipFile)))){
            toRead = dr.read();
        }

        assertEquals(toWrite, toRead);
        //assertTrue(txtFile.delete());
        assertTrue(zipFile.delete());
        assertTrue(encFile.delete());
    }

    @Test
    void ZipDecryptReaderTest() throws Exception{
        File txtFile = new File(PATH_RES + "ZipDecryptReaderTest.txt");
        File zipFile = new File(PATH_RES + "ZipDecryptReaderTest.zip");
        File encFile = new File(PATH_RES + "2ZipDecryptReaderTest.zip");

        TxtWrite(txtFile);
        Zip(txtFile, zipFile);
        Encrypt(zipFile, encFile);

        DiffReader dr = new DecryptionDiffReaderDecorator("12345",
                new ZipDearchivingDiffReaderDecorator(
                        new TxtDiffFileReader(encFile)));
        toRead = dr.read();
        dr.close();
        assertEquals(toWrite, toRead);
        assertTrue(txtFile.delete());
        assertTrue(zipFile.delete());
        assertTrue(encFile.delete());
    }
}
