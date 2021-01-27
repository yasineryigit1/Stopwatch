package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    TextView tvSplash, tvSubSplash;
    Button btnget;
    Animation atg,btgone,btgtwo;
    ImageView ivSplash;
    //Ad
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeStyles();

       reklamYukle();


    }

    private void reklamYukle() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //Fulscreen ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5103372605554042/4862436528");
        //Geçişte yüklemekle uğraşmayalım diye burada load ediyoruz
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //reklam kapatıldıysa tekrar yükle
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    public void getStarted(View v){
        finish();
        startActivity(new Intent(this,StopWatchAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        );
        //Ad yüklendiyse göster
        if (mInterstitialAd.isLoaded()) {
            System.out.println("Ad yuklendi");
            mInterstitialAd.show();

        }else{
            System.out.println("Ad yuklenmedi");
        }

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




}
