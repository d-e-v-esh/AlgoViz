package com.dev.algoviz;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class IntroSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_splash);

        getSupportActionBar().hide();
        // Prevents dark mode being applied to this app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Thread thread = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(2000);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(IntroSplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        thread.start();


    }
}