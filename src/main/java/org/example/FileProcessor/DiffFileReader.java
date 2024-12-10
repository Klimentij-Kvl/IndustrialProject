package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DiffFileReader{
    private String fileName;
    private String fileFormat;
    private final String PATH_RES = "src/resources/";

    public DiffFileReader(String fileName, String fileFormat){
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }

    private ObjectMapper makeObjectMapper(){
        return switch (fileFormat){
            case "json" -> new ObjectMapper();
            case "xml" -> new XmlMapper();
            case "yaml" -> new YAMLMapper();
            default -> null;
        };
    }

    public List<String> read() throws IOException {
        List<String> arr = new ArrayList<>();
        File inputFile = new File(PATH_RES + fileName + "." + fileFormat);
        if(fileFormat.equals("txt")){
            Scanner sc = new Scanner(inputFile);
            while(sc.hasNextLine()){
                arr.add(sc.nextLine());
            }
            sc.close();
            return arr;
        }

        return makeObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).readValue(inputFile, new TypeReference<List<String>>() {});
    }

    public boolean dearchive(){
        try(ZipInputStream zin = new ZipInputStream
                (new FileInputStream(PATH_RES + fileName  + "." + fileFormat))){
            ZipEntry entry;
            String name;
            if((entry = zin.getNextEntry()) == null){
                System.out.println("No file in archive");
                return false;
            }
            name = entry.getName();
            FileOutputStream fout = new FileOutputStream(name);
            for (int c = zin.read(); c != -1; c = zin.read()) {
                fout.write(c);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();
            return true;
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
