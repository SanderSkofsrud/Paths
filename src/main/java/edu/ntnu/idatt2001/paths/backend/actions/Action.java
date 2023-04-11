package edu.ntnu.idatt2001.paths.backend.actions;

import edu.ntnu.idatt2001.paths.backend.Player;

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

  @Override
  String toString();
}
