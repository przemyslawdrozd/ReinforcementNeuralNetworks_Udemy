package com.company.markov_process_value_iteration;

import static com.company.markov_process_value_iteration.Constants.*;

public class MarkovProcessValueIteration {

    // This is the V(s) value function
    private double v[][];
    // V`(s) used in updates
    private double vNext[][];
    // We have to store the rewards
    private double r[][];
    // We have to store policy itself
    private char pi[][];
    // Track the error delta = |V(t+1) - V(t)|
    private double delta = 0;
    private int n;

    public MarkovProcessValueIteration() {
        // Policy: in the end we want to have the characters - where to go Up, Down, Left, Right
        pi = new char[NUM_ROWS][NUM_COLUMNS];
        // The V value function values
        v = new double[NUM_ROWS][NUM_COLUMNS];
        // Initialize V`
        vNext = new double[NUM_ROWS][NUM_COLUMNS];
        // Reward function R(s`,s)
        r = new double[NUM_ROWS][NUM_COLUMNS];

        initVariables();
    }

    private void initVariables() {
        // We just have to init the R(s`,s) rewards
        for (int rowIndex = 0; rowIndex < NUM_ROWS; rowIndex++) {
            for (int columnIndex = 0; columnIndex < NUM_COLUMNS; columnIndex++) {
                r[rowIndex][columnIndex] = STATE_REWARD;
            }
        }

        // initialize the +1 and -1 stated + we have an unreachable state
        r[0][3] = 1;
        r[1][3] = -1;
        r[1][1] = 0;
    }

}
