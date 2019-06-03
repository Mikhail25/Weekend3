package com.example.weekendassigment3weatherapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weekendassigment3weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherSettingsActivity extends AppCompatActivity {
    private static final String TAG = "WeatherSettingsActivity";


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ZIP_INPUT = "zip_input";
    public static final String UNIT_SELECT = "unit_select";
    public static final String UNIT_SELECT_POS = "unit_select_pos";

    @BindView(R.id.setting_toolbar)
    Toolbar settingToolbar;
    @BindView(R.id.et_zipcode_input)
    EditText et_zipcode;
    @BindView(R.id.spinner_degree_input)
    Spinner degreeUnitPick;
    @BindView(R.id.btn_save_settings)
    Button saveSettings;

    Integer zipCodeInput;
    String degreeSearchInput;
    Integer degreeSelectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_settings);
        ButterKnife.bind(this);

        //Setting toolbar
        setSupportActionBar(settingToolbar);
        getSupportActionBar().setTitle("Umbrella");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
        updateViews();


        //Setting spinner
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(
                this, R.array.units_degree, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        degreeUnitPick.setAdapter(spinAdapter);

        degreeUnitPick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String unitSelection = parent.getItemAtPosition(position).toString();
                degreeSelectedPos = position;

                switch (position) {
                    case 0:
                        degreeSearchInput = "metric";
                        break;
                    case 1:
                        degreeSearchInput = "imperial";
                        break;
                }
                Log.d(TAG, "onItemSelected: " + degreeSearchInput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        zipCodeInput = sharedPreferences.getInt(ZIP_INPUT, 30350);
        Log.d(TAG, "loadData: "+zipCodeInput);
        
        degreeSelectedPos = sharedPreferences.getInt(UNIT_SELECT_POS, 1);
        Log.d(TAG, "loadData: ");
    }

    private void updateViews() {
        et_zipcode.setText(zipCodeInput.toString());
        degreeUnitPick.setSelection(degreeSelectedPos);
    }

    private void saveData() {
        String zip_checker = et_zipcode.getText().toString();

        if(zip_checker.length() == 5){
            try {
                zipCodeInput = Integer.parseInt(zip_checker);
            }catch (NumberFormatException e){
                Toast.makeText(this,"Number Format is not valid", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"You must enter exactly 5 numbers for the zip code", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "saveData: "+zipCodeInput+", "+degreeSearchInput);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ZIP_INPUT, zipCodeInput);
        editor.putString(UNIT_SELECT, degreeSearchInput);
        editor.putInt(UNIT_SELECT_POS,degreeSelectedPos);
        editor.commit();

        Intent resultIntent = new Intent();
        setResult(RESULT_OK);

        Toast.makeText(this,"Data Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}