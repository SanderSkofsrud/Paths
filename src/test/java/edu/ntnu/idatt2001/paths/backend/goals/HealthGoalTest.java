package edu.ntnu.idatt2001.paths.backend.goals;

import edu.ntnu.idatt2001.paths.backend.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for HealthGoal
 */
class HealthGoalTest {
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
      HealthGoal healthGoal = new HealthGoal(10);
      assertEquals(HealthGoal.class, healthGoal.getClass());
    }
  }

  @Nested
  class IsFulfilled {
    /**
     * Test that goal gets marked as completed when completed
     */
    @Test
    @DisplayName("Test that goal gets marked as completed when completed")
    void testThatGoalGetsMarkedAsCompletedWhenCompleted() {
      Player player = new Player.Builder(name).health(100).score(score).gold(gold).build();
      HealthGoal healthGoal = new HealthGoal(10);
      assertTrue(healthGoal.isFulfilled(player));
    }

    /**
     * Test that goal does not get marked as completed when not completed
     */
    @Test
    @DisplayName("Test that goal does not get marked as completed when not completed")
    void testThatGoalDoesNotGetMarkedAsCompletedWhenNotCompleted() {
      Player player = new Player.Builder(name).health(health).score(score).gold(gold).build();
      HealthGoal healthGoal = new HealthGoal(30);
      assertFalse(healthGoal.isFulfilled(player));
    }

    /**
     * Test that exception is thrown when health is less than 0
     */
    @Test
    @DisplayName("Test that exception is thrown when health is less than 0")
    void testThatExceptionIsThrownWhenHealthIsLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> new HealthGoal(-20));
    }
  }
}
