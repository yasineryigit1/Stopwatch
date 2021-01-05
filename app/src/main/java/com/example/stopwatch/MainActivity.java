package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvSplash, tvSubSplash;
    Button btnget;
    Animation atg,btgone,btgtwo;
    ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeStyles();
    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event) {
        // This is the center button for headphones
        if (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK) {
            //Toast.makeText(MainActivity.this, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
            btnget.performClick();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void makeStyles(){

        tvSplash=findViewById(R.id.tvSplash);
        tvSubSplash=findViewById(R.id.tvSubSplash);
        btnget=findViewById(R.id.btnfinish);

        ivSplash=findViewById(R.id.ivSplash);

        //atg objesine atg.xml'deki animasyon hareketini tanımlıyoruz
        atg= AnimationUtils.loadAnimation(this,R.anim.atg);
        btgone=AnimationUtils.loadAnimation(this,R.anim.btgone);
        btgtwo=AnimationUtils.loadAnimation(this,R.anim.btgtwo);

        //Ekrandaki parçalarımıza atg animasyonunu yaptırıyoruz
        ivSplash.startAnimation(atg);
        tvSplash.startAnimation(btgone);
        tvSubSplash.startAnimation(btgone);
        btnget.startAnimation(btgtwo);

        //fontları import ediyoruz
        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(),"fonts/MRegular.ttf");

        //textView'ların fontlarını değiştiriyoruz
        tvSplash.setTypeface(MRegular);
        tvSubSplash.setTypeface(MLight);
        btnget.setTypeface(MMedium);
    }

    public void getStarted(View v){
        finish();
        startActivity(new Intent(this,StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        );
    }


}
