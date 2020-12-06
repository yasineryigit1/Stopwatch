package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class StopWatchAct extends AppCompatActivity {

    Button btnstart,btnstop;
    ImageView icanchor;
    Animation roundingalone;
    Chronometer timerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        btnstart =findViewById(R.id.btnfinish);
        btnstop=findViewById(R.id.btnstop);
        icanchor =findViewById(R.id.icanchor);
        timerHere =findViewById(R.id.timerHere);

        //Stop butonu start'a basılmadan önce tıklanılamaz
        btnstop.setAlpha(0);


        roundingalone=AnimationUtils.loadAnimation(this,R.anim.roundingalone);

        //fontları import ediyoruz
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        btnstart.setTypeface(MMedium);

    }

    public void start(View v){
        icanchor.startAnimation(roundingalone);

        //Stop butonu start'a tıkladıktan 300ms sonra clickable hale gelir
        btnstop.animate().alpha(1).setDuration(300);

        //start'a tıklandıktan sonra start butonu 300ms sonra -80 aşağı doğru kayarak tıklanamaz hale gelir
        btnstart.animate().alpha(0).translationY(-80).setDuration(300).start();

        timerHere.setBase(SystemClock.elapsedRealtime());
        timerHere.start();
        btnstart.setEnabled(false);
    }

    public void stop(View v){
        //ending animation
        icanchor.clearAnimation();
        btnstart.animate().alpha(1).setDuration(300).start();

        //ending timer
        timerHere.stop();
        btnstart.setEnabled(true);

    }
}
