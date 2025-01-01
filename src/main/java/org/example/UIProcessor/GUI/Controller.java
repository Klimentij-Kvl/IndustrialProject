package org.example.UIProcessor.GUI;

import com.google.common.reflect.ClassPath;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.DataProcessor.RegexProcessor.RegexProcessor;
import org.example.FileProcessor.DiffReader.DiffFileReader.TxtDiffFileReader;
import org.example.FileProcessor.DiffReader.DiffReader;
import org.example.FileProcessor.DiffWriter.DiffFileWriter.TxtDiffFileWriter;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    private List<String> list = new ArrayList<>();
    @FXML
    private TextField inputPath;
    @FXML
    private TextField outputPath;
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
    public void ClickRead(){
        try(DiffReader dr = new TxtDiffFileReader(inputPath.getText())){
            list = dr.read();
            StringBuilder sb = new StringBuilder();
            for(String s : list)
                sb.append(s).append("\n");
            fileArea.setText(sb.toString());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void ClickWrite(){
        try(DiffWriter dw = new TxtDiffFileWriter(outputPath.getText())){
            dw.write(list);
        }catch (IOException e){
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
