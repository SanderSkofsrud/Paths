package edu.ntnu.idatt2001.paths.backend.goals;

import edu.ntnu.idatt2001.paths.backend.Player;

/**
 * Health goal.
 * A goal that is fulfilled when the player has a certain amount of health.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class HealthGoal implements Goal {
  private final int minimumHealth;

  /**
   * Instantiates a new Health goal.
   * Creates a new goal for a certain amount of health.
   *
   * @param minimumHealth the minimum health
   */
  public HealthGoal(int minimumHealth) {
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("Health goal cannot be less than 0");
    }
    this.minimumHealth = minimumHealth;
  }

  /**
   * Checks if a goal is fulfilled.
   * Calls the player's getHealth method to check if the player has more or equal to the minimum health.
   *
   * @param player the player
   * @return the boolean - true if the goal is fulfilled, false if not
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }
}
