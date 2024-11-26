package org.example.FileProcessor.DiffFileReader;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class YamlFileReader implements DiffFileReader {

    public List<String> read(String filePath) throws IOException {
        List<String> data = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            Yaml yaml = new Yaml();
            Iterable<Object> yamlObjects = yaml.loadAll(inputStream);
            for (Object obj : yamlObjects) {
                if (obj instanceof List) {
                    List<String> list = (List<String>) obj;
                    data.addAll(list);
                }
            }
        }
        return data;
    }
}
