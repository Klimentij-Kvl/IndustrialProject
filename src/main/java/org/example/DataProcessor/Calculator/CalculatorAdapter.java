package org.example.DataProcessor.Calculator;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculatorAdapter implements Calculator{
    @Override
    public List<String> calculate(List<String> expressions){
        List<String> calculatedExpressions = new ArrayList<>();
        for (String expression : expressions){
            calculatedExpressions.add(
                    calculate(
                            AddedFunctionsToExpressions(expression)));
        }
        return calculatedExpressions;
    }

    private String AddedFunctionsToExpressions(String rawExpression){
        //TODO: realize replacing user added functions to expressions
        return rawExpression;
    }
}
