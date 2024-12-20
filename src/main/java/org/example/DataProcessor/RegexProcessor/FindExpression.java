package org.example.DataProcessor.RegexProcessor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.example.DataBase.DataStorage;
public class FindExpression {
    static private String functionsMul = "";
    static private String functionsPlus = "";

    public static int countUniqueLettersExcludingFunctions(String text) {
        text = text.toLowerCase();

        text = text.replaceAll("(\\d+" + functionsPlus + ")", "");

        Pattern pattern = Pattern.compile("[a-zа-яё]");
        Matcher matcher = pattern.matcher(text);

        Set<Character> uniqueLetters = new HashSet<>();

        while (matcher.find()) {
            uniqueLetters.add(matcher.group().charAt(0));
        }

        return uniqueLetters.size();
    }

    static void makeFunctionsString() {
        DataStorage dataStorage = DataStorage.getInstance();
        Map<String, String> mapFunc = dataStorage.getFunctions();
        StringBuilder stringBuilder = new StringBuilder(functionsMul);
        StringBuilder stringBuilder1 = new StringBuilder(functionsPlus);
        for (String key : mapFunc.keySet()) {
            stringBuilder.append("|(?:").append(key).append("\\(+\\d+");
            stringBuilder1.append("|(?:").append(key).append("\\(+\\d+");
            int count = countUniqueLettersExcludingFunctions(mapFunc.get(key));
            for (int i = 1; i < count; i++) {
                stringBuilder.append(",\\d+");
                stringBuilder1.append(",\\d+");
            }
            stringBuilder.append("\\)+)*");
            stringBuilder1.append("\\)+)+");
        }
        functionsMul = stringBuilder.toString();
        functionsPlus = stringBuilder1.toString();
    }
    //Поиск выражения, возможного выражения для подсчёта
    public static List<String> findComputableExpressions(String input) {
        List<String> expressions = new ArrayList<>();

        Pattern pattern = Pattern.compile("(?:\\((?:[ -'*-0Z^-zА-яёЁ]*,+[ -'*-Z^-zА-яёЁ]*)+\\))*([\\s()\\-+]*\\d+" + functionsPlus + "[ ()]*(?:[+\\-*÷/][ ()\\-+]*\\d+" + functionsPlus + "[ ()]*)+)+|(\\s*[()\\-+]*\\s*[()\\-+]{2,})+\\d+" + functionsPlus + "[()\\s]*|((-*\\s*)*" + functionsPlus.substring(1) + ")");
        Matcher matcher = pattern.matcher(input);

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

    //Удаление пробелов в строке
    public static String deleteSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    //Замена () на [] в строке (Сохранение полезных скобок)
    public static String saveUsefulBrackets(String input) {
        return normalizePattern(input,"\\(([^\\(\\)]*)\\)", "[$1]");
    }

    //Удаление () в строке  (Удаление бесполезных скобок)
    public static String deleteSingleObjectBrackets(String input) {
        return normalizePattern(input,"\\(([-+*/\\÷]*(?:\\d*" + functionsMul + ")[-+]*)\\)", "$1");
    }

    //Замена [] на () в строке (Возвращение полезных скобок)
    public static String returnUsefulBrackets(String input) {
        return normalizePattern(input,"\\[([^\\[\\]]*)\\]", "($1)");
    }

    //Удаление всех ()   (Удаление лишних скобок)
    public static String deleteAllBrackets(String input) {
        return input.replaceAll("[()]", ""); // Убираем все скобки
    }

    //Замена -- в +
    public static String convertRedundantMinuses(String input) {
        return normalizePattern(input,"-([+]*?)-", "$1+");
    }

    //Замена +...+ в +
    public static String removeRedundantPluses(String input) {
        return input.replaceAll("\\+\\++", "+");
    }

    //Замена +- на -
    public static String chooseSign(String input) {
        return normalizePattern(input,"(\\-\\+)|(\\+\\-)", "-");
    }

    //Удаление + не влияющих на выражение
    public static String removeUselessPlus(String input) {
        input = input.replaceAll("([*/÷+-][()]*)\\+(\\d*" + functionsMul + ")", "$1$2");
        input = input.replaceAll("^\\(*\\+(\\(*\\d+" + functionsPlus + ")", "$1");
        return input;
    }

    //Циклическая обработка регулярным выражением, пока есть изменения
    private static String normalizePattern(String input, String regex, String replacement) {
        String modified;
        do {
            modified = input;
            input = input.replaceAll(regex, replacement);
        } while (!input.equals(modified));
        return input;
    }

    public static String notEmpty(String input) {String other = input.replaceFirst("(.*)\\d+" + functionsPlus + "(.*)", "$1$2");
        if(other.isEmpty()) {
            return input + "+0";
        }
        return input;
    }
    public static String addMulBetweenBrackets(String input) {
        return input.replaceAll("(\\)+)(\\(+)", "$1*$2");
    }
    //Получение итоговых выражений
    public static List<String> find(List<String> input) {
        makeFunctionsString();
        //Находим допустимые выражения
        //Удаляем пробелы
        //Удаляем бесполезные скобки
        //Сохранение взаимодействующих
        //Удаление остальных скобок
        //Возвращение сохранённых скобок
        //Превращение избыточных минусов в плюсы
        //Удаление избыточных плюсов
        //Выбор знака в случае -+
        //Удаление остаточных ненужных плюсов
        return input.stream()
                .flatMap(line -> findComputableExpressions(line).stream()) //Находим допустимые выражения
                .map(FindExpression::deleteSpaces)                         //Удаляем пробелы
                .map(FindExpression::deleteSingleObjectBrackets)           //Удаляем бесполезные скобки
                .map(FindExpression::saveUsefulBrackets)                   //Сохранение взаимодействующих
                .map(FindExpression::deleteAllBrackets)                    //Удаление остальных скобок
                .map(FindExpression::returnUsefulBrackets)                 //Возвращение сохранённых скобок
                .map(FindExpression::convertRedundantMinuses)              //Превращение избыточных минусов в плюсы
                .map(FindExpression::removeRedundantPluses)                //Удаление избыточных плюсов
                .map(FindExpression::chooseSign)                           //Выбор знака в случае -+
                .map(FindExpression::removeUselessPlus)                    //Удаление остаточных ненужных плюсов
                .map(FindExpression::addMulBetweenBrackets)                //Добавление умножения между скобками
                .map(FindExpression::notEmpty)
                .collect(Collectors.toList());
    }
}
