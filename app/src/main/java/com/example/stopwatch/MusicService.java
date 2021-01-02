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
    Vibrator vibrator;
    public static MediaPlayer mp;
    private static final String TAG = "MusicService";
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
        if(soundType.equals("5left")){
            fivesecondsleft();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mp.stop();
        super.onDestroy();
    }

    public void fivesecondsleft(){
        mp = MediaPlayer.create(MusicService.this,R.raw.say5);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                    System.out.println("kosula geldi");
                    vibrator = (Vibrator) getSystemService(MusicService.this.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);

            }
        });
    }

}
