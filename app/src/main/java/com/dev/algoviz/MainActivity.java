package com.dev.algoviz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFindPath(View v) {


        Toast.makeText(this, "PathFind Opened", Toast.LENGTH_SHORT).show();
        Intent pathFindIntent = new Intent(this, PathFind.class);
        startActivity(pathFindIntent);

    }
}