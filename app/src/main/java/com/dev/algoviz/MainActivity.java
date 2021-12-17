package com.dev.algoviz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        supportAc
    }

    public void openFindPath(View v) {

        Intent pathFindIntent = new Intent(this, PathFind.class);
        startActivity(pathFindIntent);




    }
}