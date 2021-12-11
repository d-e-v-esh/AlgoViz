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

import com.dev.algoviz.algorithms.GraphSearchAlgorithmFactory;
import com.dev.algoviz.algorithms.IGraphSearchAlgorithm;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;
import com.google.android.material.textfield.TextInputLayout;

public class PathFind extends AppCompatActivity {

    // Here we are going to decide which algo to run and what will be the speed of each algo

    private TextInputLayout algorithmMenu;
    private AutoCompleteTextView algorithmDropdown;
    String[] algorithmsList = {"BFS", "Dijkstra's", "GBFS", "A*"};
    CheckBox diagonalCheck;
    public GridView gridView;
    Grid grid;
    private IGraphSearchAlgorithm algorithm;

    Boolean isDiagonalChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_find);

        Button resetButton = findViewById(R.id.resetButton);
        Button startButton = findViewById(R.id.startButton);
        diagonalCheck = findViewById(R.id.diagonalCheck);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grid.reset();
                gridView.setAlgorithm(null);
            }

        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                initializeSearchIfNotInitialized();
                algorithm.run();
            }
        });


        diagonalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                updateDiagonalCheckBox(buttonView.isChecked());

                isDiagonalChecked = buttonView.isChecked();
                Log.d("Diagonal Check", Boolean.toString(isDiagonalChecked));
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

    /**
     * Initializes the search functionality based on the maze in its current state. Uses the {@link GraphFactory} class
     * to create the search algorithm which will be run, and starts the {@link GridView} visualizing that algorithm.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeSearchIfNotInitialized() {

        if (algorithm == null) {
            String algName = (String) "Dijkstra (Uniform Cost Search)";
            Graph graph = GraphFactory.fromMaze(grid, true);
            Node startNode = graph.findNode(grid.getStartPoint());
            Node goalNode = graph.findNode(grid.getGoalPoint());
            algorithm = GraphSearchAlgorithmFactory.createAlgorithm(algName, graph, startNode, goalNode);
            gridView.setAlgorithm(algorithm);
        }
    }

    private enum ProgramState {
        Editing, Searching_AnimNotStarted, Searching_AnimStarted, Done
    }

    @Override
    protected void onStart() {
        super.onStart();
        grid = new Grid(20, 20);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setGrid(grid);
    }
}