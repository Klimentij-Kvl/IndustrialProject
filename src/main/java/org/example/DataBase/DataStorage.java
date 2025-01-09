package org.example.DataBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private List<String> input;
    private List<String> output;
    private String inputFileName;
    private String outputFileName;
    private String inputFileFormat;
    private String outputFileFormat;
    private String functionsMul="";
    private String functionsPlus="";
    private Map<String, String> functions;

    private DataStorage() {
        input = new ArrayList<>();
        output = new ArrayList<>();
        functions = new HashMap<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<String> getInput() {
        return input;
    }
    public void setInput(List<String> input) {
        this.input = input;
    }

    public List<String> getOutput() {
        return output;
    }
    public void setOutput(List<String> output) {
        this.output = output;
    }

    public String getInputFileName() {
        return inputFileName;
    }
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getInputFileFormat() {
        return inputFileFormat;
    }
    public void setInputFileFormat(String inputFileFormat) {
        this.inputFileFormat = inputFileFormat;
    }

    public String getOutputFileFormat() {
        return outputFileFormat;
    }
    public void setOutputFileFormat(String outputFileFormat) {
        this.outputFileFormat = outputFileFormat;
    }

    public Map<String, String> getFunctions() {
        return functions;
    }
    public void setFunctions(Map<String, String> functions) {
        this.functions = functions;
    }
    public void addFunction(String functionName, String functionValue) {
        functions.put(functionName, functionValue);
    }

    public String getFunctionsMul() {
        return functionsMul;
    }
    public void setFunctionsMul(String functionsMul) {
        this.functionsMul = functionsMul;
    }

    public String getFunctionsPlus() {
        return functionsPlus;
    }
    public void setFunctionsPlus(String functionsPlus) {
        this.functionsPlus = functionsPlus;
    }
}
