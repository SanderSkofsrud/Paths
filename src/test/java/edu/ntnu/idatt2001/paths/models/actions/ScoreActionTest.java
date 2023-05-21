package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for ScoreAction
 */
class ScoreActionTest {
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
      ScoreAction scoreAction = new ScoreAction(10);
      assertEquals(ScoreAction.class, scoreAction.getClass());
    }
  }

  @Nested
  class Execute {
    /**
     * Test that score is added to player
     */
    @Test
    @DisplayName("Test that score is added to player")
    void testScoreActionAddsScoreToPlayer() {
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
      ScoreAction scoreAction = new ScoreAction(10);
      scoreAction.execute(player);
      assertEquals(40, player.getScore());
    }

    /**
     * Test that exception is thrown when score is less than 0
     */
    @Test
    @DisplayName("Test that exception is thrown when score is less than 0")
    void testScoreActionThrowsExceptionWhenScoreIsLessThanZero() {
      Player player = new Player.PlayerBuilder(name).health(health).score(score).gold(gold).build();
      ScoreAction scoreAction = new ScoreAction(-40);
      assertThrows(IllegalArgumentException.class, () -> scoreAction.execute(player));
    }
  }
}
