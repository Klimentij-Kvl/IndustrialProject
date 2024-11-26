package org.example.DataBase;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;

    private List<String> input;
    private List<String> output;
    private String inputFileName;
    private String outputFileName;
    private String inputFileFormat;
    private String outputFileFormat;
    private Database() {
        input = new ArrayList<>();
        output = new ArrayList<>();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
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

}
