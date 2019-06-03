package com.example.weekendassigment3weatherapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weekendassigment3weatherapp.R;
import com.example.weekendassigment3weatherapp.model.WeatherPojo;
import com.example.weekendassigment3weatherapp.presenter.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewContract{

    @BindView(R.id.toolbar_main)
    public Toolbar weatherToolbar;

    @BindView(R.id.tv_city_name)
    public TextView cityName;
    @BindView(R.id.tv_city_degree_day)
    public TextView cityDegrees;
    @BindView(R.id.tv_city_weather_stat)
    public TextView cityWeatherStatus;
    @BindView(R.id.iv_setting_button)
    public ImageView imageSettingsButton;

    private static final String TAG = "MainActivity";

    /**
     * For temperature in Fahrenheit use units=imperial
     * For temperature in Celsius use units=metric
     * Temperature in Kelvin is used by default, no need to use units parameter in API call
     */
    private Integer zip_code;
    private String degree_unit;
    String temp_format = null;
    int averageTemp = 60;

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding time!
        ButterKnife.bind(this);

        //Setting the weather toolbar
        setSupportActionBar(weatherToolbar);
        bindPresenter();

        loadData();



        sendWeatherDataRequest(zip_code, degree_unit);

        imageSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                WeatherSettingsActivity.SHARED_PREFS, MODE_PRIVATE);
        zip_code = sharedPreferences.getInt(WeatherSettingsActivity.ZIP_INPUT,30350);
        degree_unit = sharedPreferences.getString(WeatherSettingsActivity.UNIT_SELECT,"metric");

    }

    private void openSetting() {
        Intent settingIntent = new Intent(this,WeatherSettingsActivity.class);
        startActivity(settingIntent);
        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
    }


    @Override
    public void sendWeatherDataRequest(Integer zip_code, String degree_unit) {
        switch (degree_unit){
            case "imperial":
                temp_format = "\u2109";
                averageTemp = 140;
                break;
            case "metric":
                temp_format = "\u2103";
                averageTemp = 60;
        }


        Log.d(TAG, "sendWeatherDataRequest: sending: "+zip_code+", and "+degree_unit);
        presenter.initRetrofit(zip_code, degree_unit);
    }

    @Override
    public void getWeatherInfo(WeatherPojo dataSet) {
        String city = dataSet.getCity().getName();
        String country = dataSet.getCity().getCountry();

        int current_temp = (int)Math.round(dataSet.getWeatherList().get(0).getMain().getTemp());
        String weather_stat = dataSet.getWeatherList().get(0).getWeather().get(0).getMain();

        Log.d(TAG, "getWeatherInfo: got city name: "+city);
        cityName.setText(city+", "+country);
        cityDegrees.setText(current_temp+temp_format);
        cityWeatherStatus.setText(weather_stat);

        weatherToolbar.setBackgroundColor(current_temp < averageTemp ?
                getResources().getColor(R.color.colorCold): getResources().getColor(R.color.colorHot));
    }



    @Override
    public void onError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void bindPresenter() {
        presenter = new Presenter();
        presenter.bindView(this);
    }
}
