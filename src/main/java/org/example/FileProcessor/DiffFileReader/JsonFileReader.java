package org.example.FileProcessor.DiffFileReader;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class JsonFileReader implements DiffFileReader {

    public List<String> read(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (!jsonElement.isJsonArray()) {
                throw new IllegalStateException("JSON content is not an array.");
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                    throw new IllegalStateException("JSON array contains non-string elements.");
                }
                data.add(element.getAsString());
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new IOException("Failed to parse JSON: " + e.getMessage(), e);
        }
        return data;
    }
}
