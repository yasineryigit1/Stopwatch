package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
    ImageView icanchor,bgcircle,hourglass;
    Animation roundingalone,atg,btgone,btgtwo;

    MediaPlayer mp;
    public static Timer timer1;
    //countdown part
    TextView restCountdownText;
    CountDownTimer countDownTimer;
    int restDurationSecond;//settings'den geldi (seconds) cinsinden
    long restDurationInMilliSeconds;//settingsten geldi
    boolean isRestTimerRunning;
    //chronometer
    long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        headsetBroadcastingClass = new HeadsetBroadcastingClass();
        makeStyle();

        //restDuration part
        restCountdownText = findViewById(R.id.restCountdownText);
        Intent intent = getIntent();
        restDurationSecond = intent.getIntExtra("restDuration",SettingsScreen.defaultValue);//kaçtan geri sayacağı (second)
        restDurationInMilliSeconds = restDurationSecond*1000; //kaçtan geri sayacağı (millisecond)
        restCountdownText.setVisibility(View.INVISIBLE);

        //Stop butonu start'a basılmadan önce tıklanılamaz
        btnstop.setAlpha(0);


    }




    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);

        registerReceiver(headsetBroadcastingClass,filter);

    }


    //başka aktiviteye geçerken elapsetTime'ı al kaydet
    @Override
    protected void onPause() {
        super.onPause();

    }
    //uygulamadan çıktıysa 0 la
    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
    /*
    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        // This is the center button for headphones
        if (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK) {
            Toast.makeText(StopWatchAct.this, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            if(!isRestTimerRunning){
                btnstart.performClick();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

*/



    public void settingsButton(View v){
        startActivity(new Intent(StopWatchAct.this,SettingsScreen.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    public void start(View v){
        //start'a tıklandıktan sonra start butonu 300ms sonra -80 aşağı doğru kayarak tıklanamaz hale gelir
        btnstart.animate().alpha(0).translationY(-80).setDuration(300).start();
        //Stop butonu start'a tıkladıktan 300ms sonra clickable hale gelir
        btnstop.animate().alpha(1).setDuration(300).start();
        startRestTimer();
    }

    public void stop(View v){

        //ending animation
        //icanchor.clearAnimation();//antrenman yapıldığı sürece dönsün o yüzden kapatmadım
        //stop'a basınca; start gözükecek, stop görünmez olacak
        btnstart.animate().alpha(1).setDuration(300).start();
        btnstop.animate().alpha(0).setDuration(300).start();
        restCountdownText.setVisibility(View.INVISIBLE);
        stopMusicService();//müzik servisini durdur
        btnstart.setEnabled(true);
        stopRestTimer();

    }

    public static void stopMusicService (){
        //çalınacak soundları iptal et

        timer1.cancel();
        MusicService.mp.stop();
    }



    //rest time saymaya başladı
    public void startRestTimer(){
        setRestText();
        restCountdownText.setVisibility(View.VISIBLE);
        restDurationInMilliSeconds=restDurationSecond*1000;
        //başlar başlamaz milisaniyede başlayacak, firstStart kodlu soundu çalacak
        startTimerForSound(0000,"firstStart");

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
                //5.saniyede 5left soundu çal
                if(seconds==5){
                    startTimerForSound(0000,"5left");

                }
            }

            @Override
            public void onFinish() {
                restCountdownText.setVisibility(View.INVISIBLE);
                //süre bitince finish butonu görünmez yap
                btnstop.animate().alpha(0).setDuration(300).start();
                //süre bitince start butonu görünür yap
                btnstart.animate().alpha(1).setDuration(300).start();
                isRestTimerRunning=false;

            }
        }.start();
        isRestTimerRunning = true;
    }

    public void stopRestTimer(){
        countDownTimer.cancel();
        isRestTimerRunning = false;
        setRestText();
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

    public void setRestText(){
        int minutes =(int)restDurationInMilliSeconds/60000;
        int seconds = (int)restDurationInMilliSeconds%60000/1000;
        String timeLeftText = ""+minutes;
        timeLeftText+=":";
        if(seconds<10) timeLeftText+="0";
        timeLeftText+=seconds;

        restCountdownText.setText(timeLeftText);
    }

    public void makeStyle() {
        //view decleration
        btnstart =findViewById(R.id.btnfinish);
        btnstop=findViewById(R.id.btnstop);
        bgcircle=findViewById(R.id.bgcircle);
        icanchor =findViewById(R.id.icanchor);

        hourglass=findViewById(R.id.hourglass);


        //animation decleration
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);

        //animasyonları başlat
        bgcircle.startAnimation(atg);
        icanchor.startAnimation(atg);

        hourglass.startAnimation(btgone);
        btnstart.startAnimation(btgone);
        btnstop.startAnimation(btgtwo);



        roundingalone=AnimationUtils.loadAnimation(this,R.anim.roundingalone);

        //fontları import ediyoruz
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        btnstart.setTypeface(MMedium);



    }




}



