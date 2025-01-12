package org.example.DataProcessor.Calculator;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculatorAdapter implements Calculator{
    @Override
    public List<String> calculate(List<String> expressions){
        List<String> calculatedExpressions = new ArrayList<>();
        for (String expression : expressions){
            calculatedExpressions.add(
                            calculate(ReplaceAddedFunctionsToExpressions(expression)));
        }
        return calculatedExpressions;
    }

    protected abstract String ReplaceAddedFunctionsToExpressions(String rawExpression);
}
