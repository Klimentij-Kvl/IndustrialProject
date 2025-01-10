package org.example.DataProcessor.Replacer;

import org.example.DataProcessor.Extractor.NonRegexExtractor;

import java.util.ArrayList;
import java.util.List;

public class NonRegexReplacer implements Replacer{
    @Override
    public List<String> replace(List<String> rawList, List<String> calculatedExpressions){
        NonRegexExtractor nre = new NonRegexExtractor();
        List<String> expressions = nre.extract(rawList);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < expressions.size(); i++){
            list.add((i+1) + ") " + expressions.get(i) + " = " + calculatedExpressions.get(i));
        }

        return list;
    }
}
