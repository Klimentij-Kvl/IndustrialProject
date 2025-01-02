package org.example.UIProcessor.GUI;

import com.google.common.reflect.ClassPath;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import org.apache.commons.lang3.builder.Diff;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;
import org.example.FileProcessor.DiffReader.DiffReader;
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
    private TextField inputPath, outputPath, paramInput1, paramInput2;
    @FXML
    private ChoiceBox<String> inputType, outputType, decInput1, decInput2;
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
        remakeChoiceBoxWithClasses(decInput1, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");
        remakeChoiceBoxWithClasses(decInput2, "DiffReader.DiffReaderDecorator", "^(.+)DiffReaderDecorator$");

        remakeChoiceBoxWithClasses(outputType, "DiffWriter.DiffFileWriter", "^(.+)DiffFileWriter$");
    }

    @FXML
    public void ClickRead(){
        if(inputType.getValue() != null) {
            try {
                Class<?> clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffFileReader."
                        + inputType.getValue() + "DiffFileReader");

                DiffReader dr = (DiffReader) clazz
                        .getConstructor(String.class).newInstance(inputPath.getText());

                if (decInput1.getValue() != null) {
                    clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffReaderDecorator."
                            + decInput1.getValue() + "DiffReaderDecorator");
                    try {
                        dr = (DiffReader) clazz.getConstructor(DiffReader.class)
                                .newInstance(dr);
                    }catch (NoSuchMethodException e){
                        dr = (DiffReader) clazz.getConstructor(String.class, DiffReader.class)
                                .newInstance(paramInput1.getText(),dr);
                    }
                }

                if (decInput2.getValue() != null) {
                    clazz = Class.forName("org.example.FileProcessor.DiffReader.DiffReaderDecorator."
                            + decInput2.getValue() + "DiffReaderDecorator");
                    try {
                        dr = (DiffReader) clazz.getConstructor(DiffReader.class)
                                .newInstance(dr);
                    }catch (NoSuchMethodException e){
                        dr = (DiffReader) clazz.getConstructor(String.class, DiffReader.class)
                                .newInstance(paramInput2.getText(), dr);
                    }
                }

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
