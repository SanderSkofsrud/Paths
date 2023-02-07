package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

public class ScoreGoal implements Goal {
  private final int minimumScore;

  public ScoreGoal(int minimumScore) { this.minimumScore = minimumScore; }

  @Override
  public boolean isFulfilled(Player player) { return player.getScore() >= minimumScore; }
}
