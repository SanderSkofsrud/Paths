package edu.ntnu.idatt2001.paths.models.goals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Goal factory test.
 */
class GoalFactoryTest {
  private GoalFactory goalFactory;

  @BeforeEach
  void setUp() {
    goalFactory = new GoalFactory();
  }

  @Nested
  @DisplayName("Testing createGoal method")
  class CreateGoalTest {
    @Test
    @DisplayName("Should create a GoldGoal when given GOLD type")
    void createGoldGoal() {
      Goal goal = GoalFactory.createGoal(GoalEnum.GOLD, 10);
      assertTrue(goal instanceof GoldGoal);
    }

    @Test
    @DisplayName("Should create a HealthGoal when given HEALTH type")
    void createHealthGoal() {
      Goal goal = GoalFactory.createGoal(GoalEnum.HEALTH, 10);
      assertTrue(goal instanceof HealthGoal);
    }

    @Test
    @DisplayName("Should create a ScoreGoal when given SCORE type")
    void createScoreGoal() {
      Goal goal = GoalFactory.createGoal(GoalEnum.SCORE, 10);
      assertTrue(goal instanceof ScoreGoal);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid goal type")
    void createInvalidGoal() {
      assertThrows(NullPointerException.class, () -> GoalFactory.createGoal(null, 10));
    }
  }

  @Nested
  @DisplayName("Testing createInventoryGoal method")
  class CreateInventoryGoalTest {
    @Test
    @DisplayName("Should create an InventoryGoal")
    void createInventoryGoal() {
      Goal goal = GoalFactory.createInventoryGoal("item1", "item2", "item3");
      assertTrue(goal instanceof InventoryGoal);
    }
  }
}
