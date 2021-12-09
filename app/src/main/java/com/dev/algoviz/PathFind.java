package com.dev.algoviz;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class PathFind extends AppCompatActivity {

    // Here we are going to decide which algo to run and what will be the speed of each algo

    private TextInputLayout algorithmMenu;
    private AutoCompleteTextView algorithmDropdown;
    String[] algorithmsList = {"BFS", "Dijkstra's", "GBFS", "A*"};
    CheckBox diagonalCheck;
//    public GridView gridView;

    public CanvasAdapter canvasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        canvasAdapter = new CanvasAdapter(this);

        Grid canvasGrid = canvasAdapter.getGrid();
        Log.d("canvasGridHEgiht", Integer.toString(canvasGrid.getHeight()));
//        gridView = findViewById(R.id.gridView);

        setContentView(R.layout.activity_path_find);
        Button resetButton = findViewById(R.id.resetButton);
        Button startButton = findViewById(R.id.startButton);
        diagonalCheck = findViewById(R.id.diagonalCheck);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
            }
        });

        Boolean isDiagonalChecked = diagonalCheck.isChecked();

        diagonalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                updateDiagonalCheckBox(buttonView.isChecked());
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

    private enum ProgramState {
        Editing, Searching_AnimNotStarted, Searching_AnimStarted, Done
    }

}