package org.example.FileProcessor.DiffFileReader;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TextFileReader implements DiffFileReader {

    public List<String> read(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }
}
