package org.example.FileProcessor.DiffReader;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface DiffReader extends Closeable {
    List<String> read() throws IOException;
    String getPath();
    void setPath(String path);
    String getType();
}
