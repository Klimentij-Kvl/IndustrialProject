package org.example.Web;

import java.util.List;

public class ProcessRequestDto {
    private List<String> rawList;
    private String extracterName;
    private String calculatorName;
    private String replacerName;

    public List<String> getRawList() {
        return rawList;
    }
    public void setRawList(List<String> rawList) {
        this.rawList = rawList;
    }
    public String getExtracterName() {
        return extracterName;
    }
    public void setExtracterName(String extracterName) {
        this.extracterName = extracterName;
    }
    public String getCalculatorName() {
        return calculatorName;
    }
    public void setCalculatorName(String calculatorName) {
        this.calculatorName = calculatorName;
    }
    public String getReplacerName() {
        return replacerName;
    }
    public void setReplacerName(String replacerName) {
        this.replacerName = replacerName;
    }

}
