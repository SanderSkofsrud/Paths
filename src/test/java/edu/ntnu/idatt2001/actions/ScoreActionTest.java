package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ScoreActionTest {
  @Test
  void execute() {
    Player player = new Player("Test", 10, 30, 50);
    ScoreAction scoreAction = new ScoreAction(10);
    scoreAction.execute(player);

    assertEquals(40, player.getScore());

  }
}
