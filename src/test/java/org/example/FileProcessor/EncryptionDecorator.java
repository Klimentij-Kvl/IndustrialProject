package org.example.FileProcessor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class EncryptionDecorator extends DataProcessorDecorator{
    private String stringSecretKey;

    EncryptionDecorator(String param, DataProcessor processor){
        super(processor);
        this.stringSecretKey = param;
    }

    @Override
    public void writeData(List<String> data){
        super.writeData(encode(data));
    }

    @Override
    public List<String> readData(){
        return super.readData();
    }

    private List<String> encode(List<String> data){
        List<String> encList = new ArrayList<>();
        try {
            SecretKey secretKey = new SecretKeySpec(
                    stringSecretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            for (String s : data){
                encList.add(Base64.getEncoder().encodeToString(cipher.doFinal(s.getBytes())));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return encList;
    }
}
