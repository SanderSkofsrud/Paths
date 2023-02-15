package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
class ScoreActionTest {
  @Test
  void isFulfilled() {
    Player player = new Player("Test", 20, 30, 50);
    ScoreGoal scoreGoal = new ScoreGoal(10);
    assertTrue(scoreGoal.isFulfilled(player));
  }
}
