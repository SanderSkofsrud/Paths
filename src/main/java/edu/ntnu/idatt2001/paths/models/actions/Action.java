package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;

/**
 * The interface Action.
 * An action will modify the player´s attributes.
 * Have a method that executes the action when a player goes through a link.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public interface Action {
  /**
   * Execute an action.
   * Modifies the player´s attributes.
   *
   * @param player the player
   */
  void execute(Player player);

  /**
   * Returns a string representation of the Action object.
   *
   * @return a string representation of the Action object.
   */
  @Override
  String toString();
}
