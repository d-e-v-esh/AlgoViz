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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.algoviz.algorithms.GraphSearchAlgorithmFactory;
import com.dev.algoviz.algorithms.IGraphSearchAlgorithm;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;


public class PathFind extends AppCompatActivity {

    private TextInputLayout algorithmMenu;
    private AutoCompleteTextView algorithmDropdown;
    private Slider speedSlider;
    private int currentAnimationSpeed = 40;

    private ProgramState programState;
    String[] algorithmsList = {"Breadth-First Search", "Dijkstra (Uniform Cost Search)", "Greedy Best First Search", "A* Search"};
    CheckBox diagonalCheck;
    public GridView gridView;
    Grid grid;
    private IGraphSearchAlgorithm algorithm;

    Boolean isSliderMoving = false;
    int currentAlgorithmIndex = 0;

    // The timer for animating the algorithm progress.
    private CountDownTimer timer;
    Boolean isDiagonalChecked = false;
    Boolean isPlaying = false;

    Button playPauseButton;

    private enum ProgramState {
        Editing, Searching_AnimNotStarted, Searching_AnimStarted, Done
    }


    @Override
    protected void onStart() {
        super.onStart();
        grid = new Grid(20, 20);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setGrid(grid);
        setProgramState(ProgramState.Editing);
        algorithmDropdown.setText(algorithmsList[0], false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_find);

        playPauseButton = findViewById(R.id.playPauseButton);
        Button resetButton = findViewById(R.id.resetButton);
        Button stepButton = findViewById(R.id.stepButton);
        Button completeButton = findViewById(R.id.completeButton);
        Button deWallButton = findViewById(R.id.deWall);
        diagonalCheck = findViewById(R.id.diagonalCheck);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                resetAlgorithm();

                setProgramState(ProgramState.Editing);

            }

        });


        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {


//                When we click on the start button, if the algorithm is not running or we are in the editing phase then we need to run it and set button to stop,
//              but if the algorithm is running on start then stop the algorithm then set the button text to start


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
//                    case Done:
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


                Log.d("programState", programState.toString());

////


            }


        });


        diagonalCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDiagonalChecked = buttonView.isChecked();
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

        speedSlider = (Slider) findViewById(R.id.speedSlider);

        speedSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (value == 0) {
                    currentAnimationSpeed = 1;
                } else {
                    currentAnimationSpeed = (int) value;
                }
                startTimer();

                Log.d("programState", programState.toString());
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                initializeSearchIfNotInitialized();
                stopTimer();
                algorithm.run();
                setProgramState(ProgramState.Done);
            }
        });


        stepButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                initializeSearchIfNotInitialized();
                stopTimer();
                boolean done = algorithm.step();
                setProgramState(done ? ProgramState.Done : ProgramState.Searching_AnimNotStarted);
            }
        });

        deWallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Removes the walls
                // TODO: Rename FINISH to COMPLETE, Divide jobs between clear and complete buttons. There is confusion between those two.


                grid.reset();
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


        timer = new CountDownTimer(1000 * 1000, currentAnimationSpeed) {
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
//                btnClearWalls.setEnabled(true);
//                cboSearchAlgorithm.setEnabled(true);
//                btnStartAnimation.setEnabled(true);
//                btnStopAnimation.setEnabled(false);
//                btnSingleStep.setEnabled(true);
//                btnRunToCompletion.setEnabled(true);
//                btnResetSearch.setEnabled(false);
//                chkAllowDiagonal.setEnabled(true);
                break;

            case Searching_AnimNotStarted:
                grid.setLocked(true);
//                btnClearWalls.setEnabled(false);
//                cboSearchAlgorithm.setEnabled(false);
//                btnStartAnimation.setEnabled(true);
//                btnStopAnimation.setEnabled(false);
//                btnSingleStep.setEnabled(true);
//                btnRunToCompletion.setEnabled(true);
//                btnResetSearch.setEnabled(true);
//                chkAllowDiagonal.setEnabled(false);
                break;

            case Searching_AnimStarted:
                grid.setLocked(true);
//                btnClearWalls.setEnabled(false);
//                cboSearchAlgorithm.setEnabled(false);
//                btnStartAnimation.setEnabled(false);
//                btnStopAnimation.setEnabled(true);
//                btnSingleStep.setEnabled(true);
//                btnRunToCompletion.setEnabled(true);
//                btnResetSearch.setEnabled(true);
//                chkAllowDiagonal.setEnabled(false);
                break;

            case Done:
                grid.setLocked(true);
//                btnClearWalls.setEnabled(false);
//                cboSearchAlgorithm.setEnabled(false);
//                btnStartAnimation.setEnabled(false);
//                btnStopAnimation.setEnabled(false);
//                btnSingleStep.setEnabled(false);
//                btnRunToCompletion.setEnabled(false);
//                btnResetSearch.setEnabled(true);
//                chkAllowDiagonal.setEnabled(false);
                break;
        }
    }


}