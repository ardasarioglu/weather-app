package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DailyController {
    Stage stage;
    Scene scene;
    Parent root;


    public void goHome(ActionEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("home.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goHourly(ActionEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("hourly.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
