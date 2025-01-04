package org.example.UIProcessor.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Main;
import org.example.UIProcessor.UIProcessor;

import java.io.IOException;

public class GUI extends Application implements UIProcessor {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        primaryStage.setTitle("Industrial Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(){
        launch();
    }

    @Override
    public void stop(){
        Platform.exit();
    }

    @Override
    public String getInputFile(){return "";}

    @Override
    public String getOutputFile(){return "";}
}
