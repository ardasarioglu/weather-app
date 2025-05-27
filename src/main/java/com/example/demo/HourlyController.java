package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HourlyController {
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    VBox vbox;
    @FXML
    Label date1, date2, date3;

    JSONObject weatherJSON;

    public void setWeatherJSON(JSONObject weatherJSON) throws IOException {
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

    public void goDaily(ActionEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("daily.fxml"));
        Parent root=loader.load();
        DailyController dailyController=loader.getController();
        dailyController.setWeatherJSON(this.weatherJSON);

        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public Queue tumAnchorPaneleriBul(Node root) {
        Queue sonuc = new Queue();
        tumAnchorPaneleriBulRecursive(root, sonuc);
        return sonuc;
    }

    private void tumAnchorPaneleriBulRecursive(Node node, Queue sonuc) {
        if (node instanceof AnchorPane) {
            sonuc.add((AnchorPane) node);
        }

        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                tumAnchorPaneleriBulRecursive(child, sonuc);
            }
        }

        if (node instanceof Accordion) {
            for (Node child : ((Accordion) node).getPanes()) {
                tumAnchorPaneleriBulRecursive(child, sonuc);
            }
        }

        if (node instanceof TitledPane) {
            Node content = ((TitledPane) node).getContent();
            if (content != null) {
                tumAnchorPaneleriBulRecursive(content, sonuc);
            }
        }
    }




    public void updateScreen() throws IOException {

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
            LinkedList linkedList =new LinkedList();
            for(int i=0;i<72;i++){
                linkedList.addNode(new WeatherNode(times.getString(i), weatherCodes.getInt(i), windDirections.getInt(i), temperatures.getDouble(i), apparentTemperatures.getDouble(i), windSpeeds.getDouble(i), preciProbabs.getDouble(i)));
            }
            String dateStr1=times.getString(0);
            String dateStr2=times.getString(24);
            String dateStr3=times.getString(48);

            DateTimeFormatter formatter1=DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter formatter2=DateTimeFormatter.ISO_DATE_TIME;
            DateTimeFormatter formatter3=DateTimeFormatter.ISO_DATE_TIME;

            LocalDateTime dateTime1=LocalDateTime.parse(dateStr1, formatter1);
            LocalDateTime dateTime2=LocalDateTime.parse(dateStr2, formatter2);
            LocalDateTime dateTime3=LocalDateTime.parse(dateStr3, formatter3);

            DayOfWeek dayOfWeek1=dateTime1.getDayOfWeek();
            DayOfWeek dayOfWeek2=dateTime2.getDayOfWeek();
            DayOfWeek dayOfWeek3=dateTime3.getDayOfWeek();

            String week1=dayOfWeek1.toString().substring(0,1).toUpperCase()+dayOfWeek1.toString().substring(1).toLowerCase();
            String week2=dayOfWeek2.toString().substring(0,1).toUpperCase()+dayOfWeek2.toString().substring(1).toLowerCase();
            String week3=dayOfWeek3.toString().substring(0,1).toUpperCase()+dayOfWeek3.toString().substring(1).toLowerCase();


            int gun1=dateTime1.getDayOfMonth();
            int gun2=dateTime2.getDayOfMonth();
            int gun3=dateTime3.getDayOfMonth();

            int ay_1=dateTime1.getMonthValue();
            int ay_2=dateTime2.getMonthValue();
            int ay_3=dateTime3.getMonthValue();

            String[] months={
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            };

            String ay1=months[ay_1-1];
            String ay2=months[ay_2-1];
            String ay3=months[ay_3-1];

            date1.setText(gun1+" "+ay1+", "+week1);
            date2.setText(gun2+" "+ay2+", "+week2);
            date3.setText(gun3+" "+ay3+", "+week3);



            WeatherNode head1= linkedList.getHead();
            Queue panes=tumAnchorPaneleriBul(vbox);
            NodeAnchor head2=panes.pop();

            while(head2!=null && head1!=null){
                AnchorPane anchorPane=head2.data;
                for(Node child: anchorPane.getChildren()){
                    if(child instanceof ImageView){
                        ImageView imageView=(ImageView) child;
                        if(imageView.getId().equals("icon")){
                            switch (head1.getWeatherCode()) {
                                case 0:
                                case 1:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/clear.png")));
                                    break;

                                case 2:
                                case 3:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/cloudy.png")));
                                    break;

                                case 45:
                                case 48:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/fog.png")));
                                    break;

                                case 51:
                                case 53:
                                case 55:
                                case 56:
                                case 57:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/drizzle.png")));

                                    break;

                                case 61:
                                case 63:
                                case 65:
                                case 66:
                                case 67:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rain.png")));
                                    break;

                                case 71:
                                case 73:
                                case 75:
                                case 77:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snow.png")));
                                    break;

                                case 80:
                                case 81:
                                case 82:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/rainshower.png")));
                                    break;

                                case 85:
                                case 86:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/snowshower.png")));
                                    break;

                                case 95:
                                case 96:
                                case 99:
                                    imageView.setImage(new Image(getClass().getResourceAsStream("/com/example/demo/icons/thunderstorm.png")));
                                    break;

                                default:
                                    break;
                            }
                        }
                        if(imageView.getId().equals("arrow")){
                            imageView.setRotate(90+head1.getWindDirection());
                        }

                    }
                    if(child instanceof Label){
                        Label label=(Label) child;
                        if(label.getText().contains("--  °C")){
                            label.setText(head1.getMeanTemp()+"  °C");
                        }
                        if(label.getText().contains("apparent temp")){
                            label.setText("Apparent Temperature: "+head1.getApparentMeanTemp()+" °C");
                        }
                        if(label.getText().contains("wind speed")){
                            label.setText("Wind Speed: "+head1.getWindSpeed()+" m/s");
                        }
                        if(label.getText().contains("precipitation probability")){
                            label.setText("Precipitation Probability: "+head1.getPrecipProbabilty()+" %");
                        }
                        if(label.getText().contains("weather code")){
                            switch (head1.getWeatherCode()) {
                                case 0:
                                case 1:
                                    label.setText("Clear");
                                    break;

                                case 2:
                                case 3:
                                    label.setText("Cloudy");
                                    break;

                                case 45:
                                case 48:
                                    label.setText("Fog");
                                    break;

                                case 51:
                                case 53:
                                case 55:
                                case 56:
                                case 57:
                                    label.setText("Drizzle");

                                    break;

                                case 61:
                                case 63:
                                case 65:
                                case 66:
                                case 67:
                                    label.setText("Rain");
                                    break;

                                case 71:
                                case 73:
                                case 75:
                                case 77:
                                    label.setText("Snow");
                                    break;

                                case 80:
                                case 81:
                                case 82:
                                    label.setText("Rain Showers");
                                    break;

                                case 85:
                                case 86:
                                    label.setText("Snow Showers");
                                    break;

                                case 95:
                                case 96:
                                case 99:
                                    label.setText("Thunderstorm");
                                    break;

                                default:
                                    label.setText("Unknown Weather");
                            }

                        }
                    }
                }
                head1=head1.next;
                head2=panes.pop();
            }





        }
    }
}
