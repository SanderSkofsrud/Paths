package edu.ntnu.idatt2001.paths.models.goals;

import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.player.PlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GoldGoal
 */
class GoldGoalTest {
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
      GoldGoal goldGoal = new GoldGoal(10);
      assertEquals(GoldGoal.class, goldGoal.getClass());
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
      Player player = new PlayerBuilder(name).health(health).score(score).gold(100).build();
      GoldGoal goldGoal = new GoldGoal(10);
      assertTrue(goldGoal.isFulfilled(player));
    }

    /**
     * Test that goal does not get marked as completed when not completed
     */
    @Test
    @DisplayName("Test that goal does not get marked as completed when not completed")
    void testThatGoalDoesNotGetMarkedAsCompletedWhenNotCompleted() {
      Player player = new PlayerBuilder(name).health(health).score(score).gold(50).build();
      GoldGoal goldGoal = new GoldGoal(100);
      assertFalse(goldGoal.isFulfilled(player));
    }

    /**
     * Test that exception is thrown when goal is less than 0
     */
    @Test
    @DisplayName("Test that exception is thrown when goal is less than 0")
    void testThatExceptionIsThrownWhenGoalIsLessThanZero() {
      assertThrows(IllegalArgumentException.class, () -> new GoldGoal(-10));
    }
  }

  /**
   * Test class for file handling
   */
  @Nested
  class fileHandling {
    /**
     * Test that toString returns correct format
     * The toString in gold, health and score is identical. Therefore, this test should cover them all.
     */
    @Test
    @DisplayName("Test that toString returns correct string")
    void testThatToStringReturnsCorrectString() {
      GoldGoal goldGoal = new GoldGoal(10);
      assertEquals("GoldGoal: 10", goldGoal.toString());
    }
  }
}
