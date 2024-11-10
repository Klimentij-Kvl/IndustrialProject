package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FindExpression {
    public static List<String> Find(String input) {
        List<String> list = new ArrayList<>();

        Pattern pattern = Pattern.compile("([\s\\(\\)\\-\\+]*\\d+[ \\(\\)]*([\\+\\-\\*\\÷\\/]{1}[ \\(\\)\\-\\+]*\\d+[ \\(\\)]*)+)+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String expression = matcher.group().replaceAll("\\s+", "");
            list.add(expression);
        }
        return list;
    }
}
