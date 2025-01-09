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
        String pynkt = ".,();:\"\"";
        StringTokenizer st = new StringTokenizer(s,
                engAlph + rusAlph + pynkt + engAlph.toUpperCase() + rusAlph.toUpperCase());

        String buff;
        while(st.hasMoreTokens()){
            buff = st.nextToken().replaceAll(" ", "");
            if(!buff.isEmpty())
                expressions.add(buff);
        }

        return expressions;
    }
}
