package edu.ntnu.idatt2001.paths.models.actions;

import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Action factory test.
 */
class ActionFactoryTest {

  @Nested
  @DisplayName("Testing createAction method")
  class CreateActionTest {
    @Test
    @DisplayName("Should create a GoldAction when given GOLD type")
    void createGoldAction() {
      Action action = ActionFactory.createAction(ActionEnum.GOLD, "10");
      assertTrue(action instanceof GoldAction);
    }

    @Test
    @DisplayName("Should create a HealthAction when given HEALTH type")
    void createHealthAction() {
      Action action = ActionFactory.createAction(ActionEnum.HEALTH, "10");
      assertTrue(action instanceof HealthAction);
    }

    @Test
    @DisplayName("Should create a ScoreAction when given SCORE type")
    void createScoreAction() {
      Action action = ActionFactory.createAction(ActionEnum.SCORE, "10");
      assertTrue(action instanceof ScoreAction);
    }

    @Test
    @DisplayName("Should create an InventoryAction when given INVENTORY type")
    void createInventoryAction() {
      Action action = ActionFactory.createAction(ActionEnum.INVENTORY, "item");
      assertTrue(action instanceof InventoryAction);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid action type")
    void createInvalidAction() {
      assertThrows(NullPointerException.class, () -> ActionFactory.createAction(null, "10"));
    }
  }
}
