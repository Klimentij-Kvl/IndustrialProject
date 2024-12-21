package org.example.FileProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface DiffWriter extends Closeable {
    void write(List<String> list) throws IOException;
    void write(String s) throws IOException;
    void close() throws IOException;
    String getPath();
}
