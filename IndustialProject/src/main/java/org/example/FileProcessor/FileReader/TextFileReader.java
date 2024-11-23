package org.example.FileProcessor.FileReader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TextFileReader {

    public static List<String> read(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<String> equations = new ArrayList<>();
        for (String line : lines) {
            equations.addAll(extractEquations(line));
        }
        return equations;
    }

    public static List<String> extractEquations(String text) {
        List<String> equations = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\d\\s+\\-*/÷()]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            equations.add(matcher.group().trim());
        }
        return equations;
    }
}
