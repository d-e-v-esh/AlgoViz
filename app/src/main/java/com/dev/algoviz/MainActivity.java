package com.dev.algoviz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    IntroFragmentAdapter introFragmentAdapter;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager2 = findViewById(R.id.viewPager2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        introFragmentAdapter = new IntroFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(introFragmentAdapter);


    }


}