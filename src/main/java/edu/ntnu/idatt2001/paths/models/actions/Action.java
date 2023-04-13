package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.Player;

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
