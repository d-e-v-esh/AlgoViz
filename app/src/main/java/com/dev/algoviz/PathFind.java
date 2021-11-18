package com.dev.algoviz;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class PathFind extends AppCompatActivity {

    // Here we are going to decide which algo to run and what will be the speed of each algo

    private TextInputLayout algorithmMenu;
    private AutoCompleteTextView algorithmDropdown;
    String[] algorithmsList = {"A*", "Dijkstra's", "BFS", "DFS"};

    private Button resetButton;
    private Button startButton;

    private GridView mGridView;
    private Dijkstra dijkstra ;
    String selectedAlgorithm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_find);

        mGridView = findViewById(R.id.gridView);
        resetButton = findViewById(R.id.resetButton);
        startButton = findViewById(R.id.startButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridView.resetGrid();

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mGridView.visualize();

            }
        });

        // Here we can say, if dijkstra is selected and start is pressed,

        algorithmMenu = findViewById(R.id.algorithmMenu);

        algorithmDropdown = findViewById(R.id.algorithm_dropdown);

        ArrayAdapter<String> algoArrayAdapter = new ArrayAdapter<>(
                PathFind.this, R.layout.algorithms_dropdown, algorithmsList
        );


        algorithmDropdown.setAdapter(algoArrayAdapter);
    }
}