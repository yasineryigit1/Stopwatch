package com.example.stopwatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

public class ChronometerHelper {
    static SharedPreferences sp;
    static long startTime = SystemClock.elapsedRealtime();

    public static void startChronometer(Chronometer chronometer,Context context){

      sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
      long storedStartTime=sp.getLong("storedStartTime",0);
      //saklanmış veri varsa ordan başlat
      if(storedStartTime!=0){
          chronometer.setBase(storedStartTime);
      }else{//saklanmış veri yoksa  SystemClock.elapsedRealtime'den başla
          chronometer.setBase(startTime);
      }
      chronometer.start();
    }

    public static void stopChronometer(Chronometer chronometer, Context context){
        //durdurduğunda güncel startTime'ı al sakla
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sp.edit().putLong("storedStartTime",startTime).apply();
        chronometer.stop();
    }

    public static void destroyChronometer(Chronometer chronometer, Context context){
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sp.edit().remove("storedStartTime").apply();

        chronometer.stop();


    }
}
