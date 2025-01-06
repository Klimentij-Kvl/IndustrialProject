package org.example.DataProcessor.Replacer;

import java.util.List;

public class NonRegexReplacer implements Replacer{
    @Override
    public List<String> replace(List<String> rawList, List<String> calculatedExpressions){
        //TODO: replacing expressions without regex
        return rawList;
    }
}
