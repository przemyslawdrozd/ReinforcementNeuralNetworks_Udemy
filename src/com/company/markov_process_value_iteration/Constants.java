package com.company.markov_process_value_iteration;

public class Constants {

    private Constants(){
    }

    // reward in non-terminal states (used to initialize r[][])
    public static double STATE_REWARD = -0.1;
    // gamma discount factor: how to deal with future rewards
    public static double GAMMA = 0.99;
    // want to make the system stochastic: with 80% probability we go where we want
    public static double ACTION_PROB = 0.8;
    // with 10 % probability we go in another direction (left or right)
    public static double ACTION_MISS_PROB = 0.1;
    // number of iteration
    public static int NUM_OF_ITER = 100;
    // to measure the difference |V(actual) - V(optimal)| ~ convergence criterion
    public static double EPSILON = 1e-7;
    // broad`s height
    public static int NUM_ROWS = 3;
    // broad`s width
    public static int NUM_COLUMNS = 4;
}
