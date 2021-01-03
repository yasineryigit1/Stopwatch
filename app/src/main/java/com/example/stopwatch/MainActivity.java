package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvSplash, tvSubSplash;
    Button btnget,btnSettings;
    Animation atg,btgone,btgtwo;
    ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent i1 = new Intent(MainActivity.this,StopWatchAct.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i1);
    }

    public void getSettings(View v){
        startActivity(new Intent(MainActivity.this,SettingsScreen.class));
    }
}
