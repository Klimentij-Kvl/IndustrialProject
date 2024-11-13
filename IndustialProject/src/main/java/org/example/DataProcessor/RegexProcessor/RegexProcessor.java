package org.example.DataProcessor.RegexProcessor;

import java.util.ArrayList;
import java.util.List;

public class RegexProcessor implements DataProcessor {

    @Override
    public List<String> process(List<String> data) {
        List<String> expressions = extractExpressions(data);                    // Извлекаем выражения
        List<String> calculatedExpressions = calculateExpressions(expressions); // Вычисляем выражения
        return replaceExpressionsInData(data, calculatedExpressions);           // Заменяем выражения в данных
    }

    List<String> extractExpressions(List<String> data) {
        return new ArrayList<>(FindExpression.find(data));
    }
    List<String> calculateExpressions(List<String> expressions) {
        return expressions;
    }
    List<String> replaceExpressionsInData(List<String> data, List<String> calculatedExpressions) {
        return data;
    }
}