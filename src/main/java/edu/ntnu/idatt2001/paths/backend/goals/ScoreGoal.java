package edu.ntnu.idatt2001.paths.backend.goals;

import edu.ntnu.idatt2001.paths.backend.Player;

/**
 * The interface Goal.
 */
public class ScoreGoal implements Goal {
  private final int minimumScore;

  /**
   * Instantiates a new Score goal.
   *
   * @param minimumScore the minimum score
   */
  public ScoreGoal(int minimumScore) {
    if (minimumScore < 0) {
      throw new IllegalArgumentException("Minimum score cannot be negative");
    }
    this.minimumScore = minimumScore;
  }

  /**
   * Checks if a goal is fulfilled.
   *
   * @param player the player
   * @return the boolean
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getScore() >= minimumScore;
  }
}
