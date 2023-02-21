package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class GoldActionTest {

  @Test
    void execute() {
    Player player = new Player("Test", 10, 30, 50);
    GoldAction goldAction = new GoldAction(10);
      goldAction.execute(player);

      assertEquals(60, player.getGold());
    }
  }
