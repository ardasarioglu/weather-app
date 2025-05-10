package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPropertyIgnore;

import java.io.IOException;

public class DailyController {
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    Label tempLabel;
    @FXML
    Label weatherLabel;
    @FXML
    Label maxTempLabel;
    @FXML
    Label minTempLabel;
    @FXML
    Label maxApperantTempLabel;
    @FXML
    Label minApperantTempLabel;
    @FXML
    Label apperantTempLabel;
    @FXML
    Label precipitationLabel;
    @FXML
    Label windSpeedLabel;
    @FXML
    Label windDirectionLabel;
    @FXML
    Button button1;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button button4;
    @FXML
    Button button5;
    @FXML
    Button button6;
    @FXML
    Button button7;
    @FXML
    ImageView view1;
    Queue queue=new Queue();


    private JSONObject weatherJSON;

    public void setWeatherJSON(JSONObject weatherJSON){
        this.weatherJSON=weatherJSON;
        updateScreen();
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

    public void updateScreen(){
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
            JSONArray windDirection=daily.getJSONArray("wind_direction_10m_dominant");
            JSONArray windSpeed=daily.getJSONArray("wind_speed_10m_mean");
            JSONArray precProbability=daily.getJSONArray("precipitation_probability_max");


            button1.setText(times.getString(0).substring(times.getString(0).length()-2));
            button2.setText(times.getString(1).substring(times.getString(1).length()-2));
            button3.setText(times.getString(2).substring(times.getString(2).length()-2));
            button4.setText(times.getString(3).substring(times.getString(3).length()-2));
            button5.setText(times.getString(4).substring(times.getString(4).length()-2));
            button6.setText(times.getString(5).substring(times.getString(5).length()-2));
            button7.setText(times.getString(6).substring(times.getString(6).length()-2));

            for(int i=0;i<7;i++){
                this.queue.addNode(new WeatherNode(weatherCodes.getInt(i), windDirection.getInt(i), minTemperature.getDouble(i), meanTemperature.getDouble(i), maxTemperature.getDouble(i), minApparentTemperature.getDouble(i), meanApparentTemperature.getDouble(i), maxApparentTemperature.getDouble(i), windSpeed.getDouble(i), precProbability.getDouble(i)));
            }
        }
    }
    public void action1(){
        WeatherNode head=this.queue.getHead();
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));

        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }

    }
    public void action2(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<1;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }

    public void action3(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<2;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }
    public void action4(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<3;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }
    public void action5(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<4;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }
    public void action6(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<5;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }
    public void action7(){
        WeatherNode head=this.queue.getHead();
        for(int i=0;i<6;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText("Max Temperature: "+(head.getMaxTemp())+" °C");
        minTempLabel.setText("Min Temperature: "+(head.getMinTemp())+" °C");
        maxApperantTempLabel.setText("Max Apperant Temperature: "+(head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText("Min Apperant Temperature: "+(head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText("Apperant Temperature: "+(head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText("Precipitation Probability: "+(head.getPrecipProbabilty()));
        windSpeedLabel.setText("Wind Speed: "+(head.getWindSpeed()));
        windDirectionLabel.setText("Wind Direction: "+(head.getWindDirection()));
        switch (head.getWeatherCode()) {
            case 0:
            case 1:
                weatherLabel.setText("Clear");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                break;

            case 2:
            case 3:
                weatherLabel.setText("Cloudy");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                break;

            case 45:
            case 48:
                weatherLabel.setText("Fog");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                break;

            case 51:
            case 53:
            case 55:
            case 56:
            case 57:
                weatherLabel.setText("Drizzle");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                break;

            case 61:
            case 63:
            case 65:
            case 66:
            case 67:
                weatherLabel.setText("Rain");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                break;

            case 71:
            case 73:
            case 75:
            case 77:
                weatherLabel.setText("Snow");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                break;

            case 80:
            case 81:
            case 82:
                weatherLabel.setText("Rain Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                break;

            case 85:
            case 86:
                weatherLabel.setText("Snow Showers");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                break;

            case 95:
            case 96:
            case 99:
                weatherLabel.setText("Thunderstorm");
                view1.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                break;

            default:
                weatherLabel.setText("Unknown Weather");
                break;
        }
    }




}
