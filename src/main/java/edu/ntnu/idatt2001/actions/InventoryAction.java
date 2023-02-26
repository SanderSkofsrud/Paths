package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;

/**
 * The interface Action.
 */
public class InventoryAction implements Action {
  private final String item;

  /**
   * Instantiates a new Inventory action.
   *
   * @param item the item
   */
  public InventoryAction(String item) {
    if (item == null) throw new IllegalArgumentException("Item cannot be null");
    if (item.isEmpty()) throw new IllegalArgumentException("Item cannot be empty");
    this.item = item;
  }

  /**
   * Executes a inventory action.
   *
   * @param player the player
   */
  @Override
  public void execute(Player player) {
    player.addToInventory(item);
  }
}