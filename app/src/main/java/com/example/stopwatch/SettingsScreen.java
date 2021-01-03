package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsScreen extends AppCompatActivity {

    NumberPicker restNumberPicker;
    TextView restDurationText;
    int restDurationValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        restNumberPicker = findViewById(R.id.restNumberPicker);
        restDurationText=findViewById(R.id.restDurationText);
        restNumberPicker.setMinValue(1);
        restNumberPicker.setMaxValue(999);
        restNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                restDurationValue = newVal;
                restDurationText.setText("Rest Duration: " + restDurationValue );
            }
        });


    }
    public void save(View v){
        startActivity(new Intent(this,StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .putExtra("restDuration",restDurationValue)
        );
    }
}