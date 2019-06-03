package com.example.weekendassigment3weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherPojo {


    @SerializedName("list")
    @Expose
    private List<WeatherList> weatherList = null;
    @SerializedName("city")
    @Expose
    private City city;

    public List<WeatherList> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}