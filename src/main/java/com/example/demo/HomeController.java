package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomeController {
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    ImageView view1;
    @FXML
    TextField searchField;
    @FXML
    Label tempLabel;
    @FXML
    Label weatherLabel;
    @FXML
    Label apperantTempLabel;
    @FXML
    Label precipitationLabel;
    @FXML
    Label windSpeedLabel;
    @FXML
    Label windDirectionLabel;

    private JSONObject weatherJSON;

    public void setWeatherJSON(JSONObject weatherJSON){
        this.weatherJSON=weatherJSON;
        updateScreen();
    }

    public void goDaily(ActionEvent event) throws IOException {
        root=FXMLLoader.load(getClass().getResource("daily.fxml"));
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

    public void updateScreen(){
        if(this.weatherJSON!=null){
            JSONObject current=this.weatherJSON.getJSONObject("current");
            int weatherCode=current.getInt("weather_code");
            double temp=current.getDouble("temperature_2m");
            double apparentTemp=current.getDouble("apparent_temperature");
            double precipitation=current.getDouble("precipitation");
            double windSpeed=current.getDouble("wind_speed_10m");
            int windDirection=current.getInt("wind_direction_10m");

            WeatherNode currentWeather=new WeatherNode(weatherCode, windDirection, temp, apparentTemp, windSpeed, precipitation);
            tempLabel.setText(currentWeather.getMeanTemp()+"°C");
            apperantTempLabel.setText("Apperant Temperature: "+currentWeather.getApparentMeanTemp()+"°C");
            precipitationLabel.setText("Precipitation Probability: %"+currentWeather.getPrecipProbabilty());
            windSpeedLabel.setText("Wind Speed: "+currentWeather.getWindSpeed()+" km/h");
            windDirectionLabel.setText("Wind Direction: "+currentWeather.getWindDirection());
            switch (currentWeather.getWeatherCode()) {
                case 0:
                    weatherLabel.setText("Clear Sky");
                    break;
                case 1:
                    weatherLabel.setText("Mainly Clear");
                    break;
                case 2:
                    weatherLabel.setText("Partly Cloudy");
                    break;
                case 3:
                    weatherLabel.setText("Overcast");
                    break;
                case 45:
                    weatherLabel.setText("Fog");
                    break;
                case 48:
                    weatherLabel.setText("Depositing Rime Fog");
                    break;
                case 51:
                    weatherLabel.setText("Light Drizzle");
                    break;
                case 53:
                    weatherLabel.setText("Drizzle");
                    break;
                case 55:
                    weatherLabel.setText("Heavy Drizzle");
                    break;
                case 56:
                    weatherLabel.setText("Light Freezing Drizzle");
                    break;
                case 57:
                    weatherLabel.setText("Heavy Freezing Drizzle");
                    break;
                case 61:
                    weatherLabel.setText("Light Rain");
                    break;
                case 63:
                    weatherLabel.setText("Moderate Rain");
                    break;
                case 65:
                    weatherLabel.setText("Heavy Rain");
                    break;
                case 66:
                    weatherLabel.setText("Light Freezing Rain");
                    break;
                case 67:
                    weatherLabel.setText("Heavy Freezing Rain");
                    break;
                case 71:
                    weatherLabel.setText("Light Snow");
                    break;
                case 73:
                    weatherLabel.setText("Moderate Snow");
                    break;
                case 75:
                    weatherLabel.setText("Heavy Snow");
                    break;
                case 77:
                    weatherLabel.setText("Snow Grains");
                    break;
                case 80:
                    weatherLabel.setText("Light Rain Showers");
                    break;
                case 81:
                    weatherLabel.setText("Moderate Rain Showers");
                    break;
                case 82:
                    weatherLabel.setText("Heavy Rain Showers");
                    break;
                case 85:
                    weatherLabel.setText("Light Snow Showers");
                    break;
                case 86:
                    weatherLabel.setText("Heavy Snow Showers");
                    break;
                case 95:
                    weatherLabel.setText("Thunderstorm");
                    break;
                case 96:
                    weatherLabel.setText("Thunderstorm with Light Hail");
                    break;
                case 99:
                    weatherLabel.setText("Thunderstorm with Heavy Hail");
                    break;
                default:
                    weatherLabel.setText("Unknown Weather");
                    break;
            }
        }
    }

    @FXML
    public void search() throws IOException, InterruptedException {
        this.weatherJSON=CreateJSON.createJSON(searchField.getText());

        JSONObject current=this.weatherJSON.getJSONObject("current");
        int weatherCode=current.getInt("weather_code");
        double temp=current.getDouble("temperature_2m");
        double apparentTemp=current.getDouble("apparent_temperature");
        double precipitation=current.getDouble("precipitation");
        double windSpeed=current.getDouble("wind_speed_10m");
        int windDirection=current.getInt("wind_direction_10m");

        WeatherNode currentWeather=new WeatherNode(weatherCode, windDirection, temp, apparentTemp, windSpeed, precipitation);
        tempLabel.setText(currentWeather.getMeanTemp()+"°C");
        apperantTempLabel.setText("Apperant Temperature: "+currentWeather.getApparentMeanTemp()+"°C");
        precipitationLabel.setText("Precipitation Probability: %"+currentWeather.getPrecipProbabilty());
        windSpeedLabel.setText("Wind Speed: "+currentWeather.getWindSpeed()+" km/h");
        windDirectionLabel.setText("Wind Direction: "+currentWeather.getWindDirection());
        switch (currentWeather.getWeatherCode()){
            case 0:
                weatherLabel.setText("Clear Sky");
                break;
            case 1:
                weatherLabel.setText("Mainly Clear");
                break;
            case 2:
                weatherLabel.setText("Partly Cloudy");
                break;
            case 3:
                weatherLabel.setText("Overcast");
                break;
            case 45:
                weatherLabel.setText("Fog");
                break;
            case 48:
                weatherLabel.setText("Depositing Rime Fog");
                break;
            case 51:
                weatherLabel.setText("Light Drizzle");
                break;
            case 53:
                weatherLabel.setText("Drizzle");
                break;
            case 55:
                weatherLabel.setText("Heavy Drizzle");
                break;
            case 56:
                weatherLabel.setText("Light Freezing Drizzle");
                break;
            case 57:
                weatherLabel.setText("Heavy Freezing Drizzle");
                break;
            case 61:
                weatherLabel.setText("Light Rain");
                break;
            case 63:
                weatherLabel.setText("Moderate Rain");
                break;
            case 65:
                weatherLabel.setText("Heavy Rain");
                break;
            case 66:
                weatherLabel.setText("Light Freezing Rain");
                break;
            case 67:
                weatherLabel.setText("Heavy Freezing Rain");
                break;
            case 71:
                weatherLabel.setText("Light Snow");
                break;
            case 73:
                weatherLabel.setText("Moderate Snow");
                break;
            case 75:
                weatherLabel.setText("Heavy Snow");
                break;
            case 77:
                weatherLabel.setText("Snow Grains");
                break;
            case 80:
                weatherLabel.setText("Light Rain Showers");
                break;
            case 81:
                weatherLabel.setText("Moderate Rain Showers");
                break;
            case 82:
                weatherLabel.setText("Heavy Rain Showers");
                break;
            case 85:
                weatherLabel.setText("Light Snow Showers");
                break;
            case 86:
                weatherLabel.setText("Heavy Snow Showers");
                break;
            case 95:
                weatherLabel.setText("Thunderstorm");
                break;
            case 96:
                weatherLabel.setText("Thunderstorm with Light Hail");
                break;
            case 99:
                weatherLabel.setText("Thunderstorm with Heavy Hail");
                break;
            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }

    }


}
