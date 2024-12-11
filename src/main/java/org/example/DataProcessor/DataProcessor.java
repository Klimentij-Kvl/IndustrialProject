package org.example.DataProcessor;

import java.util.List;

public interface DataProcessor {
    List<String> process(List<String> data);
    boolean CreateFunction(String functionName, String formula);
    //CreateFunction("pow","x^y")
    //CreateFunction("ten","x*10")
}