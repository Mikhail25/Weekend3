package com.example.weekendassigment3weatherapp.view;

import com.example.weekendassigment3weatherapp.model.WeatherPojo;

public interface ViewContract {
    void sendWeatherDataRequest(Integer zip_code, String degree_unit);
    void getWeatherInfo(WeatherPojo dataSet);
    void onError(String error);
    void bindPresenter();
}
