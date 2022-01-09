package com.dev.algoviz;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.algoviz.algorithms.GraphSearchAlgorithmFactory;
import com.dev.algoviz.algorithms.IGraphSearchAlgorithm;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;
import com.google.android.material.slider.Slider;


public class PathFind extends AppCompatActivity {

    private AutoCompleteTextView algorithmDropdown;

    private AutoCompleteTextView blockTypeDropdown;
    private int currentAnimationSpeed = 40;

    private ProgramState programState;
    String[] algorithmsList = {"A* Search", "Dijkstra's Search", "Greedy Best First Search", "Breadth-First Search"};
    String[] blockTypeList = {"Wall", "Blank", "Start", "End"};
    CheckBox diagonalCheck;
    public GridView gridView;
    Grid grid;
    private IGraphSearchAlgorithm algorithm;

    int currentAlgorithmIndex = 0;

    // The timer for animating the algorithm progress.
    private CountDownTimer timer;
    Boolean isDiagonalChecked = false;

    Button playPauseButton;
    Button deWallButton;
    Button resetButton;
    Button stepButton;
    Button completeButton;

    private enum ProgramState {
        Editing, Searching_AnimNotStarted, Searching_AnimStarted, Done
    }


    @Override
    protected void onStart() {
        super.onStart();
        grid = new Grid(21, 30);
        gridView = findViewById(R.id.gridView);
        gridView.setGrid(grid);

        int canvasWidth = this.gridView.getMeasuredWidth();
        int canvasHeight = this.gridView.getMeasuredHeight();
        Log.d("Canvas Width", Integer.toString(canvasWidth));
        Log.d("Canvas Height", Integer.toString(canvasHeight));


        setProgramState(ProgramState.Editing);
        algorithmDropdown.setText(algorithmsList[0], false);
        blockTypeDropdown.setText(blockTypeList[0], false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_path_find);

        playPauseButton = findViewById(R.id.playPauseButton);
        resetButton = findViewById(R.id.resetButton);
        stepButton = findViewById(R.id.stepButton);
        completeButton = findViewById(R.id.completeButton);
        deWallButton = findViewById(R.id.deWall);
        diagonalCheck = findViewById(R.id.diagonalCheck);


        resetButton.setOnClickListener(v -> {
            resetAlgorithm();
            setProgramState(ProgramState.Editing);
            playPauseButton.setText(R.string.play);
        });

        playPauseButton.setOnClickListener(v -> {
            switch (programState) {
                case Editing:
                case Done:
                    resetAlgorithm();
                    initializeSearchIfNotInitialized();
                    startTimer();
                    playPauseButton.setText(R.string.pause);
                    setProgramState(ProgramState.Searching_AnimStarted);
                    break;

                case Searching_AnimStarted:
                    playPauseButton.setText(R.string.play);
                    stopTimer();
                    setProgramState(ProgramState.Searching_AnimNotStarted);
                    break;

                case Searching_AnimNotStarted:
                    startTimer();
                    playPauseButton.setText(R.string.pause);
                    setProgramState(ProgramState.Searching_AnimStarted);
                    break;
            }
        });


        diagonalCheck.setOnCheckedChangeListener((buttonView, isChecked) -> isDiagonalChecked = buttonView.isChecked());


        ArrayAdapter<String> algoArrayAdapter = new ArrayAdapter<>(
                PathFind.this, R.layout.algorithms_dropdown, algorithmsList
        );


        algorithmDropdown = findViewById(R.id.algorithm_dropdown);
        algorithmDropdown.setAdapter(algoArrayAdapter);

        algorithmDropdown.setOnItemClickListener((parent, view, position, id) -> currentAlgorithmIndex = position);

        ArrayAdapter<String> blockTypeArrayAdapter = new ArrayAdapter<>(
                PathFind.this, R.layout.block_type_dropdown, blockTypeList
        );

        blockTypeDropdown = findViewById(R.id.blockTypeDropdown);
        blockTypeDropdown.setAdapter(blockTypeArrayAdapter);

        blockTypeDropdown.setOnItemClickListener((parent, view, position, id) ->
                gridView.setBlockType(position)
        );


        Slider speedSlider = findViewById(R.id.speedSlider);

        speedSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (value == 0) {
                currentAnimationSpeed = 300;
            }
            if (value == 300) {
                currentAnimationSpeed = 1;
            } else {
                currentAnimationSpeed = 300 - (int) value;
            }
            startTimer();

            Log.d("programState", programState.toString());
        });

        completeButton.setOnClickListener(v -> {
            initializeSearchIfNotInitialized();
            stopTimer();
            algorithm.run();
            setProgramState(ProgramState.Done);
        });


        stepButton.setOnClickListener(v -> {

            initializeSearchIfNotInitialized();
            stopTimer();
            boolean done = algorithm.step();
            setProgramState(done ? ProgramState.Done : ProgramState.Searching_AnimNotStarted);
        });

        deWallButton.setOnClickListener(v -> {
            grid.reset();
        });

    }


    /**
     * Initializes the search functionality based on the maze in its current state. Uses the {@link GraphFactory} class
     * to create the search algorithm which will be run, and starts the {@link GridView} visualizing that algorithm.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeSearchIfNotInitialized() {

        if (algorithm == null) {
            String algName = algorithmsList[currentAlgorithmIndex];
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


        timer = new CountDownTimer(1000 * 1000, 2000) {
            public void onTick(long millisUntilFinished) {

                if (algorithm != null) {

                    boolean done = algorithm.step();
                    if (done) {
                        stopTimer();
                        setProgramState(ProgramState.Done);
                        playPauseButton.setText(R.string.play);

                        Log.d("programState", programState.toString());
                    }
                }
            }

            @Override
            public void onFinish() {
//
            }

        };
        timer.start();


        Log.d("programState", programState.toString());
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
     * Updates the program state. Enables / disables UI elements to match the state.
     */
    private void setProgramState(ProgramState programState) {
        this.programState = programState;
        switch (programState) {
            case Editing:
                grid.setLocked(false);

                resetButton.setEnabled(false);

                diagonalCheck.setEnabled(true);

                stepButton.setEnabled(true);
                completeButton.setEnabled(true);
                algorithmDropdown.setEnabled(true);
                blockTypeDropdown.setEnabled(true);
                deWallButton.setEnabled(true);
                break;

            case Searching_AnimNotStarted:
                grid.setLocked(true);
                blockTypeDropdown.setEnabled(false);


                algorithmDropdown.setEnabled(false);
                diagonalCheck.setEnabled(false);
                resetButton.setEnabled(true);

                stepButton.setEnabled(true);
                completeButton.setEnabled(true);
                deWallButton.setEnabled(false);
                break;

            case Searching_AnimStarted:
                grid.setLocked(true);


                blockTypeDropdown.setEnabled(false);
                stepButton.setEnabled(true);
                completeButton.setEnabled(true);

                algorithmDropdown.setEnabled(false);
                deWallButton.setEnabled(false);
                diagonalCheck.setEnabled(false);
                resetButton.setEnabled(true);
                break;

            case Done:
                grid.setLocked(true);


                stepButton.setEnabled(false);
                completeButton.setEnabled(false);

                blockTypeDropdown.setEnabled(false);

                algorithmDropdown.setEnabled(true);
                deWallButton.setEnabled(false);
                diagonalCheck.setEnabled(true);
                resetButton.setEnabled(true);
                break;
        }
    }


}