package org.example.UIProcessor.GUI;

import com.google.common.reflect.ClassPath;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.DataProcessor.DataProcessor;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private TextField inputPath, outputPackagePath, outputFileName, inputParam1, inputParam2, outputParam1, outputParam2;
    @FXML
    private ChoiceBox<String> inputType, outputType, inputOption1, inputOption2, outputOption1, outputOption2;
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

    private void remakeChoiceBoxWithClasses(ChoiceBox<String> toModify, String packageName, String pattern) throws IOException{
        toModify.getItems().add(null);
        Set<Class<?>> DiffFileWriterClasses =
                getConcreteClassesInPackage("org.example.FileProcessor." + packageName);
        for(Class<?> clazz : DiffFileWriterClasses){
            Matcher matcher = Pattern.compile(pattern).matcher(clazz.getSimpleName());
            if(matcher.matches())
                toModify.getItems().add(matcher.group(1));
        }
    }
    @FXML
    public void initialize() throws IOException{
        remakeChoiceBoxWithClasses(inputType, "DiffReader.DiffFileReader", "^(.+)DiffFileReader$");
        remakeChoiceBoxWithClasses(inputOption1, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");
        remakeChoiceBoxWithClasses(inputOption2, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");

        remakeChoiceBoxWithClasses(outputType, "DiffWriter.DiffFileWriter", "^(.+)DiffFileWriter$");
        remakeChoiceBoxWithClasses(outputOption1, "DiffWriter.DiffWriterDecorator", "^(.+)DiffWriterDecorator$");
        remakeChoiceBoxWithClasses(outputOption2, "DiffWriter.DiffWriterDecorator", "^(.+)DiffWriterDecorator$");
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
    public void ClickEdit(){
        DataProcessor proc = new RegexProcessor();

        String[] strings = fileArea.getText().split("\n");
        List<String> newList = Arrays.asList(strings);
        newList = proc.process(newList);

        StringBuilder sb = new StringBuilder();
        for(String s : newList)
            sb.append(s).append("\n");
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        fileArea.setText(sb.toString());
    }

    @FXML
    public void ClickInputFile(){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("src/resources/"));
        chooser.setTitle("Choose an input file:");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "all files", "*.txt", "*.xml", "*.json", "*.yaml", "*.zip", "*.tar"));
        try{
            inputPath.setText(chooser.showOpenDialog(new Stage()).getAbsolutePath());
        }catch (NullPointerException e){
            inputPath.setText("");
        }
    }

    @FXML
    public void ClickOutDir(){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose an output folder:");
        chooser.setInitialDirectory(new File("src/"));
        try{
            outputPackagePath.setText(chooser.showDialog(new Stage()).getAbsolutePath() + "\\");
        }catch (NullPointerException e){
            outputPackagePath.setText("");
        }
    }
}
