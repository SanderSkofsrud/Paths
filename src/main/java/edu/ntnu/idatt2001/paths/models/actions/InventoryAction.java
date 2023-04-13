package edu.ntnu.idatt2001.paths.backend.actions;

import edu.ntnu.idatt2001.paths.backend.Player;

import java.util.ArrayList;
import java.util.Objects;
import edu.ntnu.idatt2001.paths.models.Player;

/**
 * Inventory action.
 * An action that adds inventory items to the player when executed.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class InventoryAction implements Action {
  private final String item;

  /**
   * Instantiates a new Inventory action which adds inventory items to the player.
   * Trims the item and converts it to lowercase.
   *
   * @param item the item - a string representing of the item of the item to add
   * @throws NullPointerException if the item is null
   * @throws IllegalArgumentException if the item is already in the player´s inventory
   */
  public InventoryAction(String item) throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(item, "Item cannot be null");
    if (item.isBlank()) throw new IllegalArgumentException("Item cannot be blank");
    this.item = item.trim().toLowerCase();
  }

  /**
   * Executes an inventory action on a player.
   *
   * @param player the player
   */
  @Override
  public void execute(Player player) {
    player.addToInventory(item);
  }

  /**
   * Returns a string representation of the InventoryAction object.
   *
   * @return a string representation of the InventoryAction object.
   */
  @Override
  public String toString() {
    return "{InventoryAction}(" + this.item + ")";
  }
}