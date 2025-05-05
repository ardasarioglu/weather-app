package com.example.demo;

import java.time.LocalDateTime;

public class WeatherNode {
    private int weatherCode, windDirection;
    private double minTemp, meanTemp, maxTemp, apparentMinTemp, apparentMeanTemp, apparentMaxTemp, windSpeed, precipProbabilty;
    WeatherNode next;
    String time;

    public WeatherNode(int weatherCode, int windDirection, double minTemp, double meanTemp, double maxTemp, double apparentMinTemp, double apparentMeanTemp, double apparentMaxTemp, double windSpeed, double precipProbabilty) {
        this.weatherCode = weatherCode;
        this.windDirection = windDirection;
        this.minTemp = minTemp;
        this.meanTemp = meanTemp;
        this.maxTemp = maxTemp;
        this.apparentMinTemp = apparentMinTemp;
        this.apparentMeanTemp = apparentMeanTemp;
        this.apparentMaxTemp = apparentMaxTemp;
        this.windSpeed = windSpeed;
        this.precipProbabilty = precipProbabilty;
        this.next = null;
    }

    public WeatherNode(int weatherCode, int windDirection, double meanTemp, double apparentMeanTemp, double windSpeed, double precipProbabilty) {
        this.weatherCode = weatherCode;
        this.windDirection = windDirection;
        this.meanTemp = meanTemp;
        this.apparentMeanTemp = apparentMeanTemp;
        this.windSpeed = windSpeed;
        this.precipProbabilty = precipProbabilty;
        this.next = null;
    }

    public WeatherNode(String time, int weatherCode, int windDirection, double meanTemp, double apparentMeanTemp, double windSpeed, double precipProbabilty) {
        this.weatherCode = weatherCode;
        this.windDirection = windDirection;
        this.meanTemp = meanTemp;
        this.apparentMeanTemp = apparentMeanTemp;
        this.windSpeed = windSpeed;
        this.precipProbabilty = precipProbabilty;
        this.next = null;
        this.time=time;
    }


    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(double meanTemp) {
        this.meanTemp = meanTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getApparentMinTemp() {
        return apparentMinTemp;
    }

    public void setApparentMinTemp(double apparentMinTemp) {
        this.apparentMinTemp = apparentMinTemp;
    }

    public double getApparentMeanTemp() {
        return apparentMeanTemp;
    }

    public void setApparentMeanTemp(double apparentMeanTemp) {
        this.apparentMeanTemp = apparentMeanTemp;
    }

    public double getApparentMaxTemp() {
        return apparentMaxTemp;
    }

    public void setApparentMaxTemp(double apparentMaxTemp) {
        this.apparentMaxTemp = apparentMaxTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPrecipProbabilty() {
        return precipProbabilty;
    }

    public void setPrecipProbabilty(double precipProbabilty) {
        this.precipProbabilty = precipProbabilty;
    }

    public WeatherNode getNext() {
        return next;
    }

    public void setNext(WeatherNode next) {
        this.next = next;
    }
}
