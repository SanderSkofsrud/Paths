package edu.ntnu.idatt2001.paths.models.goals;

import edu.ntnu.idatt2001.paths.models.Player;

/**
 * The interface Goal.
 */
public class HealthGoal implements Goal {
  private final int minimumHealth;

  /**
   * Instantiates a new Health goal.
   *
   * @param minimumHealth the minimum health
   */
  public HealthGoal(int minimumHealth) {
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("Health cannot be less than 0");
    }
    this.minimumHealth = minimumHealth;
  }

  /**
   * Checks if a goal is fulfilled.
   *
   * @param player the player
   * @return the boolean
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }
}
