package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsScreen extends AppCompatActivity {

    NumberPicker restNumberPicker;
    TextView restDurationText;
    int restDurationValue;
    public static int defaultValue = 10;
    //switches
    Switch switchaktolga,switchvibration,switchsound;
    Boolean booleanaktolga,booleanvibration,booleansound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        restNumberPicker = findViewById(R.id.restNumberPicker);
        restDurationText=findViewById(R.id.restDurationText);
        restNumberPicker.setMinValue(10);
        restNumberPicker.setMaxValue(500);
        restNumberPicker.setValue(defaultValue);
        restDurationText.setText("Rest Duration: " + defaultValue);
        restNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                restDurationValue = newVal;
                restDurationText.setText("Rest Duration: " + restDurationValue );
            }
        });
        switchaktolga = findViewById(R.id.aktolgamod_switch);
        switchsound = findViewById(R.id.sound_switch);
        switchvibration = findViewById(R.id.vibration_switch);

        switchaktolga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    booleanaktolga=true;
                }else{
                    booleanaktolga=false;
                }
            }
        });


        switchvibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    booleanvibration=true;
                }else{
                    booleanvibration=false;
                }
            }
        });

        switchsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    booleansound=true;
                }else{
                    booleansound=false;
                }
            }
        });

    }
    public void save(View v){
        finish();
        startActivity(new Intent(this,StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("restDuration",restDurationValue)
        );
    }
}