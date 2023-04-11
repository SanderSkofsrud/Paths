package edu.ntnu.idatt2001.paths.backend.actions;

import edu.ntnu.idatt2001.paths.backend.Player;

import java.util.ArrayList;

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
    if (item.isBlank()) throw new IllegalArgumentException("Item cannot be blank");
    this.item = item.trim().toLowerCase();
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

  @Override
  public String toString() {
    return "{InventoryAction}(" + this.item + ")";
  }
}