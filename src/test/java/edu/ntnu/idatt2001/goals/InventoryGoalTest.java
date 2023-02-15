package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class InventoryGoalTest {
  @Test
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

  @Test
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

  @Test
  void testThatInventoryGoalThrowsExceptionWhenMandatoryItemsIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new InventoryGoal(null));
  }
}
