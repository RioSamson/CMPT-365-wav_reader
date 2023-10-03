package com.example.cmpt_365_wav_reader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * IMPORTANT DOCS AND TODO
 * Resources:
 * http://www.labbookpages.co.uk/audio/javaWavFiles.html
 * https://docs.oracle.com/javase/8/docs/technotes/guides/sound/programmer_guide/contents.html
 * https://www.youtube.com/watch?v=dcw8n6rcBuY
 * https://www.reddit.com/r/AskProgramming/comments/4rkd2h/javafx_displaying_the_waveform_of_an_audio_file/
 * https://github.com/goxr3plus/Java-Audio-Wave-Spectrum-API
 * https://stackoverflow.com/questions/5210147/reading-wav-file-in-java
 */

//IMPORTANT: Changed Scene builder settings: SceneBuilder > app > SceneBuilder.cfg > changed "java-options=-Djpackage.app-version=20.0.0" to null in the next line because scene builder was writing the wrong code
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("wav reader");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}