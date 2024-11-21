package org.nocompany;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class fileWriter{
    public String fileName;
    public String format;

    public Boolean setFileNameAndFormat(String file){
        Pattern pattern = Pattern.compile("^(\\w+)\\.(\\w+)$");
        Matcher matcher = pattern.matcher(file);
        if(!matcher.matches()) return false;
        fileName = matcher.group(1);
        format = matcher.group(2);
        return true;
    }

    void write(List<String> text){
        if(format.equals("txt")) {
            try {
                FileWriter out = new FileWriter(fileName + "." + format);
                for (String s : text) {
                    out.write(s + "\n");
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if(format.equals("json")){
            File outputFile = new File(fileName + "."  + format);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(outputFile, text);
            }catch (IOException e){ System.out.println(e.getMessage());}
        }
        else if(format.equals("xml")){

            File outputFile = new File(fileName + "."  + format);
            ObjectMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(outputFile, text);
            }catch (IOException e){ System.out.println(e.getMessage());}
        }
        else if(format.equals("yaml")){
            File outputFile = new File(fileName + "."  + format);
            ObjectMapper mapper = new YAMLMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(outputFile, text);
            }catch (IOException e){ System.out.println(e.getMessage());}
        }
    }

    void archieve(String archieveName){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archieveName));
            FileInputStream fis= new FileInputStream(fileName + "."  + format);)
        {
            zout.putNextEntry(new ZipEntry(fileName + "."  + format));
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
            File file = new File(fileName + "." + format);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            for(int symbol : buffer){
                System.out.println(symbol);
                symbol += 3;
                fos.write(symbol);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}