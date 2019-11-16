package com.company.qlearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.qlearning.Constants.*;

public class QLearning {

    // This R[][] stories the reward for every state
    private double[][] R = {
            {MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, MIN_VALUE},
            {MIN_VALUE, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, STANDARD_REWARD, EXIT_REWARD},
            {MIN_VALUE, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, MIN_VALUE, MIN_VALUE},
            {MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, MIN_VALUE},
            {STANDARD_REWARD, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, MIN_VALUE, EXIT_REWARD},
            {MIN_VALUE, STANDARD_REWARD, MIN_VALUE, MIN_VALUE, STANDARD_REWARD, EXIT_REWARD}
    };

    private double[][] Q;
    private Random random;

    public QLearning() {
        this.random = new Random();

        // Q values for going from s to s`
        this.Q = new double[NUM_OF_STATES][NUM_OF_STATES];
    }

    public void run() {

        // Episode: a full iteration when the agent starts from a random state and finds the terminal state
        for (int episodeState = 0; episodeState < NUM_OF_STATES; ++episodeState) {
            int state = random.nextInt(NUM_OF_STATES);
            // We do not want to start with the terminal state
            if (state == 5) continue;
            simulate(state);
        }
    }

    private void simulate(int state) {

        // A single episode: the agent finds a path from state s to the terminal state

        do {
            // get available actions (so available next state
            List<Integer> possibleNextStates = availableState(state);
            // Chosen a random next state
            int nextState = possibleNextStates.get(random.nextInt(possibleNextStates.size()));
            // The max Q value concerning the next state
            double maxQ = findMaxQ(nextState);
            // Q learning equation: Q[s][a] = Q[s][a] + alpha (R[s][a] + gamma (max Q[s`][a`]) - Q[s][a])
            Q[state][nextState] = Q[state][nextState] + ALPHA * (R[state][nextState] + GAMMA * maxQ - Q[state][nextState]);

            // Consider the next state: the agent considers the next state until it reaches the terminal one
            state = nextState;
        } while (state != 5);
    }

    // Finding the max Q value for the next state
    private double findMaxQ(int nextState) {

        double maxQ = MIN_VALUE;

        // That is why we use Q[][] it is easy to find the max Q value with
        // A simple linear search Q(N)
        for (int i = 0; i < this.Q.length; i++) {
            if (this.Q[nextState][i] > maxQ) {
                maxQ = this.Q[nextState][i];
            }
        }
        return maxQ;
    }

    private List<Integer> availableState(int state) {

        List<Integer> possibleNextStates = new ArrayList<>();

        // Get rhe available states: the R[][] matrix given row contain all the possible next states
        for (int colIndex = 0; colIndex < this.R.length; ++colIndex) {
            if (this.R[state][colIndex] > MIN_VALUE) {
                possibleNextStates.add(colIndex);
            }
        }

        return possibleNextStates;
    }

    public void showResult() {

        for (int i = 0; i < this.Q.length; ++i){
            for (int j = 0; j < this.Q.length; ++j) {
                System.out.printf("%.1f ", this.Q[i][j]);
            }
            System.out.println();
        }
    }

    public void showPolicy() {

        // Consider every state as a staring state until find the terminal state: we go in the direction of the max Q value
        for (int i = 0; i < NUM_OF_STATES; i++) {
            int state = i;
            System.out.print("Policy: " + state);

            while (state != 5) {
                int maxQState = 0;
                double maxQ = 0;

                for (int j = 0; j < NUM_OF_STATES; j++) {
                    if (Q[state][j] > maxQ) {
                        maxQ = Q[state][j];
                        maxQState = j;
                    }
                }
                System.out.print(" -> " + maxQState);
                state = maxQState;
            }
            System.out.println();
        }
    }

}