package org.example.FileProcessor.DiffFileReader;

import java.io.IOException;
import java.util.List;

public interface DiffFileReader {
    List<String> read(String filePath) throws IOException;
}
