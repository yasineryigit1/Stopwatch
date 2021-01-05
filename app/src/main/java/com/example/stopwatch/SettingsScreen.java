package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.stopwatch.StopWatchAct.stopMusicService;

public class SettingsScreen extends AppCompatActivity {
    //Number picker
    NumberPicker restNumberPicker;
    boolean isNumberPickerChanged;
    TextView restDurationText;
    ImageView settingsSplash;
    TextView switchsoundtext,switchvibrationtext,switchaktolgamodtext;
    Button btnsave;
    int restDurationValue;
    public static int defaultValue = 10;
    //switches
    public Switch switchaktolga,switchvibration,switchsound;
    SharedPreferences sp;
    //Animation
    Animation atg,btgone,btgtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        stopMusicService();//Settings ekranına geçtiği anda tüm soundları durdur
        makeStyles();
        isNumberPickerChanged=false;
        restNumberPicker.setMinValue(10);
        restNumberPicker.setMaxValue(500);
        //restDurationSecond değiştirildiyse yani default değilse, yeni değerini ata o zaman
        if(StopWatchAct.restDurationSecond!=defaultValue){
            defaultValue=StopWatchAct.restDurationSecond;
        }
        restNumberPicker.setValue(defaultValue);
        //restDurationText.setText("Rest Duration: " + defaultValue);
        restNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                isNumberPickerChanged=true;//değer değişti
                restDurationValue = newVal;
                //restDurationText.setText("Rest Duration: " + restDurationValue );
            }
        });

        //--------------Değiştirilmemişse true, değiştirilmişse değerini ver----
        if(MediaController.booleansound!=null){
            switchsound.setChecked(MediaController.booleansound);
        }else{
            switchsound.setChecked(true);
        }

        if(MediaController.booleanvibration!=null){
            switchvibration.setChecked(MediaController.booleanvibration);
        }else{
            switchvibration.setChecked(true);
        }
        if(MediaController.booleanaktolga!=null){
            switchaktolga.setChecked(MediaController.booleanaktolga);
        }else{
            switchaktolga.setChecked(true);
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
                    switchsoundtext.setText("Sound Notification On");
                }else{
                    MediaController.booleansound=false;
                    switchsoundtext.setText("Sound Notification Off");
                }
            }
        });

    }
    public void save(View v) {
        sp = getSharedPreferences("rest", Context.MODE_PRIVATE);
        if(isNumberPickerChanged){//değer değiştirildiyse yeni sayıyı kaydet
            sp.edit().putInt("restDuration",restDurationValue).apply();
        }
        //save'e basınca ayarları kaydet
        sp.edit().putBoolean("boolsound",MediaController.booleansound).apply();
        sp.edit().putBoolean("boolvibration",MediaController.booleanvibration).apply();
        sp.edit().putBoolean("boolaktolga",MediaController.booleanaktolga).apply();
        finish();
    }


    public void makeStyles(){
        //ImageView
        settingsSplash = findViewById(R.id.settingsSplash);
        //number picker | text
        restNumberPicker = findViewById(R.id.restNumberPicker);
        restDurationText=findViewById(R.id.restDurationText);
        //switch tanımlamaları
        switchaktolga = findViewById(R.id.aktolgamod_switch);
        switchsound = findViewById(R.id.sound_switch);
        switchvibration = findViewById(R.id.vibration_switch);
        //switch text tanımlamaları
        switchsoundtext = findViewById(R.id.switch_sound_text);
        switchvibrationtext=findViewById(R.id.switch_vibration_text);
        switchaktolgamodtext = findViewById(R.id.switch_aktolgamod_text);
        //Button
        btnsave = findViewById(R.id.btnsave);

        //animation
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);

        settingsSplash.startAnimation(atg);

        restNumberPicker.startAnimation(btgone);
        restDurationText.startAnimation(btgone);
        switchsound.startAnimation(btgone);
        switchvibration.startAnimation(btgone);
        switchaktolga.startAnimation(btgone);
        switchsoundtext.startAnimation(btgone);
        switchvibrationtext.startAnimation(btgone);
        switchaktolgamodtext.startAnimation(btgone);

        btnsave.startAnimation(btgtwo);





    }

}