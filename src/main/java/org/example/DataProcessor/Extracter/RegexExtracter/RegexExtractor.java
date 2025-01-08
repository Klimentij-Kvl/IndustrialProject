package org.example.DataProcessor.Extracter.RegexExtracter;

import org.example.DataProcessor.Extracter.Extracter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexExtractor implements Extracter {

    private final String functionsMul;
    private final String functionsPlus;

    private final Pattern REDUNDANT_MINUS_PATTERN;
    private final Pattern REDUNDANT_PLUS_PATTERN;
    private final Pattern CHOOSE_SIGN_PATTERN;
    private final Pattern USELESS_PLUS_PATTERN_1;
    private final Pattern USELESS_PLUS_PATTERN_2;
    private final Pattern ADD_MUL_BETWEEN_BRACKETS_PATTERN;
    private final Pattern NORMALIZE_BRACKETS_PATTERN;
    private final Pattern DELETE_SINGLE_OBJECT_BRACKETS_PATTERN;
    private final Pattern RETURN_USEFUL_BRACKETS_PATTERN;


    public RegexExtractor() {
        MakeFuncExpression makeFuncExpression = new MakeFuncExpression();
        List<String> list = makeFuncExpression.makeFunctionsString();
        functionsMul = list.get(0);
        functionsPlus = list.get(1);

        this.REDUNDANT_MINUS_PATTERN = Pattern.compile("-([+]*?)-");
        this.REDUNDANT_PLUS_PATTERN = Pattern.compile("\\+\\++");
        this.CHOOSE_SIGN_PATTERN = Pattern.compile("(-\\+)|(\\+-)");
        this.USELESS_PLUS_PATTERN_1 = Pattern.compile("([*/÷][()]*)\\+((?:\\d*" + this.functionsMul + "))");
        this.USELESS_PLUS_PATTERN_2 = Pattern.compile("^\\(*\\+(\\(*(?:\\d+" + this.functionsPlus + "))");
        this.ADD_MUL_BETWEEN_BRACKETS_PATTERN = Pattern.compile("(\\)+)(\\(+)");
        this.NORMALIZE_BRACKETS_PATTERN = Pattern.compile("\\(([^()]*)\\)");
        this.DELETE_SINGLE_OBJECT_BRACKETS_PATTERN = Pattern.compile("\\(([-+*/\\\\÷]*(?:\\d*" + this.functionsMul + ")[-+]*)\\)");
        this.RETURN_USEFUL_BRACKETS_PATTERN = Pattern.compile("\\[([^\\[\\]]*)]");
    }

    @Override
    public List<String> extract(List<String> rawList) {
        return rawList.stream()
                .flatMap(line -> findComputableExpressions(line).stream())
                .map(this::deleteSpaces)
                .map(this::deleteSingleObjectBrackets)
                .map(this::saveUsefulBrackets)
                .map(this::deleteAllBrackets)
                .map(this::returnUsefulBrackets)
                .map(this::convertRedundantMinuses)
                .map(this::removeRedundantPluses)
                .map(this::chooseSign)
                .map(this::removeUselessPlus)
                .map(this::addMulBetweenBrackets)
                .map(this::notEmpty)
                .collect(Collectors.toList());
    }

    public List<String> findComputableExpressions(String input) {
        String regex;
        if (functionsPlus.isEmpty()) {
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*"
                    + "((?:[\\s()\\-+]*\\d+[ ()]*(?:[+\\-*÷/][ ()\\-+]*\\d+[ ()]*)+)+)"
                    + "|"
                    + "((?:\\s*[()\\-+]*\\s*[()\\-+]{2,})+\\d+[()\\s]*)";
        } else {
            // Если functionsPlus не пустой, используем расширенный шаблон
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*"
                    + "((?:[\\s()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*"
                    + "(?:[+\\-*÷/][ ()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*)+)+)"
                    + "|"
                    + "((?:\\s*[()\\-+]*\\s*[()\\-+]{2,})+(?:\\d+" + functionsPlus + ")[()\\s]*"
                    + "|((-*\\s*)*)" + functionsPlus.substring(1) + ")";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        List<String> expressions = new ArrayList<>();
        while (matcher.find()) {
            StringBuilder expression = new StringBuilder();
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (group != null) {
                    expression.append(group);
                }
            }
            expressions.add(expression.toString());
        }
        return expressions;
    }



    public String deleteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    /**
     * Удаляет "одиночные" скобки вокруг объектов (определяется шаблоном
     * DELETE_SINGLE_OBJECT_BRACKETS_PATTERN).
     */
    public String deleteSingleObjectBrackets(String input) {
        return normalizePattern(input, DELETE_SINGLE_OBJECT_BRACKETS_PATTERN, "$1");
    }

    /**
     * Меняет (...) на [...] для сохранения "полезных" скобок и дальнейшего возврата.
     */
    public String saveUsefulBrackets(String input) {
        return normalizePattern(input, NORMALIZE_BRACKETS_PATTERN, "[$1]");
    }

    /**
     * Удаляет вообще все круглые скобки.
     */
    public String deleteAllBrackets(String input) {
        return input.replaceAll("[()]", "");
    }

    /**
     * Возвращает полезные скобки (заменяет [...] обратно на (...)).
     */
    public String returnUsefulBrackets(String input) {
        return normalizePattern(input, RETURN_USEFUL_BRACKETS_PATTERN, "($1)");
    }

    /**
     * Преобразует лишние минусы вида -(-) или -+- в + и т.д.
     */
    public String convertRedundantMinuses(String input) {
        return normalizePattern(input, REDUNDANT_MINUS_PATTERN, "$1+");
    }

    /**
     * Убирает повторяющиеся плюсы (+++ -> +).
     */
    public String removeRedundantPluses(String input) {
        return normalizePattern(input, REDUNDANT_PLUS_PATTERN, "+");
    }

    /**
     * Если встречается -+ или +- подряд, выбираем знак "-".
     */
    public String chooseSign(String input) {
        return normalizePattern(input, CHOOSE_SIGN_PATTERN, "-");
    }

    /**
     * Убирает бесполезный плюс (например, перед числами или выражениями).
     */
    public String removeUselessPlus(String input) {
        // Здесь применяем replaceAll с уже готовыми паттернами
        input = input.replaceAll(USELESS_PLUS_PATTERN_1.pattern(), "$1$2");
        input = input.replaceAll(USELESS_PLUS_PATTERN_2.pattern(), "$1");
        return input;
    }

    /**
     * Вставляет знак умножения между закрывающими и открывающимися скобками, где нужно.
     */
    public String addMulBetweenBrackets(String input) {
        return normalizePattern(input, ADD_MUL_BETWEEN_BRACKETS_PATTERN, "$1*$2");
    }

    /**
     * Делает выражение "не пустым": если в итоге убирается вся часть до \d+functionsPlus,
     * добавляем "+0".
     */
    public String notEmpty(String input) {
        String other = input.replaceFirst("(.*)\\d+" + functionsPlus + "(.*)", "$1$2");
        if (other.isEmpty()) {
            return input + "+0";
        }
        return input;
    }

    /**
     * Универсальный метод для повторяющейся замены паттерна в цикле:
     * пока есть что заменить, заменяем.
     */
    private String normalizePattern(String input, Pattern pattern, String replacement) {
        String modified;
        do {
            modified = input;
            input = pattern.matcher(input).replaceAll(replacement);
        } while (!input.equals(modified));
        return input;
    }
}
