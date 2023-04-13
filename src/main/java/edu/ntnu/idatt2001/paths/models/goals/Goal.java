package edu.ntnu.idatt2001.paths.models.goals;

import edu.ntnu.idatt2001.paths.models.Player;

/**
 * The interface Goal.
 * A goal will be fulfilled when a player has reached a certain attribute.
 * Goal checks if the player has reached a goal.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public interface Goal {

  /**
   * Checks if a goal is fulfilled.
   *
   * @param player the player
   * @return the boolean - true if the goal is fulfilled, false if not
   */
  boolean isFulfilled(Player player);
}
