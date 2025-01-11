package org.example.Web;

import java.util.List;

public class ProcessRequestDto {
    private List<String> rawList;
    private String extractorName;
    private String calculatorName;
    private String replacerName;

    public List<String> getRawList() {
        return rawList;
    }
    public void setRawList(List<String> rawList) {
        this.rawList = rawList;
    }
    public String getExtractorName() {
        return extractorName;
    }
    public void setExtractorName(String extractorName) {
        this.extractorName = extractorName;
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
