package org.example.DataProcessor.RegexProcessor;

import org.example.DataBase.DataStorage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MakeFuncExpression {
    private static final Pattern LETTERS_PATTERN = Pattern.compile("[a-zа-яё]", Pattern.CASE_INSENSITIVE);
    MakeFuncExpression() {}

    static int countUniqueLetters(String text) {
        DataStorage dataStorage = DataStorage.getInstance();
        text = text.toLowerCase();
        text = text.replaceAll("(\\d+" + dataStorage.getFunctionsPlus() + ")", "");

        Matcher matcher = LETTERS_PATTERN.matcher(text);
        Set<Character> uniqueLetters = new HashSet<>();

        while (matcher.find()) {
            uniqueLetters.add(matcher.group().charAt(0));
        }
        return uniqueLetters.size();
    }

    static void makeFunctionsString() {
        DataStorage dataStorage = DataStorage.getInstance();
        Map<String, String> mapFunc = dataStorage.getFunctions();

        StringBuilder mulBuilder = new StringBuilder();
        StringBuilder plusBuilder = new StringBuilder();

        for (String key : mapFunc.keySet()) {
            mulBuilder.append("|(?:").append(key).append("\\(+\\d+");
            plusBuilder.append("|(?:").append(key).append("\\(+\\d+");

            int count = countUniqueLetters(mapFunc.get(key));
            for (int i = 1; i < count; i++) {
                mulBuilder.append(",\\d+");
                plusBuilder.append(",\\d+");
            }

            mulBuilder.append("\\)+)*");
            plusBuilder.append("\\)+)+");
        }

        dataStorage.setFunctionsMul(mulBuilder.toString());
        dataStorage.setFunctionsPlus(plusBuilder.toString());
    }
}
