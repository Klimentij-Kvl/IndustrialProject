package org.example.DataProcessor.Replacer;

import java.util.List;

public interface Replacer {
    List<String> replace(List<String> rawList, List<String> calculatedExpressions);
}
