package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DiffFileWriter {
    public String fileName;
    public String format;
    private static DiffFileWriter _instance;
    private final String PATH_RES = "src/resources/";

    protected DiffFileWriter(String fileName, String format){
        this.fileName = fileName;
        this.format = format;
    }

    public static DiffFileWriter Instance(String fileName, String format){
        if(_instance == null)  return new DiffFileWriter(fileName, format);
        _instance.fileName = fileName;
        _instance.format = format;
        return _instance;
    }

    private ObjectMapper makeObjectMapper(){
        return switch (format){
            case "json" -> new ObjectMapper();
            case "xml" -> new XmlMapper();
            case "yaml" -> new YAMLMapper();
            default -> null;
        };
    }

    public void write(List<String> text) throws IOException{
        File outputFile = new File(PATH_RES + fileName + "."  + format);
        if(format.equals("txt")){
            FileWriter out = new FileWriter(PATH_RES + fileName + "." + format);
            for (String s : text) {
                out.write(s + "\n");
            }
            out.flush();
            out.close();
            return;
        }
        makeObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValue(outputFile, text);
    }

    void archieve(String archieveName){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archieveName));
            FileInputStream fis= new FileInputStream(PATH_RES+fileName + "."  + format);)
        {
            zout.putNextEntry(new ZipEntry(PATH_RES+fileName + "."  + format));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    void CaesaerCipher(int code){
        try{
            File file = new File(PATH_RES+fileName + "." + format);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            for(int symbol : buffer){
                symbol += 3;
                fos.write(symbol);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    void encrypt(String keyString){
        try{
            FileOutputStream encFile = new FileOutputStream(PATH_RES+fileName +".enc");
            FileInputStream fis = new FileInputStream(PATH_RES+fileName + "." + format);
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

