package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;

/**
 * The interface Action.
 */
public class GoldAction implements Action {
  private final int gold;

  /**
   * Instantiates a new Gold action.
   *
   * @param gold the gold
   */
  public GoldAction(int gold) {
    this.gold = gold;
  }

  /**
   * Executes a gold action.
   *
   * @param player the player
   */
  @Override
  public void execute(Player player) {
    if (player.getGold() + this.gold < 0) {
      throw new IllegalArgumentException("Gold cannot be less than 0");
    }
    player.addGold(this.gold);
  }

  @Override
  public String toString() {
    return "{GoldAction}(" + this.gold + ")";
  }
}
