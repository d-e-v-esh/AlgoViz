package com.dev.algoviz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    IntroFragmentAdapter introFragmentAdapter;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        
        getSupportActionBar().hide();

        viewPager2 = findViewById(R.id.viewPager2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        introFragmentAdapter = new IntroFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(introFragmentAdapter);


    }


    public void openFindPath(View v) {


        Toast.makeText(this, "PathFind Opened", Toast.LENGTH_SHORT).show();
        Intent pathFindIntent = new Intent(this, PathFind.class);
        startActivity(pathFindIntent);

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

}