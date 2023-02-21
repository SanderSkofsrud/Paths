package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class InventoryActionTest {
  @Test
  void execute() {
    Player player = new Player("Test", 10, 30, 50);
    InventoryAction inventoryAction = new InventoryAction("Item");
    inventoryAction.execute(player);
    assertEquals("Item", player.getInventory().get(0));
  }

  @Test
  void testInventoryActionThrowsExceptionWhenItemIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new InventoryAction(null));
  }
}
