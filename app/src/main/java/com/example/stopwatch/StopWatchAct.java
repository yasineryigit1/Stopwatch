package com.example.stopwatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.MediaStore;
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
import java.util.concurrent.BlockingDeque;

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
    public static int restDurationSecond;//settings'den geldi (seconds) cinsinden
    long restDurationInMilliSeconds;//settingsten geldi
    boolean isRestTimerRunning;
    //chronometer
    Chronometer chronometer;
    private static final String TAG = "StopWatchAct";
    //sharedpreferences
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        headsetBroadcastingClass = new HeadsetBroadcastingClass();
        Log.d(TAG, "onCreate: worked");
        //chronometer
        chronometer=findViewById(R.id.totalchronometer);
        //restDuration part
        restCountdownText = findViewById(R.id.restCountdownText);
        makeStyle();
        icanchor.startAnimation(roundingalone);//uygulama başlatıldığında yelkovanı döndür
        Intent intent = getIntent();

        sp=getSharedPreferences("rest",Context.MODE_PRIVATE);
        restDurationSecond = sp.getInt("restDuration",SettingsScreen.defaultValue);
        restDurationInMilliSeconds = restDurationSecond*1000; //kaçtan geri sayacağı (millisecond)
        restCountdownText.setVisibility(View.INVISIBLE);

        //Stop butonu start'a basılmadan önce tıklanılamaz
        btnstop.setAlpha(0);


        if(MediaController.booleansound==null){
            MediaController.booleansound=true;
        }
        if(MediaController.booleanvibration==null){
            MediaController.booleanvibration=true;
        }
        if(MediaController.booleanaktolga==null){
            MediaController.booleanaktolga=true;
        }

        Log.d(TAG, "onCreate: booleansound geldi: "+MediaController.booleansound);
        Log.d(TAG, "onCreate: booleanvibration geldi: "+MediaController.booleanvibration);




    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: worked");
        chronometer.start();
        restDurationSecond = sp.getInt("restDuration",SettingsScreen.defaultValue);

    }


    //başka aktiviteye geçerken elapsetTime'ı al kaydet
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: a geldi");
        chronometer.stop();
        super.onPause();

    }
    //uygulamadan çıktıysa 0 la
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d(TAG, "onBackPressed: worked");
        //ChronometerHelper.destroyChronometer(chronometer,this);
        finishAlert();

    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        // This is the center button for headphones
        if (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK) {
            //Toast.makeText(StopWatchAct.this, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            if(!isRestTimerRunning){
                btnstart.performClick();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    public void settingsButton(View v){
        //ChronometerHelper.stopChronometer(chronometer,this);
        btnstop.performClick();//settings'e giderse mevcut sayacı ve zamanlanmış müzikleri kapat
        startActivity(new Intent(StopWatchAct.this,SettingsScreen.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    //Finish button with alert dialog
    public void finishTraining(View v){
        finishAlert();
    }

    public void finishAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Finish");
        alert.setMessage("Are you sure to finish training?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                stopMusicService();//müzik durdurulması için eklendi
                stopRestTimer();    //zamanlanmış müziğin kalmaması için eklendi
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    public void start(View v){
        //start'a tıklandıktan sonra start butonu 300ms sonra -80 aşağı doğru kayarak tıklanamaz hale gelir
        btnstart.animate().alpha(0).translationY(-80).setDuration(300).start();
        btnstart.setClickable(false);
        //Stop butonu start'a tıkladıktan 300ms sonra clickable hale gelir
        btnstop.animate().alpha(1).setDuration(300).start();
        setRestText();
        restCountdownText.setVisibility(View.VISIBLE);
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
       // btnstart.setEnabled(true);
        btnstart.setClickable(true);
        stopRestTimer();

    }

    public static void stopMusicService (){
        //çalınacak soundları iptal et

        if(MusicService.mp!=null){
            if(MusicService.mp.isPlaying()){
                MusicService.mp.stop();
            }
        }

        if(MusicService.mp1!=null){
            if(MusicService.mp1.isPlaying()){
                MusicService.mp1.stop();
            }
        }


    }



    //rest time saymaya başladı
    public void startRestTimer(){
        restDurationInMilliSeconds=restDurationSecond*1000;
        //başlar başlamaz milisaniyede başlayacak, firstStart kodlu soundu çalacak
        if(MediaController.booleanaktolga){//mod switchi açıksa
            //startTimerForSound(0000,"firstStart",MediaController.booleansound,MediaController.booleanvibration);
            startService(new Intent(StopWatchAct.this,MusicService.class)
                    .putExtra("soundType","firstStart")
                    .putExtra("boolvibration",MediaController.booleanvibration)
                    .putExtra("boolsound",MediaController.booleansound)
                    .putExtra("boolaktolga",MediaController.booleanaktolga)
            );
        }

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
                if(minutes==0&&seconds==5){//son 5sn kala 5left soundunu çal
                    //startTimerForSound(0000,"5left",MediaController.booleansound,MediaController.booleanvibration);

                    startService(new Intent(StopWatchAct.this,MusicService.class)
                            .putExtra("soundType","5left")
                            .putExtra("boolvibration",MediaController.booleanvibration)
                            .putExtra("boolsound",MediaController.booleansound)
                    );
                }
                if(minutes==0&&seconds==0){//süre bittiğidne
                    MusicService.vibrate(MediaController.booleanvibration);
                }

            }

            @Override
            public void onFinish() {
                restCountdownText.setVisibility(View.INVISIBLE);
                //süre bitince finish butonu görünmez yap
                btnstop.animate().alpha(0).setDuration(300).start();
                //süre bitince start butonu görünür yap
                btnstart.animate().alpha(1).setDuration(300).start();
                btnstart.setClickable(true);
                isRestTimerRunning=false;

            }
        }.start();
        isRestTimerRunning = true;
    }

    public void stopRestTimer(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        isRestTimerRunning = false;
        setRestText();
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
        chronometer.startAnimation(btgone);
        btnstart.startAnimation(btgone);
        btnstop.startAnimation(btgtwo);



        roundingalone=AnimationUtils.loadAnimation(this,R.anim.roundingalone);

        //fontları import ediyoruz
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        btnstart.setTypeface(MMedium);



    }




}



