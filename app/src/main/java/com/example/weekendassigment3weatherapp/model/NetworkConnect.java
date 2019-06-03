package com.example.weekendassigment3weatherapp.model;

import android.util.Log;

import com.example.weekendassigment3weatherapp.R;
import com.example.weekendassigment3weatherapp.presenter.Presenter;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnect {
    private static final String TAG = "NetworkConnect";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public final String API_KEY = "59522776659ba117cd7e6a6ffa40c063";
    //59522776659ba117cd7e6a6ffa40c063
    private static NetworkConnect instance;

    public Presenter presenter;
    WeatherApi weatherApi;

    private NetworkConnect() {
    }

    public static NetworkConnect getInstance() {
        if (instance == null) {
            instance = new NetworkConnect();
        }
        return instance;
    }

    public void setPresenter(Presenter presenter) {
        instance.presenter = presenter;
    }

    public void initRetrofit(Integer zip_code, String degree_unit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherApi = retrofit.create(WeatherApi.class);

        weatherApi.getWeatherInfo(zip_code,degree_unit)
                .enqueue(new Callback<WeatherPojo>() {
                    @Override
                    public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                        if(response.body() == null){
                            presenter.netFailure("Invalid zipcode, please try again...");
                            return;
                        }
                            presenter.netSucess(response.body());

                    }


                    @Override
                    public void onFailure(Call<WeatherPojo> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                        presenter.netFailure(t.getMessage());
                    }
                });

    }
}
