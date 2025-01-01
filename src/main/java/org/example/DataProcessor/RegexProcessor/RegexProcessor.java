package org.example.DataProcessor.RegexProcessor;

import org.example.DataBase.DataStorage;
import org.example.DataProcessor.DataProcessor;

import java.util.ArrayList;
import java.util.List;

public class RegexProcessor implements DataProcessor {

    @Override
    public List<String> process(List<String> data) {
        MakeFuncExpression makeFuncExpression = new MakeFuncExpression();
        makeFuncExpression.makeFunctionsString();                               // Создаём выражения для функции
        List<String> expressions = extractExpressions(data);                    // Извлекаем выражения
        List<String> calculatedExpressions = calculateExpressions(expressions); // Вычисляем выражения
        return replaceExpressionsInData(data, calculatedExpressions);           // Заменяем выражения в данных
    }

    @Override
    public boolean CreateFunction(String functionName, String formula) {
        DataStorage dataStorage = DataStorage.getInstance();
        dataStorage.addFunction(functionName, formula);
        return true;
    }

    List<String> extractExpressions(List<String> data) {
        FindExpression findExpression = new FindExpression();
        return new ArrayList<>(findExpression.find(data));
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
        DataStorage dataStorage = DataStorage.getInstance();
        String functionsPlus = dataStorage.getFunctionsPlus();
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
        for (String line : data) {
            String modifiedLine;
            do {
                if (index >= calculatedExpressions.size()) {
                    replacedExpressions.add(line);
                    break;
                }

                modifiedLine = line.replaceFirst(
                        regex,
                        " " + calculatedExpressions.get(index) + " "
                );

                if (modifiedLine.equals(line)) {
                    replacedExpressions.add(line);
                    break;
                } else {
                    index++;
                    line = modifiedLine;
                }
            } while (true);
        }
        return replacedExpressions;
    }
}