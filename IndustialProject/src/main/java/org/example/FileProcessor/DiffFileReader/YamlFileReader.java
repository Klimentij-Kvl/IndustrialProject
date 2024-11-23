package org.example.FileProcessor.DiffFileReader;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class YamlFileReader {

    public static List<String> read(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            Yaml yaml = new Yaml();
            Iterable<Object> yamlObjects = yaml.loadAll(inputStream);
            for (Object obj : yamlObjects) {
                if (obj instanceof List) {
                    List<String> list = (List<String>) obj;
                    for (String text : list) {
                        List<String> expressions = extractExpressions(text);
                        data.addAll(expressions);
                    }
                }
            }
        }
        return data;
    }

    private static List<String> extractExpressions(String text) {
        List<String> expressions = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(?:\\d+\\s*[+\\-*/÷]\\s*\\d+|\\([^)]+\\))\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            expressions.add(matcher.group().trim());
        }
        return expressions;
    }
}
