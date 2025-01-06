package org.example.DataProcessor.Extracter;

import java.util.List;

public interface Extracter {
    List<String> extract(List<String> rawList);
}
