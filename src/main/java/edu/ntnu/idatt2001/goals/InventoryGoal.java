package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

import java.util.List;

/**
 * The type Inventory goal.
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Instantiates a new Inventory goal.
   *
   * @param mandatoryItems the mandatory items
   */
  public InventoryGoal(List<String> mandatoryItems) {
    if (mandatoryItems == null) {
      throw new IllegalArgumentException("Mandatory items cannot be null");
    }
    if (mandatoryItems.isEmpty()) {
      throw new IllegalArgumentException("Mandatory items cannot be empty");
    }
    this.mandatoryItems = mandatoryItems;
  }

  /**
   * Checks if a goal is fulfilled.
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
}
