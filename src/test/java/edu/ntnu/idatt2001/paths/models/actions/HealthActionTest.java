package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for HealthAction
 */
class HealthActionTest {
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
      HealthAction healthAction = new HealthAction(10);
      assertEquals(HealthAction.class, healthAction.getClass());
    }
  }

  @Nested
  class Execute {
    /**
     * Test that health is added to player
     */
    @Test
    @DisplayName("Test that health is added to player")
    void testHealthActionAddsHealthToPlayer() {
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
      HealthAction healthAction = new HealthAction(10);
      healthAction.execute(player);
      assertEquals(20, player.getHealth());
    }

    /**
     * Test exception is thrown when health is less than 0
     */
    @Test
    @DisplayName("Test that exception is thrown when health is 0 or less")
    void testGoldActionThrowsExceptionWhenHealthIsZeroOrLess() {
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
      HealthAction healthAction = new HealthAction(-20);
      assertThrows(IllegalArgumentException.class, () -> healthAction.execute(player));
    }
  }
}
