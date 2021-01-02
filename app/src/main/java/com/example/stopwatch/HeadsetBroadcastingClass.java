package com.example.stopwatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.Toast;

import java.security.Key;

public class HeadsetBroadcastingClass extends BroadcastReceiver {
    public HeadsetBroadcastingClass() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive'e geldi");
        String intentAction = intent.getAction();
        //intent filter action media button ise
        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            KeyEvent event = (KeyEvent)
                    intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            if (event == null) {
                return;
            }

            int keycode = event.getKeyCode();
            int action = event.getAction();
            long eventtime = event.getEventTime();

            if (keycode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keycode == KeyEvent.KEYCODE_HEADSETHOOK) {
                if (action == KeyEvent.ACTION_DOWN) {
                    System.out.println("isleme geldi");
                    Toast.makeText(context, "Clicked receiverdan", Toast.LENGTH_SHORT).show();

                    if (isOrderedBroadcast()) {
                        abortBroadcast();
                    }
                }
            }
        }
    }
}






