package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InventoryGoal
 */
class InventoryGoalTest {
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
      List<String> mandatoryItems = new ArrayList<>();
      mandatoryItems.add("Test1");
      mandatoryItems.add("Test2");
      InventoryGoal inventoryGoal = new InventoryGoal(mandatoryItems);
      assertEquals(InventoryGoal.class, inventoryGoal.getClass());
    }
  }

  @Nested
  class IsFulfilled {
    /**
     * Test that inventory goal returns expected result when correct
     */
    @Test
    @DisplayName("Test that inventory goal returns expected result when correct")
    void testThatInventoryGoalReturnsExpectedResultWhenCorrect() {
      Player player = new Player("Test", 20, 30, 50);
      List<String> mandatoryItems = new ArrayList<>();
      mandatoryItems.add("Test1");
      mandatoryItems.add("Test2");
      InventoryGoal inventoryGoal = new InventoryGoal(mandatoryItems);
      player.addToInventory("Test1");
      player.addToInventory("Test2");
      assertTrue(inventoryGoal.isFulfilled(player));
    }

    /**
     * Test that inventory goal returns expected result when incorrect
     */
    @Test
    @DisplayName("Test that inventory goal returns expected result when incorrect")
    void testThatInventoryGoalReturnsExpectedResultWhenIncorrect() {
      Player player = new Player("Test", 20, 30, 50);
      List<String> mandatoryItems = new ArrayList<>();
      mandatoryItems.add("Test1");
      mandatoryItems.add("Test2");
      InventoryGoal inventoryGoal = new InventoryGoal(mandatoryItems);
      player.addToInventory("Test1");
      player.addToInventory("Test3");
      assertFalse(inventoryGoal.isFulfilled(player));
    }

    /**
     * Test that inventory goal throws exception when mandatory items is null
     */
    @Test
    @DisplayName("Test that inventory goal throws exception when mandatory items is null")
    void testThatInventoryGoalThrowsExceptionWhenMandatoryItemsIsNull() {
      assertThrows(IllegalArgumentException.class, () -> new InventoryGoal(null));
    }
  }
}
