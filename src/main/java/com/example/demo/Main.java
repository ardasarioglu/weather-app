package com.example.demo;
//deneme ddşlkdlg

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root=FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene=new Scene(root);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}