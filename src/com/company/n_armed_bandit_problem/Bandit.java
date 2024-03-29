package com.company.n_armed_bandit_problem;

import java.util.Random;

public class Bandit {

    // The bandit has 3 params: Q(a), k and the probability distribution
    // Qk(a) stores the mean of awards
    private double q;

    // k means how many times action a (so the bandit) was chosen in the past
    private int k;

    // probability distribution: 60% means the agent wins +1 reward with 60% probability
    private double probability;

    // We need some stochastic behaviour
    private Random random;

    public Bandit(double probability) {
        this.probability = probability;
        this.random = new Random();
    }

    public int getReward() {

        // The agent gets a reward with x% chance: x is the probability!
        double randomProbability = random.nextDouble();

        // Rewards ca  be +1 (win) or 0 (lose)
        if (randomProbability < this.probability) {
            return +1;
        } else {
            return 0;
        }
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "Bandit p " + this.probability;
    }
}
