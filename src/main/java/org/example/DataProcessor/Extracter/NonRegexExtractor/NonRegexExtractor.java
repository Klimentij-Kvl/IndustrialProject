package org.example.DataProcessor.Extracter.NonRegexExtractor;

import org.example.DataProcessor.Extracter.Extracter;

import java.util.List;

public class NonRegexExtractor implements Extracter {
    @Override
    public List<String> extract(List<String> rawList){
        //TODO: extracting expressions from rawList without regex
        return rawList;
    }
}
