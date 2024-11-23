package com.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.GeneralSecurityException;
import com.example.ArchieveReader;

public class FileProcessor {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java FileProcessor <inputFilePath> <outputFilePath> <fileType> [<encryptionKey> <iv>]");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String fileType = args[2];

        String encryptionKey = args.length > 3 ? args[3] : null;
        String iv = args.length > 4 ? args[4] : null;

        try {
            List<String> data;
            if ("encrypted".equals(fileType)) {
                if (encryptionKey == null || iv == null) {
                    throw new IllegalArgumentException("Encryption key and IV must be provided for encrypted file type.");
                }
                SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
                data = EncryptedFileReader.read(inputFilePath, secretKey, ivSpec);
            } else if ("archieve".equals(fileType)) {
                data = ArchieveReader.read(inputFilePath);
            } else {
                data = readFile(inputFilePath, fileType);
            }

            data.forEach(System.out::println);
            Path outputPath = Paths.get(outputFilePath);
            if (Files.notExists(outputPath)) {
                Files.createFile(outputPath);
            }
            Files.write(outputPath, data);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    static List<String> readFile(String filePath, String fileType) throws IOException {
        switch (fileType) {
            case "text":
                return TextFileReader.read(filePath);
            case "xml":
                return XmlFileReader.read(filePath);
            case "json":
                return JsonFileReader.read(filePath);
            case "yaml":
                return YamlFileReader.read(filePath);
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
