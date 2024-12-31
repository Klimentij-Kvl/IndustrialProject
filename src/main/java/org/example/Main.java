package org.example;

import com.google.common.reflect.ClassPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.UIProcessor.GUI.Controller;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Industrial Project");
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}