package org.example.FileProcessor.DiffReader.DiffReaderDecorator;

import org.example.FileProcessor.DiffReader.DiffReader;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DecryptionDiffReaderDecorator extends DiffReaderDecorator {
    private Cipher cipher;

    public DecryptionDiffReaderDecorator(String key, DiffReader dr){
        super(dr);
        SecretKey secretKey = new SecretKeySpec
                (Arrays.copyOf(key.getBytes(), 16), "AES");
        try{
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void decrypt() throws IOException{
        String path = super.getPath();
        try(CipherInputStream cis = new CipherInputStream(new FileInputStream(path), cipher)){
            byte[] b = cis.readAllBytes();
            try(FileOutputStream fos = new FileOutputStream(path)){
                fos.write(b);
            }
        }
    }

    @Override
    public List<String> read() throws IOException{
        decrypt();
        return super.read();
    }

    @Override
    public String getType(){return super.getNextType();}
}
