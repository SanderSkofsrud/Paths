package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ActionsTest {

  Player player = new Player("Test", 10, 30, 50);

  @Nested
  class GoldActionTest {

    @Test
    void execute() {
      GoldAction goldAction = new GoldAction(10);
      goldAction.execute(player);
      assertEquals(60, player.getGold());
    }
  }

  @Nested
  class HealthActionTest {
    @Test
    void execute (){
      HealthAction healthAction = new HealthAction(10);
      healthAction.execute(player);
      assertEquals(20, player.getHealth());
    }
  }

  @Nested
  class InventoryActionTest {

    @Test
    void execute() {
      InventoryAction inventoryAction = new InventoryAction("Item");
      inventoryAction.execute(player);
      assertEquals(1, player.getInventory().size());
    }
  }

  @Nested
  class ScoreActiontest {
    @Test
    void execute() {
      ScoreAction scoreAction = new ScoreAction(10);
      scoreAction.execute(player);
      assertEquals(40, player.getScore());

    }
  }
}

