package org.example.FileProcessor.FileBuilder;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class FileOutputBuilder {
    private String toWrite;
    private final String path;
    private OutputStream _currentStream;

    public FileOutputBuilder(List<String> list, String name) throws IOException {
        _currentStream = new FileOutputStream(name);
        StringBuilder sBuilder = new StringBuilder();
        for(String s : list){
            sBuilder.append(s).append("\n");
        }
        sBuilder.deleteCharAt(sBuilder.length()-1);
        toWrite = sBuilder.toString();
        this.path = name;
    }

    public void Build() throws IOException{
        _currentStream.write(toWrite.getBytes());
    }

    public void BuildEncrypting(String secretKeyString){
        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(secretKeyString.getBytes(), 16), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(secretKeyString.getBytes()));

            _currentStream = new CipherOutputStream(_currentStream, cipher);
        }catch (Exception e){
            System.out.println("create enc error: " + e.getMessage());
        }
    }
}
