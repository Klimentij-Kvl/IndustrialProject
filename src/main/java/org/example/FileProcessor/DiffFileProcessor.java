package org.example.FileProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiffFileProcessor implements DataProcessor{
    private String fileName;

    public DiffFileProcessor(String  fileName){
        this.fileName = fileName;
    }

    @Override
    public void writeData(List<String> data){
        File file = new File(fileName);
        try(OutputStream fos = new FileOutputStream(file)){
            for(String s : data){
                fos.write((s + "\n").getBytes(), 0, s.length()+1);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> readData(){
        File file = new File(fileName);
        List<String> list = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String s;
            while((s = br.readLine()) != null){
                list.add(s);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        return list;
    }
}
