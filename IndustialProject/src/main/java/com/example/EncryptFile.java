package com.example;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;

public class EncryptFile {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        if (args.length < 3) {
            System.out.println("Usage: java EncryptFile <inputFilePath> <outputFilePath> <encryptionKey> <iv>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String encryptionKey = args[2];
        String iv = args[3];

        SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        try (InputStream in = Files.newInputStream(Paths.get(inputFilePath));
             OutputStream out = Files.newOutputStream(Paths.get(outputFilePath));
             CipherOutputStream cos = new CipherOutputStream(out, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }
}
