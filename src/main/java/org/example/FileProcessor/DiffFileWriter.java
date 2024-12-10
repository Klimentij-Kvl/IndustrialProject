package org.example.FileProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DiffFileWriter {
    public String fileName;
    public String fileFormat;
    private final String PATH_RES = "src/resources/";

    public DiffFileWriter(String fileName, String fileFormat){
        this.fileName = fileName;
        this.fileFormat = fileFormat;
    }

    private ObjectMapper makeObjectMapper(){
        return switch (fileFormat){
            case "json" -> new JsonMapper();
            case "xml" -> new XmlMapper();
            case "yaml" -> new YAMLMapper();
            default -> null;
        };
    }

    public void write(List<String> text) throws IOException{
        File outputFile = new File(PATH_RES + fileName + "."  + fileFormat);
        if(fileFormat.equals("txt")){
            FileWriter out = new FileWriter(outputFile);
            for (String s : text) {
                out.write(s + "\n");
            }
            out.flush();
            out.close();
            return;
        }
        makeObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValue(outputFile, text);
    }

    void archive(String archiveName) throws IOException {
        FileOutputStream fos = new FileOutputStream(PATH_RES + archiveName + ".zip");
        ZipOutputStream zout = new ZipOutputStream(fos);
        FileInputStream fis = new FileInputStream(PATH_RES + fileName + "." + fileFormat);

        zout.putNextEntry(new ZipEntry(fileName + "." + fileFormat));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        zout.write(buffer);
        zout.closeEntry();
        zout.close();
        fos.close();
        fis.close();
    }

    void encrypt(String keyString){
        try(FileOutputStream encFile = new FileOutputStream(PATH_RES+fileName +".enc");
            FileInputStream fis = new FileInputStream(PATH_RES+fileName + "." + fileFormat))
        {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            SecretKey key = new SecretKeySpec(Arrays.copyOf(keyString.getBytes(StandardCharsets.UTF_8),16), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] outBytes = cipher.doFinal(buffer);
            encFile.write(outBytes);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

