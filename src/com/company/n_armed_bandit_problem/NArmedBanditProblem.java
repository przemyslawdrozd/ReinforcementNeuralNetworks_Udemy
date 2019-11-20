package com.company.n_armed_bandit_problem;

import java.util.Random;

import static com.company.n_armed_bandit_problem.Constants.*;

public class NArmedBanditProblem {

    private Bandit[] bandits;
    private Random random;

    public NArmedBanditProblem() {
        this.random = new Random();
        this.bandits = new Bandit[NUM_OF_BANDITS];

        // There are 3 bandits: 30%, 60%, 50% probability of winning
        this.bandits[0] = new Bandit(0.3);
        this.bandits[1] = new Bandit(0.6);
        this.bandits[2] = new Bandit(0.5);
    }

    public void run() {

        // Making several iterations/episodes
        for (int i = 0; i <NUM_OF_EPISODES; i++) {
            // Choose a given bandit with epsilon-greedy strategy
            Bandit bandit = this.bandits[chooseBandit()];
            int reward = bandit.getReward();
            updateQ(bandit, reward);
            System.out.println("Iteration #" + i + ": " + bandit + " with Q value: " + bandit.getQ());
        }
    }

    private void updateQ(Bandit bandit, int reward) {
        // We use the formula to update the Q(a) values
        bandit.setK(bandit.getK() + 1);
        bandit.setQ(bandit.getQ() + (1.0 / (1 + bandit.getK())) * (reward - bandit.getQ()));
    }

    private int chooseBandit() {

        int nextBanditIndex = 0;
        double randomNumber = random.nextDouble();

        // This is the epsilon-greedy strategy
        // With epsilon probability the agent explore ... otherwise it exploit
        if (randomNumber < EPSILON) {
            nextBanditIndex = random.nextInt(this.bandits.length);
        } else {
            nextBanditIndex = getBanditMaxQ();
        }
        return  nextBanditIndex;
    }

    private int getBanditMaxQ() {

        // We find the bandit with max Q(a) value for the greedy exploration
        // We need the index of the bandit with max Q(a)
        int maxQBanditIndex = 0;
        double maxQ = this.bandits[0].getQ();

        for (int i = 1; i < this.bandits.length; i++) {
            if (this.bandits[i].getQ() > maxQ) {
                maxQ = this.bandits[i].getQ();
                maxQBanditIndex = i;
            }
        }
        return maxQBanditIndex;
    }

    public void showStatistics() {
        for (Bandit bandit: this.bandits) {
            System.out.println("\nResult\n" + bandit + " - " + bandit.getK());
        }
    }
}















