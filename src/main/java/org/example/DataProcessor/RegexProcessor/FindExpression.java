package org.example.DataProcessor.RegexProcessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.example.DataBase.DataStorage;

public class FindExpression {

    private static final DataStorage dataStorage = DataStorage.getInstance();
    private static final String functionsMul = dataStorage.getFunctionsMul();
    private static final String functionsPlus = dataStorage.getFunctionsPlus();

    private static final Pattern REDUNDANT_MINUS_PATTERN = Pattern.compile("-([+]*?)-");
    private static final Pattern REDUNDANT_PLUS_PATTERN = Pattern.compile("\\+\\++");
    private static final Pattern CHOOSE_SIGN_PATTERN = Pattern.compile("(-\\+)|(\\+-)");
    private static final Pattern USELESS_PLUS_PATTERN_1 = Pattern.compile("([*/÷][()]*)\\+((?:\\d*" + functionsMul + "))");
    private static final Pattern USELESS_PLUS_PATTERN_2 = Pattern.compile("^\\(*\\+(\\(*(?:\\d+" + functionsPlus +"))");
    private static final Pattern ADD_MUL_BETWEEN_BRACKETS_PATTERN = Pattern.compile("(\\)+)(\\(+)");
    private static final Pattern NORMALIZE_BRACKETS_PATTERN = Pattern.compile("\\(([^()]*)\\)");
    private static final Pattern DELETE_SINGLE_OBJECT_BRACKETS_PATTERN = Pattern.compile("\\(([-+*/\\\\÷]*(?:\\d*" + functionsMul + ")[-+]*)\\)");
    private static final Pattern RETURN_USEFUL_BRACKETS_PATTERN = Pattern.compile("\\[([^\\[\\]]*)]");

    public List<String> findComputableExpressions(String input) {
        String regex;
        if (functionsPlus.isEmpty()) {
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*((?:[\\s()\\-+]*\\d+[ ()]*(?:[+\\-*÷/][ ()\\-+]*\\d+[ ()]*)+)+)|((?:\\s*[()\\-+]*\\s*[()\\-+]{2,})+\\d+[()\\s]*)";
        }
        else
        {
            regex = "(?:\\((?:[ -'*-Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*((?:[\\s()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*(?:[+\\-*÷/][ ()\\-+]*(?:\\d+" + functionsPlus + ")[ ()]*)+)+)|((?:\\s*[()\\-+]*\\s*[()\\-+]{2,})+(?:\\d+" + functionsPlus + ")[()\\s]*|((-*\\s*)*)" + functionsPlus.substring(1) + ")";
        }
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);
        List<String> expressions = new ArrayList<>();

        while (matcher.find()) {
            StringBuilder expression = new StringBuilder();
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    expression.append(matcher.group(i));
                }
            }
            expressions.add(expression.toString());
        }
        return expressions;
    }

    private String normalizePattern(String input, Pattern pattern, String replacement) {
        String modified;
        do {
            modified = input;
            input = pattern.matcher(input).replaceAll(replacement);
        } while (!input.equals(modified));
        return input;
    }

    public String deleteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    public String saveUsefulBrackets(String input) {
        return normalizePattern(input, NORMALIZE_BRACKETS_PATTERN, "[$1]");
    }

    public String deleteSingleObjectBrackets(String input) {

        return normalizePattern(input, DELETE_SINGLE_OBJECT_BRACKETS_PATTERN, "$1");
    }

    public String returnUsefulBrackets(String input) {
        return normalizePattern(input, RETURN_USEFUL_BRACKETS_PATTERN, "($1)");
    }

    public String deleteAllBrackets(String input) {
        return input.replaceAll("[()]", "");
    }

    public String convertRedundantMinuses(String input) {
        return normalizePattern(input, REDUNDANT_MINUS_PATTERN, "$1+");
    }

    public String removeRedundantPluses(String input) {
        return normalizePattern(input, REDUNDANT_PLUS_PATTERN, "+");
    }

    public String chooseSign(String input) {
        return normalizePattern(input, CHOOSE_SIGN_PATTERN, "-");
    }

    public String removeUselessPlus(String input) {
        input = input.replaceAll(USELESS_PLUS_PATTERN_1.toString(), "$1$2");
        input = input.replaceAll(USELESS_PLUS_PATTERN_2.toString(), "$1");
        return input;
    }

    public String addMulBetweenBrackets(String input) {
        return normalizePattern(input, ADD_MUL_BETWEEN_BRACKETS_PATTERN, "$1*$2");
    }

    public String notEmpty(String input) {
        String other = input.replaceFirst("(.*)\\d+" + functionsPlus + "(.*)", "$1$2");
        if (other.isEmpty()) {
            return input + "+0";
        }
        return input;
    }

    public List<String> find(List<String> input) {

        return input.stream()
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
}
