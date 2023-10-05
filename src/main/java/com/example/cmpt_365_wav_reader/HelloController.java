package com.example.cmpt_365_wav_reader;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
//    @FXML
//    private Label welcomeText;

    private Stage stage;
    private Scene scene;
    private Parent root;

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

    public void switchToMenuScreens(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("wavReader.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 900, 400);
        stage.setScene(scene);
        stage.show();

    }
}