package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HealthActionTest {
  @Test
  void execute (){
    Player player = new Player("Test", 10, 30, 50);
    HealthAction healthAction = new HealthAction(10);
    healthAction.execute(player);

    assertEquals(20, player.getHealth());
  }

  @Test
  void testHealthActionThrowsExceptionWhenHealthIsLessThanZero() {
    Player player = new Player("Test", 10, 30, 50);
    HealthAction healthAction = new HealthAction(-20);
    assertThrows(IllegalArgumentException.class, () -> healthAction.execute(player));
  }
}
