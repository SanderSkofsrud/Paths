package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for GoldAction
 */
class GoldActionTest {
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

  /**
   * Test class for constructor
   */

  @Nested
  class Constructor {
    /**
     * Test that constructor constructs object
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      GoldAction goldAction = new GoldAction(10);
      assertEquals(GoldAction.class, goldAction.getClass());
    }
  }

  /**
   * Test class for execute
   */
  @Nested
  class Execute {
    /**
     * Test that gold is added to player
     */
    @Test
    @DisplayName("Test that gold is added to player")
    void testGoldActionAddsGoldToPlayer() {
      Player player = new Player(name, health, score, gold);
      GoldAction goldAction = new GoldAction(10);
      goldAction.execute(player);
      assertEquals(60, player.getGold());
    }

    /**
     * Test exception is thrown when gold is less than 0
     */
    @Test
    @DisplayName("Test that exception is thrown when gold is less than 0")
    void testGoldActionThrowsExceptionWhenGoldIsLessThanZero() {
      Player player = new Player(name, health, score, gold);
      GoldAction goldAction = new GoldAction(-60);
      assertThrows(IllegalArgumentException.class, () -> goldAction.execute(player));
    }
  }
}
