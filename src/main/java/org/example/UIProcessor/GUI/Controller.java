package org.example.UIProcessor.GUI;

import com.google.common.reflect.ClassPath;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {
    private List<String> list = new ArrayList<>();
    @FXML
    private TextField inputPath, outputPath;
    @FXML
    private ChoiceBox<String> inputType, outputType;
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


    @FXML
    public void initialize() throws IOException{
        Set<Class<?>> DiffFileWriterClasses =
                getConcreteClassesInPackage("org.example.FileProcessor.DiffReader.DiffFileReader");
        Pattern patternWriter = Pattern.compile("^(.*)DiffFileReader$");
        for(Class<?> clazz : DiffFileWriterClasses){
            Matcher matcher = patternWriter.matcher(clazz.getSimpleName());
            if(matcher.matches())
                inputType.getItems().add(matcher.group(1));
        }

        Set<Class<?>> DiffFileReaderClasses =
                getConcreteClassesInPackage("org.example.FileProcessor.DiffWriter.DiffFileWriter");
        Pattern patternReader = Pattern.compile("^(.*)DiffFileWriter$");
        for(Class<?> clazz : DiffFileReaderClasses){
            Matcher matcher = patternReader.matcher(clazz.getSimpleName());
            if(matcher.matches())
                outputType.getItems().add(matcher.group(1));
        }
    }

    @FXML
    public void ClickRead(){
        try{
            Class<?> clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffFileReader."
                    + inputType.getValue() +  "DiffFileReader");
            try(DiffReader dr = (DiffReader)clazz
                    .getConstructor(String.class).newInstance(inputPath.getText())){
                list = dr.read();
                StringBuilder sb = new StringBuilder();
                for(String s : list)
                    sb.append(s).append("\n");
                fileArea.setText(sb.toString());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void ClickWrite(){
        try{
            Class<?> clazz = Class.forName("org.example.FileProcessor.DiffWriter.DiffFileWriter."
                    + outputType.getValue() +  "DiffFileWriter");
            try(DiffWriter dw = (DiffWriter)clazz
                    .getConstructor(String.class).newInstance(outputPath.getText())){
                dw.write(list);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void ClickEdit(){
        RegexProcessor regexProcessor = new RegexProcessor();
        list = regexProcessor.process(list);
        StringBuilder sb = new StringBuilder();
        for(String s : list)
            sb.append(s).append("\n");
        fileArea.setText(sb.toString());
    }
}
