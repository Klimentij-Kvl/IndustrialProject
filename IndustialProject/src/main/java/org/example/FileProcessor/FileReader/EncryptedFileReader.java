package org.example.FileProcessor.FileReader;

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
                data.addAll(extractEquations(line));
            }
        }
        return data;
    }

    private static List<String> extractEquations(String text) {
        List<String> equations = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\d\\s+\\-*/÷()]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            equations.add(matcher.group().trim());
        }
        return equations;
    }
}
