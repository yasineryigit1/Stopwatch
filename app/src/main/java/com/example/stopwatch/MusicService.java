package com.example.stopwatch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    public static Vibrator vibrator;
    public static MediaPlayer mp,mp1;
    private static final String TAG = "MusicService";
    Boolean boolsound,boolvibration,boolaktolga;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: geldi");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: geldi ");

        String soundType = intent.getStringExtra("soundType");
         boolsound= intent.getBooleanExtra("boolsound",true);
         boolvibration = intent.getBooleanExtra("boolvibration",true);
         boolaktolga = intent.getBooleanExtra("boolaktolga",true);
         vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
         if(soundType.equals("5left")){
            fivesecondsleftSound(boolsound);

        }
        if(soundType.equals("firstStart")){
            firstStartSound(boolaktolga);

        }

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mp.stop();
        super.onDestroy();
    }

    public void fivesecondsleftSound(Boolean boolsound){
        if(boolsound){//ses açıksa çaş
            //Log.d(TAG, "fivesecondsleftSound: çalmadan önce boolsound geldi"+boolsound);
            mp1 = MediaPlayer.create(MusicService.this,R.raw.say5);
            mp1.start();
        }



    }


    //ilk başlanguç sesini çal
    public void firstStartSound(Boolean boolaktolga) {
        if (boolaktolga) {
            Log.d(TAG, "firstStartSound: çalmadan önce boolsound geldi"+boolsound);
            mp = MediaPlayer.create(MusicService.this,R.raw.aktolga1);
            mp.start();
        }

    }

    public static void vibrate(Boolean boolvibration) {
        if(boolvibration){
            vibrator.vibrate(1000);
        }
    }

}
