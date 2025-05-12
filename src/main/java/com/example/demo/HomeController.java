package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    @FXML
    ImageView arrow;

    private JSONObject weatherJSON;

    public void setWeatherJSON(JSONObject weatherJSON){
        this.weatherJSON=weatherJSON;
        updateScreen();
    }

    public void goDaily(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("daily.fxml"));
        Parent root=loader.load();
        DailyController dailyController=loader.getController();
        dailyController.setWeatherJSON(this.weatherJSON);

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
            apperantTempLabel.setText(currentWeather.getApparentMeanTemp()+"°C");
            precipitationLabel.setText(currentWeather.getPrecipProbabilty()+" %");
            windSpeedLabel.setText(currentWeather.getWindSpeed()+" m/s");
            arrow.setRotate(90+currentWeather.getWindDirection());
            switch (currentWeather.getWeatherCode()) {
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

    @FXML
    public void search() throws IOException, InterruptedException {
        try{
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
            apperantTempLabel.setText(currentWeather.getApparentMeanTemp()+"°C");
            precipitationLabel.setText(currentWeather.getPrecipProbabilty()+" %");
            windSpeedLabel.setText(currentWeather.getWindSpeed()+" m/s");
            arrow.setRotate(90+currentWeather.getWindDirection());
            switch (currentWeather.getWeatherCode()) {
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
        catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Invalid Location!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }





    }


}
