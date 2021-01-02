package com.example.stopwatch;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mp;
    private static final String TAG = "MusicService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        fivesecondsleft();
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
    }
}
