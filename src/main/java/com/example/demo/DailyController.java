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
    @FXML
    ImageView arrow;
    LinkedList linkedList =new LinkedList();


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


            String date1[]=times.getString(0).split("-");
            String date2[]=times.getString(1).split("-");
            String date3[]=times.getString(2).split("-");
            String date4[]=times.getString(3).split("-");
            String date5[]=times.getString(4).split("-");
            String date6[]=times.getString(5).split("-");
            String date7[]=times.getString(6).split("-");

            String months[]={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            button1.setWrapText(true);
            button2.setWrapText(true);
            button3.setWrapText(true);
            button4.setWrapText(true);
            button5.setWrapText(true);
            button6.setWrapText(true);
            button7.setWrapText(true);

            button1.setText(months[Integer.parseInt(date1[1])-1]+"\n"+date1[2]);
            button2.setText(months[Integer.parseInt(date2[1])-1]+"\n"+date2[2]);
            button3.setText(months[Integer.parseInt(date3[1])-1]+"\n"+date3[2]);
            button4.setText(months[Integer.parseInt(date4[1])-1]+"\n"+date4[2]);
            button5.setText(months[Integer.parseInt(date5[1])-1]+"\n"+date5[2]);
            button6.setText(months[Integer.parseInt(date6[1])-1]+"\n"+date6[2]);
            button7.setText(months[Integer.parseInt(date7[1])-1]+"\n"+date7[2]);

            for(int i=0;i<7;i++){
                this.linkedList.addNode(new WeatherNode(weatherCodes.getInt(i), windDirection.getInt(i), minTemperature.getDouble(i), meanTemperature.getDouble(i), maxTemperature.getDouble(i), minApparentTemperature.getDouble(i), meanApparentTemperature.getDouble(i), maxApparentTemperature.getDouble(i), windSpeed.getDouble(i), precProbability.getDouble(i)));
            }
        }
    }
    public void action1(){
        WeatherNode head=this.linkedList.getHead();
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());

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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<1;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<2;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<3;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<4;i++){
            head=head.next;
        }

        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<5;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
        WeatherNode head=this.linkedList.getHead();
        for(int i=0;i<6;i++){
            head=head.next;
        }
        tempLabel.setText((head.getMeanTemp())+" °C");
        maxTempLabel.setText((head.getMaxTemp())+" °C");
        minTempLabel.setText((head.getMinTemp())+" °C");
        maxApperantTempLabel.setText((head.getApparentMaxTemp())+" °C");
        minApperantTempLabel.setText((head.getApparentMinTemp())+" °C");
        apperantTempLabel.setText((head.getApparentMeanTemp())+" °C");
        precipitationLabel.setText((head.getPrecipProbabilty())+" %");
        windSpeedLabel.setText((head.getWindSpeed())+" m/s");
        arrow.setRotate(90+head.getWindDirection());
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
