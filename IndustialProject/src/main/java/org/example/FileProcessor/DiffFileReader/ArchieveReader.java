package org.example.FileProcessor.DiffFileReader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

public class ArchieveReader {

    public static List<String> read(String zipFilePath) throws IOException {
        List<String> data = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.addAll(extractEquations(line));
                    }
                }
                zis.closeEntry();
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
