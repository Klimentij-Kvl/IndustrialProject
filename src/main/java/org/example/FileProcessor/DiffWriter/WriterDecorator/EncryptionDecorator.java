package org.example.FileProcessor.DiffWriter.WriterDecorator;

import org.example.FileProcessor.DiffWriter.DiffWriter;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EncryptionDecorator extends WriterDecorator{
    private Cipher cipher;
    //private String path;

    public EncryptionDecorator(String key, DiffWriter dw){
        super(dw);
        SecretKey secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES");
        try{
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void encrypt() throws IOException{
        File file = new File(super.getPath());
        byte[] b;

        try(FileInputStream fis = new FileInputStream(file)){
            b = fis.readAllBytes();
        }

        try(FileOutputStream fos = new FileOutputStream(file);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher)){
            cos.write(b);
        }
    }

    @Override
    public void write(String s) throws IOException{
        super.write(s);
        encrypt();
    }

    @Override
    public void write(List<String> list) throws IOException{
        super.write(list);
        encrypt();
    }

    //@Override
   // public String getPath(){
    //    return path;
    //}
}
