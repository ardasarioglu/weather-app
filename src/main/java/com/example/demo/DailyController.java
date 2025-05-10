package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class DailyController {
    Stage stage;
    Scene scene;
    Parent root;

    private JSONObject weatherJSON;

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

    public void goHourly(ActionEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("hourly.fxml"));
        Parent root=loader.load();
        HourlyController hourlyController=loader.getController();
        hourlyController.setWeatherJSON(this.weatherJSON);

        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        if(this.weatherJSON!=null){
            JSONObject jsonObject=this.weatherJSON;
            JSONObject daily=jsonObject.getJSONObject("daily");
            JSONArray times=daily.getJSONArray("time");
            JSONArray weatherCodes=daily.getJSONArray("weather_code");
            JSONArray maxTemperature=daily.getJSONArray("temperature_2m_max");
            JSONArray minTemperature=daily.getJSONArray("temperature_2m_min");
            JSONArray meanTemperature=daily.getJSONArray("temperature_2m_mean");
            JSONArray maxApparentTemperature=daily.getJSONArray("apparent_temperature_max");
            JSONArray minApparentTemperature=daily.getJSONArray("apparent_temperature_min");
            JSONArray meanApparentTemperature=daily.getJSONArray("apparent_temperature_mean");
            JSONArray windDirection=daily.getJSONArray("wind");

        }
    }





}
