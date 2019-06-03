package com.example.weekendassigment3weatherapp.presenter;

import android.util.Log;

import com.example.weekendassigment3weatherapp.model.NetworkConnect;
import com.example.weekendassigment3weatherapp.model.Weather;
import com.example.weekendassigment3weatherapp.model.WeatherPojo;
import com.example.weekendassigment3weatherapp.view.ViewContract;

import java.util.List;

public class Presenter implements PresenterContract {
    private static final String TAG = "Presenter";

    private ViewContract view;
    private WeatherPojo dataSet;


    @Override
    public void bindView(ViewContract view) {
        this.view = view;
    }

    @Override
    public void unBindView() {
        view = null;
        dataSet = null;
    }

    @Override
    public void initRetrofit(Integer zip_code, String degree_unit) {
        Log.d(TAG, "initRetrofit: ");
        NetworkConnect network = NetworkConnect.getInstance();
        network.setPresenter(this);
        network.initRetrofit(zip_code, degree_unit);
    }

    @Override
    public void netSucess(WeatherPojo dataSet) {
        Log.d(TAG, "netSucess: ");
        view.getWeatherInfo(dataSet);
    }

    @Override
    public void netFailure(String error) {
        view.onError(error);
    }
}
