package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;

/**
 * Gold action.
 * An action that adds or removes gold to the player when executed.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 - 11.04.2023
 */
public class GoldAction implements Action {
  private final int gold;

  /**
   * Instantiates a new Gold action which adds or removes gold to the player.
   *
   * @param gold the amount of gold to add or remove
   */
  public GoldAction(int gold) {
    this.gold = gold;
  }

  /**
   * Executes a gold action on a player.
   *
   * @param player the player
   * @throws IllegalArgumentException if the player's gold become less than 0
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player.getGold() + this.gold < 0) {
      throw new IllegalArgumentException("Your balance got lower than 0");
    }
    player.addGold(this.gold);
  }

  /**
   * Returns a string representation of the GoldAction object.
   *
   * @return a string representation of the GoldAction object.
   */
  @Override
  public String toString() {
    return "{GoldAction}(" + this.gold + ")";
  }
}
