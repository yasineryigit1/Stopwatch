package com.ossovita.stopwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadsetBroadcastingClass extends BroadcastReceiver {
    public HeadsetBroadcastingClass() {
        super();
        System.out.println("initialized");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "action detected", Toast.LENGTH_SHORT).show();
    }

}

