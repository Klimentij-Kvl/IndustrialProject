package org.example.FileProcessor.StreamBuilder;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class OutputStreamBuilder {
    private OutputStream _currentStream;
    private String fileName;

    public OutputStreamBuilder(String fileName) throws IOException{
        this.fileName = fileName;
        _currentStream = new FileOutputStream(fileName);
    }

    public void BuildArchiving(String archiveType){

    }

    public void BuildEncryption(String secretKeyString){
        try {
            SecretKey secretKey = new SecretKeySpec
                    (Arrays.copyOf(secretKeyString.getBytes(), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            _currentStream = new CipherOutputStream(_currentStream, cipher);
        }catch (Exception e){
            System.out.println("Enc output error: " + e.getMessage());
        }
    }

    public OutputStream BuildStream(){
        return _currentStream;
    }
}
