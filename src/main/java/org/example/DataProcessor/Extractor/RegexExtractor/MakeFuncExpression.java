package org.example.DataProcessor.Extractor.RegexExtractor;

import org.example.DataBase.DataStorage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, отвечающий за создание строковых шаблонов для функций (mul/plus)
 * в зависимости от анализируемых формул.
 */
public class MakeFuncExpression {

    private static final Pattern LETTERS_PATTERN = Pattern.compile("[a-zа-яё]", Pattern.CASE_INSENSITIVE);

    private final DataStorage dataStorage;

    /**
     * Конструктор по умолчанию, использует синглтон-хранилище {@link DataStorage#getInstance()}.
     */
    public MakeFuncExpression() {
        dataStorage = DataStorage.getInstance();
    }

    /**
     * Подсчитывает количество уникальных букв в тексте (a-z, а-я, ё).
     * При этом удаляются упоминания вида \\d+functionsPlus (т. е. “число + функция”).
     *
     * @param text текст, в котором надо посчитать буквы
     * @return количество уникальных букв (регистр не важен)
     */
    public int countUniqueLetters(String text) {
        // Заменяем всё, что совпадает с (\\d+ + dataStorage.getFunctionsPlus()) на пустое
        String processed = text.toLowerCase()
                .replaceAll("(\\d+" + safeFunctionsPlus() + ")", "");

        Matcher matcher = LETTERS_PATTERN.matcher(processed);
        Set<Character> uniqueLetters = new HashSet<>();
        while (matcher.find()) {
            uniqueLetters.add(matcher.group().charAt(0));
        }
        return uniqueLetters.size();
    }

    /**
     * Из формул в dataStorage формирует паттерны для functionsMul и functionsPlus
     * и записывает их обратно в dataStorage.
     */
    public void makeFunctionsString() {
        Map<String, String> mapFunc = dataStorage.getFunctions();
        StringBuilder mulBuilder = new StringBuilder();
        StringBuilder plusBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : mapFunc.entrySet()) {
            String functionName = entry.getKey();
            String functionValue = entry.getValue();

            mulBuilder.append("|(?:")
                    .append(functionName)
                    .append("\\(+\\d+");
            plusBuilder.append("|(?:")
                    .append(functionName)
                    .append("\\(+\\d+");

            int count = countUniqueLetters(functionValue);
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

    /**
     * Безопасно получить functionsPlus, чтобы не было NPE.
     */
    private String safeFunctionsPlus() {
        String fp = dataStorage.getFunctionsPlus();
        return fp != null ? fp : "";
    }
}
