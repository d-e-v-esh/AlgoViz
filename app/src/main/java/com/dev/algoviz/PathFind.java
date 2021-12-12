package com.dev.algoviz;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


    String[] algorithmsList = {"Breadth-First Search", "Dijkstra (Uniform Cost Search)", "Greedy Best First Search", "A* Search"};
    CheckBox diagonalCheck;
    public GridView gridView;
    Grid grid;
    private IGraphSearchAlgorithm algorithm;

    int currentAlgorithmIndex = 0;

    // The timer for animating the algorithm progress.
    private CountDownTimer timer;
    Boolean isDiagonalChecked = false;

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
                // Removes the walls
                grid.reset();

                resetAlgorithm();

            }

        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                resetAlgorithm();
                initializeSearchIfNotInitialized();
//                algorithm.run();
                // Starts animating the algorithm

                startTimer();

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


        ArrayAdapter<String> algoArrayAdapter = new ArrayAdapter<>(
                PathFind.this, R.layout.algorithms_dropdown, algorithmsList
        );


        algoArrayAdapter.setDropDownViewResource(R.layout.algorithms_dropdown);
        algorithmDropdown = (AutoCompleteTextView) findViewById(R.id.algorithm_dropdown);
        algorithmDropdown.setAdapter(algoArrayAdapter);


        algoArrayAdapter.notifyDataSetChanged();

        algorithmDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentAlgorithmIndex = position;
            }
        });
    }

    /**
     * Initializes the search functionality based on the maze in its current state. Uses the {@link GraphFactory} class
     * to create the search algorithm which will be run, and starts the {@link GridView} visualizing that algorithm.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeSearchIfNotInitialized() {

        if (algorithm == null) {
            String algName = (String) algorithmsList[currentAlgorithmIndex];
            Graph graph = GraphFactory.fromMaze(grid, isDiagonalChecked);
            Node startNode = graph.findNode(grid.getStartPoint());
            Node goalNode = graph.findNode(grid.getGoalPoint());
            algorithm = GraphSearchAlgorithmFactory.createAlgorithm(algName, graph, startNode, goalNode);
            gridView.setAlgorithm(algorithm);
        }
    }


    /**
     * Resets the algorithm
     */
    private void resetAlgorithm() {
        algorithm = null;
        stopTimer();
        gridView.setAlgorithm(null);
    }

    /**
     * Stops the timer if it's already running, then starts it again.
     */
    private void startTimer() {
        stopTimer();


        timer = new CountDownTimer(1000 * 1000, 1) {
            public void onTick(long millisUntilFinished) {
                boolean done = algorithm.step();
                if (done) {
                    stopTimer();
//                setProgramState(ProgramState.Done);
                }
            }

            public void onFinish() {
//                stopTimer();
            }
        };
        timer.start();
    }


    /**
     * Stops the timer if it's running.
     */
    private void stopTimer() {
        if (timer != null) {

            timer.cancel();
            timer = null;
        }
    }

    /**
     * Each tick of the timer, performs one step of the algorithm. Then, if the algorithm is done, updates the
     * program state.
     */


    private enum ProgramState {
        Editing, Searching_AnimNotStarted, Searching_AnimStarted, Done
    }

    @Override
    protected void onStart() {
        super.onStart();
        grid = new Grid(20, 20);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setGrid(grid);
        algorithmDropdown.setText(algorithmsList[0], false);
    }
}