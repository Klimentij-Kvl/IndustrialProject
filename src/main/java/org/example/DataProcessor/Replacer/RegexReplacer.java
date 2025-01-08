package org.example.DataProcessor.Replacer;

import org.example.DataProcessor.Extracter.RegexExtracter.MakeFuncExpression;

import java.util.ArrayList;
import java.util.List;

public class RegexReplacer implements Replacer{
    @Override
    public List<String> replace(List<String> rawList, List<String> calculatedExpressions){

        int index = 0;
        List<String> replacedExpressions = new ArrayList<>();
        MakeFuncExpression makeFuncExpression = new MakeFuncExpression();
        String functionsPlus = makeFuncExpression.makeFunctionsString().get(1);
        String regex;

        if (functionsPlus.isEmpty()) {
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*"
                    + "((?:(?:[()\\-+]+\\s*)*\\d+[ ()]*(?:[+\\-*÷/][ ()\\-+]*\\d+[ ()]*)+)+)"
                    + "|"
                    + "((?:(?:[()\\-+]+\\s*)*[()\\-+]{2,})+\\d+[()\\s]*)";
        } else {
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*"
                    + "((?:(?:[()\\-+]+\\s*)*(?:\\d+" + functionsPlus + ")[ ()]*"
                    + "(?:[+\\-*÷/][ ()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*)+)+)"
                    + "|"
                    + "((?:(?:[()\\-+]+\\s*)*[()\\-+]{2,})+(?:\\d+" + functionsPlus + ")[()\\s]*"
                    + "|((-*\\s*)*)" + functionsPlus.substring(1) + ")";
        }

        for (String line : rawList) {
            String modifiedLine;
            do {
                if (index >= calculatedExpressions.size()) {
                    replacedExpressions.add(line);
                    break;
                }

                modifiedLine = line.replaceFirst(regex, calculatedExpressions.get(index) + " ");
                modifiedLine = modifiedLine.replaceFirst("(\\d+) ,(\\d+)", "$1, $2");
                if (modifiedLine.equals(line)) {
                    replacedExpressions.add(line);
                    break;
                } else {
                    index++;
                    line = modifiedLine;
                }
            } while (true);
        }
        return replacedExpressions;    }
}
