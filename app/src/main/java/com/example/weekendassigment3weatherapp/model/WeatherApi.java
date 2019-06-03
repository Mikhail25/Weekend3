package com.example.weekendassigment3weatherapp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {


    //https://api.openweathermap.org/data/2.5/forecast?
    // zip=30350&
    // units=imperial&
    // appid=59522776659ba117cd7e6a6ffa40c063
    @GET("forecast?appid=59522776659ba117cd7e6a6ffa40c063")
    Call<WeatherPojo> getWeatherInfo(@Query("zip") Integer zip_code,
                                     @Query("units") String degree_unit);

    @GET("forecast?&zip=30350")
    Call<WeatherPojo>getWeatherInfoNoZipCode(@Query("units") String degree_unit,
                                             @Query("appid") String appid);

    @GET("forecast?appid=59522776659ba117cd7e6a6ffa40c063&zip=30350&units=imperial")
    Call<WeatherPojo> getWeatherInfoNoQuerry();
}
