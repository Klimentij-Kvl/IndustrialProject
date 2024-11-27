package org.example.FileProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiffFileReader{
    public String fileName;
    public String fileFormat;
    private static DiffFileReader _instance;
    private final String PATH_RES = "src/resources/";

    protected DiffFileReader(String fileName, String fileFormat){
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }

    public static DiffFileReader Instance(String fileName, String fileFormat){
        if(_instance == null) _instance = new DiffFileReader(fileName, fileFormat);
        _instance.fileName = fileName;
        _instance.fileFormat = fileFormat;
        return _instance;
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
}
