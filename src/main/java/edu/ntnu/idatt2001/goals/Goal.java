package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

/**
 * The interface Goal.
 */
public interface Goal {

  /**
   * Checks if a goal is fulfilled.
   *
   * @param player the player
   * @return the boolean
   */
  boolean isFulfilled(Player player);
}
