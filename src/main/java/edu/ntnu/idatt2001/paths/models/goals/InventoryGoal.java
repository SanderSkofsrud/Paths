package edu.ntnu.idatt2001.paths.models.goals;

import edu.ntnu.idatt2001.paths.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Inventory goal.
 * A goal that is fulfilled when the player has a certain amount of items.
 * The items are case insensitive and trimmed.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Instantiates a new Inventory goal.
   * Creates a new goal for a certain amount of items.
   * The items are trimmed and converted to lowercase.
   *
   * @param mandatoryItems the mandatory items - a list of items that must be in the player's inventory
   * @throws NullPointerException if the list of mandatory items is null
   * @throws IllegalArgumentException if the list of mandatory items is empty
   *
   */
  public InventoryGoal(List<String> mandatoryItems) {
    Objects.requireNonNull(mandatoryItems, "Mandatory items cannot be null");
    if (mandatoryItems.isEmpty()) {
      throw new IllegalArgumentException("Mandatory items cannot be empty");
    }
    (this.mandatoryItems = new ArrayList<>()).addAll
        (mandatoryItems.stream().map(String::trim).map(String::toLowerCase).toList());
  }

  /**
   * Checks if a goal is fulfilled.
   * Calls the player's getInventory method to check if the player has all the mandatory items.
   *
   * @param player the player
   * @return the boolean
   */
  @Override
  public boolean isFulfilled(Player player) {
    return player.getInventory().containsAll(this.mandatoryItems);
    // Alternative solution:
    //return new HashSet<>(player.getInventory()).containsAll(this.mandatoryItems);
  }

  /**
   * Returns a string representation of the InventoryGoal object.
   *
   * @return a string representation of the InventoryGoal object.
   */
  @Override
  public String toString() {
    return "(InventoryGoal)(" + this.mandatoryItems + ")";
  }
}
