package org.example.DataProcessor.RegexProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FindExpression {

    //Поиск выражения, возможного выражения для подсчёта
    public static List<String> findComputableExpressions(String input) {
        List<String> expressions = new ArrayList<>();

        Pattern pattern = Pattern.compile("([\\s()\\-+]*\\d+[ ()]*([+\\-*÷/][ ()\\-+]*\\d+[ ()]*)+)+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            expressions.add(matcher.group());
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
        return normalizePattern(input,"\\(([-+*/\\÷]*?\\d*[-+]*)\\)", "$1");
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
        input = input.replaceAll("([*/÷][()]*)\\+(\\d*)", "$1$2");
        input = input.replaceAll("^\\(*\\+(\\(*\\d+)", "$1");
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

    //Получение итоговых выражений
    public static List<String> findExpression(List<String> input) {
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
                .map(FindExpression::removeUselessPlus)                     //Удаление остаточных ненужных плюсов
                .collect(Collectors.toList());
    }
}
