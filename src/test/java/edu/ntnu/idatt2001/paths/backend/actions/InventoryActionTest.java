package edu.ntnu.idatt2001.paths.backend.actions;

import edu.ntnu.idatt2001.paths.backend.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for InventoryAction
 */
class InventoryActionTest {
  String name;
  int health;
  int score;
  int gold;

  /**
   * Sets up the test class
   */
  @BeforeEach
  void setUp() {
    name = "test";
    health = 10;
    score = 30;
    gold = 50;
  }

  @Nested
  class Constructor {
    /**
     * Test that constructor constructs object
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      InventoryAction inventoryAction = new InventoryAction("Item");
      assertEquals(InventoryAction.class, inventoryAction.getClass());
    }
  }

  @Nested
  class Execute {
    /**
     * Test that item is added to inventory
     */
    @Test
    @DisplayName("Test that item is added to inventory")
    void testInventoryActionAddsItemToInventory() {
      Player player = new Player(name, health, score, gold);
      InventoryAction inventoryAction = new InventoryAction("Item");
      inventoryAction.execute(player);
      assertEquals("Item", player.getInventory().get(0));
    }

    /**
     * Test that exception is thrown when item is null
     */
    @Test
    @DisplayName("Test that exception is thrown when item is null")
    void testInventoryActionThrowsExceptionWhenItemIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new InventoryAction(null));
    }
  }
}
