package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;

public class InventoryAction implements Action {
  private final String item;

  public InventoryAction(String item) {
    if (item == null) throw new IllegalArgumentException("Item cannot be null");
    if (item.isEmpty()) throw new IllegalArgumentException("Item cannot be empty");
    this.item = item;
  }

  @Override
  public void execute(Player player) { player.addToInventory(item);}
}