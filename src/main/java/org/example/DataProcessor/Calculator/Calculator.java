package org.example.DataProcessor.Calculator;

import java.util.List;

public interface Calculator {
    String calculate(String expression);
    List<String> calculate(List<String> expressions);
}
