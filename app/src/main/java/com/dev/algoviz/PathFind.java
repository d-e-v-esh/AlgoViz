package com.dev.algoviz;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class PathFind extends AppCompatActivity {

    // Here we are going to decide which algo to run and what will be the speed of each algo

    private TextInputLayout algorithmMenu;
    private AutoCompleteTextView algorithmDropdown;
    String[] algorithmsList = {"A*", "Dijkstra's", "BFS", "DFS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);


        algorithmMenu = findViewById(R.id.algorithmMenu);

        algorithmDropdown = findViewById(R.id.algorithm_dropdown);

        ArrayAdapter<String> algoArrayAdapter = new ArrayAdapter<>(
                PathFind.this, R.layout.algorithms_dropdown, algorithmsList
        );


        algorithmDropdown.setAdapter(algoArrayAdapter);
    }
}