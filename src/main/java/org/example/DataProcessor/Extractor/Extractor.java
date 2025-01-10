package org.example.DataProcessor.Extractor;

import java.util.List;

public interface Extractor {
    List<String> extract(List<String> rawList);
}
