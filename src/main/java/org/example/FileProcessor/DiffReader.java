package org.example.FileProcessor;

import java.io.IOException;
import java.util.List;

public interface DiffReader {
    List<String> read() throws IOException;
    void close() throws IOException;
    String getPath();
}
