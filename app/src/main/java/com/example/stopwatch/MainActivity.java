package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvSplash, tvSubSplash;
    Button btnget,btnSettings;
    Animation atg,btgone,btgtwo;
    ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeStyles();
    }

    public void makeStyles(){

        tvSplash=findViewById(R.id.tvSplash);
        tvSubSplash=findViewById(R.id.tvSubSplash);
        btnget=findViewById(R.id.btnfinish);
        btnSettings = findViewById(R.id.btnSettings);
        ivSplash=findViewById(R.id.ivSplash);

        //atg objesine atg.xml'deki animasyon hareketini tanımlıyoruz
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);

        //Ekrandaki parçalarımıza atg animasyonunu yaptırıyoruz
        ivSplash.startAnimation(atg);
        tvSplash.startAnimation(btgone);
        tvSubSplash.startAnimation(btgone);
        btnget.startAnimation(btgtwo);
        btnSettings.startAnimation(btgtwo);

        //fontları import ediyoruz
        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(),"fonts/MRegular.ttf");

        //textView'ların fontlarını değiştiriyoruz
        tvSplash.setTypeface(MRegular);
        tvSubSplash.setTypeface(MLight);
        btnget.setTypeface(MMedium);
    }

    public void getStarted(View v){
        finish();
        startActivity(new Intent(this,StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK)

        );
    }

    public void getSettings(View  v){
        finish();
        startActivity(new Intent(this,SettingsScreen.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK)

        );
    }
}
