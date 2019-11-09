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

    public void run() {

        do {
            copyArray(vNext, v);
            n++;
            delta = 0;

            for (int rowIndex = 0; rowIndex < NUM_ROWS; rowIndex++) {
                for (int columnIndex = 0; columnIndex < NUM_COLUMNS; columnIndex++) {
                    // Manipulate cNext
                    update(rowIndex, columnIndex);
                    double error = Math.abs(vNext[rowIndex][columnIndex] - v[rowIndex][columnIndex]);

                    // We make sure every state s it will converge |V(s) - V`(s)| < delta
                    // Track the maximum error (as many eeeor terms as the nim of states)
                    // If the max error is smaller the epsilon -> the algorithm has converged!
                    if (error > delta) {
                        delta = error;
                    }
                }
            }

        } while (delta > EPSILON && n < NUM_OF_ITER);

        printResult();
    }

    public void printResult() {
        // Display the V(s) value - function values
        System.out.println("The V(s) values after " + n + "iterations:\n");
        for (int rowIndex = 0; rowIndex < NUM_ROWS; rowIndex++) {
            for (int columnIndex = 0; columnIndex < NUM_COLUMNS; columnIndex++) {
                System.out.printf("% 6.2f\t", v[rowIndex][columnIndex]);
            }
            System.out.println("\n");
        }

        pi[0][3] = '+';
        pi[1][3] = '-';
        pi[1][1] = '@';

        // Display the pi(s) policy-function: prints out what action to do in every state
        System.out.println("Best Policy\n");
        for (int rowIndex = 0; rowIndex < NUM_ROWS; rowIndex++) {
            for (int columnIndex = 0; columnIndex < NUM_COLUMNS; columnIndex++) {
                System.out.print(pi[rowIndex][columnIndex] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }

    public void update(int row, int col) {

        double actions[] = new double[4];

        // +1, -1 or obstacle state - use that value
        if ((row == 0 && col == 3) || (row == 1 && col == 3) || (row == 1 && col == 1)) {
            vNext[row][col] = r[row][col];
        } else {
            // Calculate the P(s`'s,a) * V(s`) values
            actions[0] = ACTION_PROB * goUp(row, col) + ACTION_MISS_PROB * goLeft(row, col);
            actions[1] = ACTION_PROB * goDown(row, col) + ACTION_MISS_PROB * goLeft(row, col);
            actions[2] = ACTION_PROB * goLeft(row, col) + ACTION_MISS_PROB * goDown(row, col);
            actions[3] = ACTION_PROB * goRight(row, col) + ACTION_MISS_PROB * goDown(row, col);

            // We want to find the optimal action so finding the max
            int best = findMaxIndex(actions);

            // We calculate V with Bellman-equation - V(s) = R(s) + max[gamma * sum(P(s`|s,a) * V(s`))]
            vNext[row][col] = r[row][col] + GAMMA * actions[best];

            // Update policy (argmax implementation)
            switch (best) {
                case 0:
                    pi[row][col] = 'U';
                    break;
                case 1:
                    pi[row][col] = 'D';
                    break;
                case 2:
                    pi[row][col] = 'L';
                    break;
                case 3:
                    pi[row][col] = 'R';
                    break;
            }
        }
    }

    private int findMaxIndex(double[] actions) {
        int maxIndex = 0;

        for (int i = 1; i < actions.length; i++) {
            if (actions[i] > actions[maxIndex])
                maxIndex = i;
        }

        return maxIndex;
    }

    private double goUp(int row, int col) {
        // Check whether it is possible to go up
        // If cant: stay at the same state
        if ((row == 0) || (row == 2 && col == 1))
            return v[row][col];

        return v[row - 1][col];
    }

    private double goDown(int row, int col) {
        // Check whether it is possible to go down
        // If cant: stay at the same state
        if ((row == NUM_ROWS - 1) || (row == 0 && col == 1))
            return v[row][col];

        return v[row + 1][col];
    }

    private double goLeft(int row, int col) {
        // Check whether it is possible to go left
        // If cant: stay at the same state
        if ((col == 0) || (row == 1 && col == 2))
            return v[row][col];

        return v[row][col - 1];
    }

    private double goRight(int row, int col) {
        // Check whether it is possible to go right
        // If cant: stay at the same state
        if ((col == NUM_COLUMNS - 1) || (row == 1 && col == 0))
            return v[row][col];

        return v[row][col + 1];
    }

    public void copyArray(double[][] sourceArray, double[][] destArray) {
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = 0; j < sourceArray[i].length; j++) {
                destArray[i][j] = sourceArray[i][j];
            }
        }
    }

}