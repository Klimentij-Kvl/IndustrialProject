package org.example.UIProcessor.GUI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.ClassPath;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Extractor.Extractor;
import org.example.DataProcessor.Replacer.Replacer;
import org.example.DataProcessorFactory;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffWriter.DiffWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {
    private List<String> list = new ArrayList<>();
    @FXML
    private TextField inputPath, outputPackagePath, outputFileName,
            inputParam1, inputParam2, outputParam1, outputParam2,
            funcNameField, formulaField;
    @FXML
    private ChoiceBox<String> inputType, outputType, inputOption1,
            inputOption2, outputOption1, outputOption2,
            extractorChoice, calculatorChoice, replacerChoice;
    @FXML
    private TextArea fileArea;

    private Set<Class<?>> getConcreteClassesInPackage(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase(packageName))
                .map(ClassPath.ClassInfo::load)
                .filter(load -> !Modifier.isAbstract(load.getModifiers()))
                .collect(Collectors.toSet());
    }

    private Set<Class<?>> getConcreteClassesWithInterface(Class<?> interfaceClass) throws IOException{
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getTopLevelClassesRecursive("org.example")
                .stream()
                .map(ClassPath.ClassInfo::load)
                .filter(interfaceClass::isAssignableFrom)
                .filter(load -> !Modifier.isAbstract(load.getModifiers()))
                .collect(Collectors.toSet());
    }

    private void remakeFilesChoiceBoxWithClasses(ChoiceBox<String> toModify, String packageName, String pattern) throws IOException{
        toModify.getItems().add(null);
        Set<Class<?>> Classes =
                getConcreteClassesInPackage("org.example.FileProcessor." + packageName);
        for(Class<?> clazz : Classes){
            Matcher matcher = Pattern.compile(pattern).matcher(clazz.getSimpleName());
            if(matcher.matches())
                toModify.getItems().add(matcher.group(1));
        }
    }

    private void remakeDataProcessorChoiceBoxWithClasses(ChoiceBox<String> toModify, Class<?> interfaceClass) throws IOException{
        toModify.getItems().add(null);
        Set<Class<?>> Classes = getConcreteClassesWithInterface(interfaceClass);
        for(Class<?> clazz : Classes){
            toModify.getItems().add(clazz.getSimpleName());
        }
    }

    @FXML
    public void initialize() throws IOException{
        remakeFilesChoiceBoxWithClasses(inputType, "DiffReader.DiffFileReader", "^(.+)DiffFileReader$");
        remakeFilesChoiceBoxWithClasses(inputOption1, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");
        remakeFilesChoiceBoxWithClasses(inputOption2, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");

        remakeFilesChoiceBoxWithClasses(outputType, "DiffWriter.DiffFileWriter", "^(.+)DiffFileWriter$");
        remakeFilesChoiceBoxWithClasses(outputOption1, "DiffWriter.DiffWriterDecorator", "^(.+)DiffWriterDecorator$");
        remakeFilesChoiceBoxWithClasses(outputOption2, "DiffWriter.DiffWriterDecorator", "^(.+)DiffWriterDecorator$");

        remakeDataProcessorChoiceBoxWithClasses(extractorChoice, Extractor.class);
        remakeDataProcessorChoiceBoxWithClasses(calculatorChoice, Calculator.class);
        remakeDataProcessorChoiceBoxWithClasses(replacerChoice, Replacer.class);
    }

    private DiffReader decorateDiffReader(DiffReader dr, ChoiceBox<String> option, TextField param) throws Exception{
        if (option.getValue() != null) {
            Class<?> clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffReaderDecorator."
                    + option.getValue() + "DiffReaderDecorator");
            try {
                dr = (DiffReader) clazz.getConstructor(DiffReader.class)
                        .newInstance(dr);
            }catch (NoSuchMethodException e){
                dr = (DiffReader) clazz.getConstructor(String.class, DiffReader.class)
                        .newInstance(param.getText(), dr);
            }
        }

        return dr;
    }

    @FXML
    public void ClickRead(){
        if(inputType.getValue() != null) {
            try {
                Class<?> clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffFileReader."
                        + inputType.getValue() + "DiffFileReader");
                DiffReader dr = (DiffReader) clazz
                        .getConstructor(String.class).newInstance(inputPath.getText());
                dr = decorateDiffReader(dr, inputOption1, inputParam1);
                dr = decorateDiffReader(dr, inputOption2, inputParam2);

                list = dr.read();

                StringBuilder sb = new StringBuilder();
                for (String s : list)
                    sb.append(s).append("\n");
                fileArea.setText(sb.toString());

                dr.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private DiffWriter decorateDiffWriter(DiffWriter dw, ChoiceBox<String> option, TextField param) throws Exception{
        if(option.getValue() != null){
            Class<?> clazz = Class.forName("org.example.FileProcessor.DiffWriter.DiffWriterDecorator."
                    + option.getValue() + "DiffWriterDecorator");
            try {
                dw = (DiffWriter) clazz.getConstructor(DiffWriter.class)
                        .newInstance(dw);
            }catch (NoSuchMethodException e){
                dw = (DiffWriter) clazz.getConstructor(String.class, DiffWriter.class)
                        .newInstance(param.getText(), dw);
            }
        }

        return dw;
    }

    @FXML
    public void ClickWrite(){
        if(outputType.getValue() != null){
            try{
                Class<?> clazz = Class.forName("org.example.FileProcessor.DiffWriter.DiffFileWriter."
                        + outputType.getValue() + "DiffFileWriter");
                DiffWriter dw = (DiffWriter) clazz.getConstructor(String.class).newInstance(
                        outputPackagePath.getText() + outputFileName.getText()
                                + "." + outputType.getValue().toLowerCase());
                dw = decorateDiffWriter(dw, outputOption1, outputParam1);
                dw = decorateDiffWriter(dw, outputOption2, outputParam2);

                String[] strings = fileArea.getText().split("\n");
                List<String> newList = Arrays.asList(strings);

                dw.write(newList);
                dw.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void ClickEdit() {
        try {
            OkHttpClient client = new OkHttpClient();

            String[] strings = fileArea.getText().split("\n");
            List<String> rawList = Arrays.asList(strings);

            ObjectMapper objectMapper = new ObjectMapper();
            String rawListJson = objectMapper.writeValueAsString(rawList);

            String extractorName = "org.example.DataProcessor.Extractor." + extractorChoice.getValue();
            String calculatorName = "org.example.DataProcessor.Calculator." + calculatorChoice.getValue();
            String replacerName = "org.example.DataProcessor.Replacer." + replacerChoice.getValue();

            String json = String.format("""
                {
                    "rawList": %s,
                    "extractorName": "%s",
                    "calculatorName": "%s",
                    "replacerName": "%s"
                }
                """, rawListJson, extractorName, calculatorName, replacerName);

            RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url("https://corovinus.online/api/v1/calc/process")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, IOException e) {
                    Platform.runLater(() -> fileArea.setText("Ошибка: " + e.getMessage()));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String responseBody = response.body().string();
                        List<String> resultList = objectMapper.readValue(responseBody, new TypeReference<List<String>>() {});
                        Platform.runLater(() -> {
                            StringBuilder sb = new StringBuilder();
                            for (String s : resultList) {
                                sb.append(s).append("\n");
                            }
                            if (!sb.isEmpty()) {
                                sb.deleteCharAt(sb.lastIndexOf("\n"));
                            }
                            fileArea.setText(sb.toString());
                        });
                    } else {
                        Platform.runLater(() -> fileArea.setText("Ошибка: " + response.message()));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            fileArea.setText("Произошла ошибка: " + e.getMessage());
        }
    }

    @FXML
    public void ClickInputFile(){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("src/resources/"));
        chooser.setTitle("Choose an input file:");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "all files", "*.*"));
        try{
            inputPath.setText(chooser.showOpenDialog(new Stage()).getAbsolutePath());
        }catch (NullPointerException e){
            inputPath.setText("");
        }
    }

    @FXML
    public void ClickOutputDir(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose an output folder:");
        chooser.setInitialDirectory(new File("src/"));
        try{
            outputPackagePath.setText(chooser.showDialog(new Stage()).getAbsolutePath() + "\\");
        }catch (NullPointerException e){
            outputPackagePath.setText("");
        }
    }

    @FXML
    public void makeFunc(){
        DataProcessorFactory.addFunction(funcNameField.getText(), formulaField.getText());
    }
}
