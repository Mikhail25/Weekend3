package com.example.weekendassigment3weatherapp.presenter;

import com.example.weekendassigment3weatherapp.model.WeatherPojo;
import com.example.weekendassigment3weatherapp.view.ViewContract;

import java.util.List;

public interface PresenterContract {
    void bindView(ViewContract view);
    void initRetrofit(Integer zip_code, String degree_unit);
    void unBindView();
    void netSucess(WeatherPojo dataSet);
    void netFailure(String error);
}
