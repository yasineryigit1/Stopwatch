package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

public class StopWatchAct extends AppCompatActivity {
    HeadsetBroadcastingClass headsetBroadcastingClass;
    Button btnstart,btnstop;
    ImageView icanchor,bgcircle;
    Animation roundingalone,atg,btgone,btgtwo;
    Chronometer timerHere;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        headsetBroadcastingClass = new HeadsetBroadcastingClass();

        //view decleration
        btnstart =findViewById(R.id.btnfinish);
        btnstop=findViewById(R.id.btnstop);
        bgcircle=findViewById(R.id.bgcircle);
        icanchor =findViewById(R.id.icanchor);
        timerHere =findViewById(R.id.timerHere);

        //animation decleration
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);

        //animasyonları başlat
        bgcircle.startAnimation(atg);
        icanchor.startAnimation(atg);
        timerHere.startAnimation(btgone);
        btnstart.startAnimation(btgone);
        btnstop.startAnimation(btgtwo);

        //Stop butonu start'a basılmadan önce tıklanılamaz
        btnstop.setAlpha(0);


        roundingalone=AnimationUtils.loadAnimation(this,R.anim.roundingalone);

        //fontları import ediyoruz
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        btnstart.setTypeface(MMedium);

    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        // This is the center button for headphones
        if (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK) {
            Toast.makeText(StopWatchAct.this, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            baslat();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void baslat(){
        icanchor.startAnimation(roundingalone);

        //Stop butonu start'a tıkladıktan 300ms sonra clickable hale gelir
        btnstop.animate().alpha(1).setDuration(300);

        //start'a tıklandıktan sonra start butonu 300ms sonra -80 aşağı doğru kayarak tıklanamaz hale gelir
        btnstart.animate().alpha(0).translationY(-80).setDuration(300).start();

        timerHere.setBase(SystemClock.elapsedRealtime());
        timerHere.start();

        btnstart.setEnabled(false);
        timerHere.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedSecond = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                //hangi zamanda ne yapacağımız
                if (elapsedSecond==3){
                    System.out.println("gecen: "+ elapsedSecond);
                    Toast.makeText(StopWatchAct.this, "3 seconds", Toast.LENGTH_SHORT).show();
                    startService(new Intent(StopWatchAct.this,MusicService.class));
                }

            }
        });

    }

    public void start(View v){
        baslat();
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
