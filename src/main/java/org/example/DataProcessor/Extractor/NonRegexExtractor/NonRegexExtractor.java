package org.example.DataProcessor.Extractor.NonRegexExtractor;

import org.example.DataProcessor.Extractor.Extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NonRegexExtractor implements Extractor {
    @Override
    public List<String> extract(List<String> rawList){
        List<String> expressions =new ArrayList<>();

        for(String s : rawList){
            expressions.addAll(FindExprInString(s));
        }

        return expressions;
    }

    private List<String> FindExprInString(String s){
        List<String> expressions = new ArrayList<>();
        String engAlph = "qwertyyuiopasdfghjklzxcvbnm";
        String rusAlph = "йцукенгшщзхъфывапролджэячсмитьбю";
        String greckAlph = "αβγδεζηθικλμνξοπρστυφχψω";
        String pynkt = ".,;:\"";
        s = s.replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(s,
                engAlph + rusAlph + greckAlph + pynkt
                        + engAlph.toUpperCase() + rusAlph.toUpperCase() + greckAlph.toUpperCase());

        while(st.hasMoreTokens()){
            expressions.add(st.nextToken());
        }

        return expressions;
    }
}
