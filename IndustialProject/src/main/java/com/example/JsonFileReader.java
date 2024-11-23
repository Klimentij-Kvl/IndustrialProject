package com.example;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class JsonFileReader {

    public static List<String> read(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                data.addAll(extractEquations(jsonElement.getAsString()));
            }
        }
        return data;
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
