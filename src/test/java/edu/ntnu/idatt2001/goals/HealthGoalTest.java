package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HealthGoalTest {
  @Test
  void isFulfilled() {
    Player player = new Player("Test", 20, 30, 50);
    HealthGoal healthGoal = new HealthGoal(10);
    assertTrue(healthGoal.isFulfilled(player));
  }

  @Test
  void testHealthGoalReturnsExpectedResultWhenIncorrect(){
    Player player = new Player("Test", 20, 30, 50);
    HealthGoal healthGoal = new HealthGoal(30);
    assertFalse(healthGoal.isFulfilled(player));
  }

  @Test
  void testHealthGoalThrowsExceptionWhenHealthIsLessThanZero() {
    assertThrows(IllegalArgumentException.class, () -> new HealthGoal(-20));
  }
}
