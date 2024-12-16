package org.example.FileProcessor;

import java.util.List;

public interface DataProcessor {
    void writeData(List<String> data);

    List<String> readData();
}
