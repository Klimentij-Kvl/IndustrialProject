package org.example.RegexProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class FindExpression {

    //Поиск выражения, возможного для подсчёта
    public static List<String> findComputableExpressions(String input) {
        List<String> expressions = new ArrayList<>();

        Pattern pattern = Pattern.compile("([\\s\\(\\)\\-\\+]*\\d+[ \\(\\)]*([\\+\\-\\*\\÷\\/][ \\(\\)\\-\\+]*\\d+[ \\(\\)]*)+)+");
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
        String modified;
        do {
            modified = input;
            input = input.replaceAll("\\(([^\\(\\)]*)\\)", "[$1]");
        } while (!input.equals(modified));

        return input;
    }

    //Удаление () в строке  (Удаление бесполезных скобок)
    public static String deleteSingleObjectBrackets(String input) {
        String modified;
        do {
            modified = input;
            input = input.replaceAll("\\(([-+*/\\÷]*?\\d*[-+]*)\\)", "$1");
        } while (!input.equals(modified));

        return input;
    }

    //Замена [] на () в строке (Возвращение полезных скобок)
    public static String returnUsefulBrackets(String input) {
        String modified;
        do {
            modified = input;
            input = input.replaceAll("\\[([^\\[\\]]*)\\]", "($1)");
        } while (!input.equals(modified));

        return input;
    }

    //Удаление всех ()   (Удаление лишних скобок)
    public static String deleteAllBrackets(String input) {
        return input.replaceAll("[()]", ""); // Убираем все скобки
    }

    //Замена -- в +
    public static String convertRedundantMinuses(String input) {
        String modified;
        do {
            modified = input;
            input = input.replaceAll("-([+]*?)-", "$1+");
        } while (!input.equals(modified));
        return input;
    }

    //Замена +...+ в +
    public static String removeRedundantPluses(String input) {
        return input.replaceAll("\\+\\++", "+");
    }

    //Замена +- на -
    public static String chooseSign(String input) {
        String modified;
        do {
            modified = input;
            input = input.replaceAll("(\\-\\+)|(\\+\\-)", "-");
        } while (!input.equals(modified));
        return input;
    }
    //Удаление + не влияющих на выражение
    public static String removeUslessPlus(String input) {
        input = input.replaceAll("([*/\\÷][\\(\\)]*)\\+(\\d*)", "$1$2");
        input = input.replaceAll("(\\D\\(*)*\\+(\\(*\\d*)", "$1$2");
        return input;
    }

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
        List<String> expressions = input.stream()
                .flatMap(line -> findComputableExpressions(line).stream()) //Находим допустимые выражения
                .map(FindExpression::deleteSpaces)                         //Удаляем пробелы
                .map(FindExpression::deleteSingleObjectBrackets)           //Удаляем бесполезные скобки
                .map(FindExpression::saveUsefulBrackets)                   //Сохранение взаимодействующих
                .map(FindExpression::deleteAllBrackets)                    //Удаление остальных скобок
                .map(FindExpression::returnUsefulBrackets)                 //Возвращение сохранённых скобок
                .map(FindExpression::convertRedundantMinuses)              //Превращение избыточных минусов в плюсы
                .map(FindExpression::removeRedundantPluses)                //Удаление избыточных плюсов
                .map(FindExpression::chooseSign)                           //Выбор знака в случае -+
                .map(FindExpression::removeUslessPlus)                     //Удаление остаточных ненужных плюсов
                .collect(Collectors.toList());
        return expressions;
    }
}
