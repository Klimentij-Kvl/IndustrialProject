package org.example.DataProcessor.Replacer;

import org.example.DataProcessor.Extractor.MakeFuncExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplacer implements Replacer {

    @Override
    public List<String> replace(List<String> rawList, List<String> calculatedExpressions) {
        int index = 0;
        List<String> replacedExpressions = new ArrayList<>();
        String regex = buildRegex();

        for (String line : rawList) {
            List<Integer> counts = countReplacements(line, regex);
            int i = 0;
            while (true) {
                if (index >= calculatedExpressions.size() || i >= counts.size()) {
                    replacedExpressions.add(line);
                    break;
                }

                String replacement = String.join(" ", calculatedExpressions.subList(index, index + counts.get(i)));
                replacement = replacement + " ";
                index += counts.get(i);

                line = line.replaceFirst(regex, replacement)
                        .replaceAll("(\\d),(\\d)", "$1,$2")
                        .replaceFirst("(\\d+) ,(\\d+)", "$1, $2");

                i++;
            }
        }
        return replacedExpressions;
    }

    private String buildRegex() {
        MakeFuncExpression makeFuncExpression = new MakeFuncExpression();
        String functionsPlus = makeFuncExpression.makeFunctionsString().get(1);

        String baseRegex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*" +
                "((?:(?:[()\\-+]+\\s*)*";

        if (functionsPlus.isEmpty()) {
            return baseRegex + "\\d+[ ()]*(?:[+\\-*\\u00f7/][ ()\\-+]*\\d+[ ()]*)+)+)" +
                    "|" +
                    "((?:(?:[()\\-+]+\\s*)*[()\\-+]{2,})+\\d+[()\\s]*)";
        } else {
            return baseRegex + "(?:\\d+" + functionsPlus + ")[ ()]*(?:[+\\-*\\u00f7/][ ()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*)+)+)" +
                    "|" +
                    "((?:(?:[()\\-+]+\\s*)*[()\\-+]{2,})+(?:\\d+" + functionsPlus + ")[()\\s]*)";
        }
    }

    private List<Integer> countReplacements(String line, String regex) {
        List<Integer> counts = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(line);

        while (matcher.find()) {
            int groupCount = 0;
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    groupCount++;
                }
            }
            counts.add(groupCount);
        }

        return counts;
    }
}
