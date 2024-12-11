package org.example.DataProcessor.RegexProcessor;

import org.example.DataProcessor.DataProcessor;

import java.util.ArrayList;
import java.util.List;

public class RegexProcessor implements DataProcessor {

    @Override
    public List<String> process(List<String> data) {
        List<String> expressions = extractExpressions(data);                    // Извлекаем выражения
        List<String> calculatedExpressions = calculateExpressions(expressions); // Вычисляем выражения
        return replaceExpressionsInData(data, calculatedExpressions);           // Заменяем выражения в данных
    }

    @Override
    public boolean CreateFunction(String functionName, String formula) {
        return false;
    }

    List<String> extractExpressions(List<String> data) {
        return new ArrayList<>(FindExpression.find(data));
    }
    List<String> calculateExpressions(List<String> expressions) {
        List<String> calculatedExpressions = new ArrayList<>();
        for (String expression : expressions) {
            calculatedExpressions.add(CalculateExpression.calculate(expression));
        }
        return calculatedExpressions;
    }

    List<String> replaceExpressionsInData(List<String> data, List<String> calculatedExpressions) {
        int index = 0;
        List<String> replacedExpressions = new ArrayList<>();
        for(String line : data) {
            String modifiedLine;
            do {
            modifiedLine = line.replaceFirst(
                    "([\\s()\\-+]*\\d+[ ()]*([+\\-*÷/][ ()\\-+]*\\d+[ ()]*)+)+|(\\s*[()\\-+]*\\s*[()\\-+]{2,})+\\d+[()\\s]*",
                    " " + calculatedExpressions.get(index) + " "
            );
            if (modifiedLine.equals(line)) {
                replacedExpressions.add(line);
                break;
            }
            else {
                index++;
                line = modifiedLine;
            }
            if (index == calculatedExpressions.size() - 1) {
                replacedExpressions.add(line);
                break;
            }
            } while (true);
            if (index == calculatedExpressions.size() - 1) {
                index = 0;
            }
        }
        return replacedExpressions;
    }
}