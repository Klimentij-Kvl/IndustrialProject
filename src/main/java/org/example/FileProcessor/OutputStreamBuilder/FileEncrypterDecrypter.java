package org.example.FileProcessor.OutputStreamBuilder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class FileEncrypterDecrypter {
    private final SecretKey secretKey;
    private Cipher cipher;

    public FileEncrypterDecrypter(String key){
        secretKey = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES");
        try{
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }catch (Exception e){
            System.out.println("Creating Cipher error");
        }
    }

    public Cipher encCipher(){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return cipher;
    }

    public Cipher decrCipher(){
        try{
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return cipher;
    }
}
