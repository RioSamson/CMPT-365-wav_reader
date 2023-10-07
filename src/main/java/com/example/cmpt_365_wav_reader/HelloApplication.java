package com.example.cmpt_365_wav_reader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//IMPORTANT: Changed Scene builder settings: SceneBuilder > app > SceneBuilder.cfg > changed "java-options=-Djpackage.app-version=20.0.0" to null in the next line because scene builder was writing the wrong code
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 650);
        stage.setTitle("wav reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}