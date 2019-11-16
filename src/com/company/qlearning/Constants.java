package com.company.qlearning;

public class Constants {

    public static final double MIN_VALUE = -1e5;

    // Number of states
    public static final int NUM_OF_STATES = 6;

    // Reward in non-terminal states (used to init R[][])
    public static final double STANDARD_REWARD = 0;
    public static final double EXIT_REWARD = 100;

    // Gamma discount factor: how to deal with future rewards [0,1]
    public static final double GAMMA = 0.9;

    // Learning rate
    public static final double ALPHA = 0.1;

    // Number of iterations
    private static final int NUM_OF_EPISODES = 100_000;

    private Constants() {}
}
