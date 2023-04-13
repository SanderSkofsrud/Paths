package edu.ntnu.idatt2001.paths.backend.goals;

import edu.ntnu.idatt2001.paths.backend.Player;

/**
 * Score goal.
 * A goal that is fulfilled when the player has a certain score.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class ScoreGoal implements Goal {
  private final int minimumScore;

  /**
   * Instantiates a new Score goal.
   * Creates a new goal for a certain score.
   *
   * @param minimumScore the minimum score for the goal to be fulfilled
   * @throws IllegalArgumentException if the minimum score is negative
   */
  public ScoreGoal(int minimumScore) {
    if (minimumScore < 0) {
      throw new IllegalArgumentException("Minimum score cannot be negative");
    }
    this.minimumScore = minimumScore;
  }

  /**
   * Checks if a goal is fulfilled.
   * Calls the player's getScore method to check if the player has more or equal to the minimum score.
   *
   * @param player the player
   * @return the boolean - true if the goal is fulfilled, false if not
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getScore() >= minimumScore;
  }
}
