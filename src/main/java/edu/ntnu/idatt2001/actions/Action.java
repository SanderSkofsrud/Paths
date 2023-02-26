package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;

/**
 * The interface Action.
 */
public interface Action {
  /**
   * Execute an action.
   *
   * @param player the player
   */
  void execute(Player player);
}
