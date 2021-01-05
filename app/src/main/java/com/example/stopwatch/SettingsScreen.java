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

import static com.example.stopwatch.StopWatchAct.stopMusicService;

public class SettingsScreen extends AppCompatActivity {

    NumberPicker restNumberPicker;
    TextView restDurationText;
    TextView switchsoundtext,switchvibrationtext,switchaktolgamodtext;
    int restDurationValue;
    public static int defaultValue = 10;
    //switches
    public Switch switchaktolga,switchvibration,switchsound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        stopMusicService();//Settings ekranına geçtiği anda tüm soundları durdur
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
        //switch tanımlamaları
        switchaktolga = findViewById(R.id.aktolgamod_switch);
        switchsound = findViewById(R.id.sound_switch);
        switchvibration = findViewById(R.id.vibration_switch);
        //switch text tanımlamaları
        switchsoundtext = findViewById(R.id.switch_sound_text);
        switchvibrationtext=findViewById(R.id.switch_vibration_text);
        switchaktolgamodtext = findViewById(R.id.switch_aktolgamod_text);
        //--------------Değiştirilmemişse true, değiştirilmişse değerini ver----
        if(MediaController.booleansound!=null){
            switchsound.setChecked(MediaController.booleansound);
        }else{
            switchsound.setChecked(MediaController.booleansound);
        }

        if(MediaController.booleanvibration!=null){
            switchvibration.setChecked(MediaController.booleanvibration);
        }else{
            switchvibration.setChecked(MediaController.booleanvibration);
        }
        if(MediaController.booleanaktolga!=null){
            switchaktolga.setChecked(MediaController.booleanaktolga);
        }else{
            switchaktolga.setChecked(MediaController.booleanaktolga);
        }

        switchaktolga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MediaController.booleanaktolga=true;
                    switchaktolgamodtext.setText("Serdar Aktolga Mod On");

                }else{
                    MediaController.booleanaktolga=false;
                    switchaktolgamodtext.setText("Serdar Aktolga Mod Off");
                }
            }
        });


        switchvibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MediaController.booleanvibration=true;
                    switchvibrationtext.setText("Vibration On");
                }else{
                    MediaController.booleanvibration=false;
                    switchvibrationtext.setText("Vibration Off");
                }
            }
        });

        switchsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MediaController.booleansound=true;
                    switchsoundtext.setText("Sound On");
                }else{
                    MediaController.booleansound=false;
                    switchsoundtext.setText("Sound Off");
                }
            }
        });

    }
    public void save(View v) {
        finish();
        startActivity(new Intent(this, StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("restDuration", restDurationValue)
        );

    }
}