package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

/**
 * The interface Goal.
 */
public class GoldGoal implements Goal {
  private final int minimumGold;

  /**
   * Instantiates a new Gold goal.
   *
   * @param minimumGold the minimum gold
   */
  public GoldGoal(int minimumGold) {
    if (minimumGold < 0) {
      throw new IllegalArgumentException("Minimum gold cannot be negative");
    }
    this.minimumGold = minimumGold;
  }

  /**
   * Checks if a goal is fulfilled.
   *
   * @param player the player
   * @return the boolean
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getGold() >= minimumGold;
  }
}
