package org.example.FileProcessor.StreamChooser;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptionDecorator extends StreamChooserDecorator{
    final private SecretKey secretKey;
    Cipher cipher;
    IvParameterSpec ivParameterSpec;

    public EncryptionDecorator(String stringSecretKey, StreamChooserOld chooser){
        super(chooser);
        secretKey = new SecretKeySpec
                (Arrays.copyOf(stringSecretKey.getBytes(StandardCharsets.UTF_8),16), "AES");
        ivParameterSpec = new IvParameterSpec(stringSecretKey.getBytes());

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public OutputStream output(OutputStream out){
        return createEncryptOutput(super.output(out));
    }

    @Override
    public InputStream input(InputStream in){
        return createDecryptInput(super.input(in));
    }

    private OutputStream createEncryptOutput(OutputStream out) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return new CipherOutputStream(out, cipher);
        } catch (Exception e) {
            System.out.println("createEncryptOutput error with code: " + e.getMessage());
        }
        return out;
    }

    private InputStream createDecryptInput(InputStream in){
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            in =  new CipherInputStream(in, cipher);
        }catch(Exception e) {
            System.out.println("createDecryptInput error with code: " + e.getMessage());
        }
        return in;
    }
}
