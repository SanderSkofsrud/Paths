package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.Player;
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
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      InventoryAction inventoryAction = new InventoryAction("Item");
      inventoryAction.execute(player);
      assertEquals("item", player.getInventory().get(0));
    }

    /**
     * Test that exception is thrown when item is null
     */
    @Test
    @DisplayName("Test that exception is thrown when item is null")
    void testInventoryActionThrowsExceptionWhenItemIsNull() {
      assertThrows(NullPointerException.class, () -> new InventoryAction(null));
    }

    /**
     * Test that exception is thrown when item is blank
     */
    @Test
    @DisplayName("Test that exception is thrown when item is blank")
    void testInventoryActionThrowsExceptionWhenItemIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> new InventoryAction(""));
    }
  }


  /**
   * Test class for toString
   */
  @Nested
  class toString {
    /**
     * Test that toString returns correct string
     */
    @Test
    @DisplayName("Test that toString returns correct string")
    void testToStringReturnsCorrectString() {
      InventoryAction inventoryAction = new InventoryAction("Item");
      assertEquals("{InventoryAction}(item)", inventoryAction.toString());
    }
  }
}
