package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatchAct extends AppCompatActivity {
    HeadsetBroadcastingClass headsetBroadcastingClass;
    Button btnstart,btnstop;
    ImageView icanchor,bgcircle;
    Animation roundingalone,atg,btgone,btgtwo;
    Chronometer timerHere;
    MediaPlayer mp;
    Timer timer1;
    Boolean baslatTotalBoolean = true;
    //countdown part
    TextView restCountdownText;
    CountDownTimer countDownTimer;
    int restDurationSecond;//settings'den geldi (seconds) cinsinden
    long restDurationInMilliSeconds;//settingsten geldi
    boolean isRestTimerRunning;

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

        //restDuration part
        restCountdownText = findViewById(R.id.restCountdownText);
        Intent intent = getIntent();
        restDurationSecond = intent.getIntExtra("restDuration",60);//kaçtan geri sayacağı (second)
        restDurationInMilliSeconds = restDurationSecond*1000; //kaçtan geri sayacağı (millisecond)
        setRestText();

        //timerHere otomatik başlat
        baslatTotal();


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
            if(baslatTotalBoolean==true){
                baslatTotal();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    public void baslatTotal(){
        baslatTotalBoolean=false;
        icanchor.startAnimation(roundingalone);

        //Stop butonu start'a tıkladıktan 300ms sonra clickable hale gelir
        btnstop.animate().alpha(1).setDuration(300);

        //start'a tıklandıktan sonra start butonu 300ms sonra -80 aşağı doğru kayarak tıklanamaz hale gelir
        btnstart.animate().alpha(0).translationY(-80).setDuration(300).start();

        timerHere.setBase(SystemClock.elapsedRealtime());
        timerHere.start();
        //0001. milisaniyede başlayacak, firstStart kodlu soundu çalacak
        startTimerForSound(0000,"firstStart");
        //3000. milisaniyede başlayacak, 5left kodlu soundu çalacak
        startTimerForSound(5000,"5left");
        btnstart.setEnabled(false);


    }

    //ne kadar süre sonra hangi sesi çalacağımızı task olarak kaydeder
    // ve zamanı geldiğinde servisi başlatır
    public void startTimerForSound(long left,String soundType){
        timer1 = new Timer();
        final String msoundType = soundType;
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), "3 seconds", Toast.LENGTH_SHORT).show();
                startService(new Intent(StopWatchAct.this,MusicService.class).putExtra("soundType",msoundType));
            }
        };
        timer1.schedule(task, left);

    }

    public void start(View v){

        startRestTimer();
    }

    public void stop(View v){
        baslatTotalBoolean=true;
        //ending animation
        icanchor.clearAnimation();
        btnstart.animate().alpha(1).setDuration(300).start();

        //ending timer
        timerHere.stop();
        timer1.cancel();
        MusicService.mp.stop();
        btnstart.setEnabled(true);
        stopRestTimer();

    }



    //RestTimer
    //karar mekanizması, çalışıyorsa baştan alır,
    public void restCountdown(){
        if(isRestTimerRunning){

        }else{

        }
    }
    //rest time saymaya başladı
    public void startRestTimer(){
        restDurationInMilliSeconds=restDurationSecond*1000;
        //her başlatmada sıfırdan başla
        countDownTimer = new CountDownTimer(restDurationInMilliSeconds,1000) {
            @Override
            public void onTick(long l) {
                restDurationInMilliSeconds = l;
                int minutes =(int)restDurationInMilliSeconds/60000;
                int seconds = (int) restDurationInMilliSeconds%60000/1000;
                String timeLeftText = ""+minutes;
                timeLeftText+=":";
                if(seconds<10) timeLeftText+="0";
                timeLeftText+=seconds;

                restCountdownText.setText(timeLeftText);

            }

            @Override
            public void onFinish() {
                restCountdownText.setText("DONE!");
            }
        }.start();
        isRestTimerRunning = true;
    }

    public void stopRestTimer(){
        countDownTimer.cancel();
        isRestTimerRunning = false;
    }

    public void setRestText(){
        int minutes =(int)restDurationInMilliSeconds/60000;
        int seconds = (int)restDurationInMilliSeconds%60000/1000;
        String timeLeftText = ""+minutes;
        timeLeftText+=":";
        if(seconds<10) timeLeftText+="0";
        timeLeftText+=seconds;

        restCountdownText.setText(timeLeftText);
    }





}



