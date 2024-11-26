package org.example.FileProcessor.DiffFileReader;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import java.util.regex.*;

public class EncryptedFileReader {

    public static List<String> read(String filePath, SecretKey secretKey, IvParameterSpec iv) throws IOException, GeneralSecurityException {
        List<String> data = new ArrayList<>();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        try (CipherInputStream cis = new CipherInputStream(Files.newInputStream(Paths.get(filePath)), cipher)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(cis));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        }
        return data;
    }
}
