package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

import java.util.ArrayList;
import java.util.List;

public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    if (mandatoryItems == null) {
      throw new IllegalArgumentException("Mandatory items cannot be null");
    }
    if (mandatoryItems.isEmpty()) {
      throw new IllegalArgumentException("Mandatory items cannot be empty");
    }
    this.mandatoryItems = mandatoryItems;
  }

  @Override
  public boolean isFulfilled(Player player) {
    return player.getInventory().containsAll(this.mandatoryItems);
  }
}
