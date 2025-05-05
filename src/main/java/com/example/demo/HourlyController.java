package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class HourlyController {
    Stage stage;
    Scene scene;
    Parent root;

    JSONObject weatherJSON;

    public void setWeatherJSON(JSONObject weatherJSON){
        this.weatherJSON=weatherJSON;
    }


    public void goHome(ActionEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root=loader.load();
        HomeController homeController=loader.getController();
        homeController.setWeatherJSON(this.weatherJSON);
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goDaily(ActionEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("daily.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        if(weatherJSON!=null){
            JSONObject jsonObject=weatherJSON;
            JSONObject hourly=jsonObject.getJSONObject("hourly");
            JSONArray times=hourly.getJSONArray("time");
            JSONArray weatherCodes=hourly.getJSONArray("weather_code");
            JSONArray windSpeeds=hourly.getJSONArray("wind_speed_10m");
            JSONArray temperatures=hourly.getJSONArray("temperature_2m");
            JSONArray apparentTemperatures=hourly.getJSONArray("apparent_temperature");
            JSONArray preciProbabs=hourly.getJSONArray("precipitation_probability");
            JSONArray windDirections=hourly.getJSONArray("wind_direction_10m");
            Queue queue=new Queue();
            for(int i=0;i<72;i++){
                queue.addNode(new WeatherNode(times.getString(i), weatherCodes.getInt(i), windDirections.getInt(i), temperatures.getDouble(i), apparentTemperatures.getDouble(i), windSpeeds.getDouble(i), preciProbabs.getDouble(i)));
            }
        }



    }




}
