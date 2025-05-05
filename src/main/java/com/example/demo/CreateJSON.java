package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreateJSON {
    public static JSONObject createJSON(String city)throws IOException, InterruptedException {
        String geoCodingUrl="https://geocoding-api.open-meteo.com/v1/search?name="+city+"&count=1&language=tr&format=json";
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder().uri(URI.create(geoCodingUrl)).build();
        HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
        String tempJson=response.body();
        JSONObject tempJsonObject=new JSONObject(tempJson);
        JSONArray tempResultsArray=tempJsonObject.getJSONArray("results");
        JSONObject tempFirstResult=tempResultsArray.getJSONObject(0);
        double latitude=tempFirstResult.getDouble("latitude");
        double longitude=tempFirstResult.getDouble("longitude");

        String weatherUrl="https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&daily=weather_code,temperature_2m_max,temperature_2m_min,temperature_2m_mean,apparent_temperature_max,apparent_temperature_min,apparent_temperature_mean,wind_direction_10m_dominant,wind_speed_10m_mean,precipitation_probability_max&hourly=weather_code,wind_speed_10m,temperature_2m,apparent_temperature,precipitation_probability,wind_direction_10m&current=temperature_2m,weather_code,apparent_temperature,precipitation,wind_speed_10m,wind_direction_10m&timezone=auto";
        HttpRequest weatherRequest= HttpRequest.newBuilder().uri(URI.create(weatherUrl)).build();
        HttpResponse<String> weatherResponse= client.send(weatherRequest, HttpResponse.BodyHandlers.ofString());

        JSONObject weatherJSON=new JSONObject(weatherResponse.body());

        return weatherJSON;


    }

}
