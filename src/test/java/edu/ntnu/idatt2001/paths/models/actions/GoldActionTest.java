package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;
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
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
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
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
      GoldAction goldAction = new GoldAction(-60);
      assertThrows(IllegalArgumentException.class, () -> goldAction.execute(player));
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
    void testToStringReturnsCorrectString() {
      GoldAction goldAction = new GoldAction(10);
      assertEquals("{GoldAction}(10)", goldAction.toString());
    }
  }
}
