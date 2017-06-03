package me.abheyrana.quicknews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    private static long SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sourceSetting = getSharedPreferences("Source Setting", Context.MODE_PRIVATE);
                boolean selected = sourceSetting.getBoolean("selected",false);
                if(!selected){
                    startActivity(new Intent(SplashScreen.this,NewsSourceSelection.class));
                }
                else{
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                }
                finish();
            }
        },SPLASH_TIME_OUT);

    }

}
