package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoalsTest {

  Player player = new Player("Test", 20, 30, 50);
  List<String> inventory = new ArrayList<>();
  String test = "Item";

  @Nested
  class GoldGoalTest {

    @Test
    void isFulfilled() {
      GoldGoal goldGoal = new GoldGoal(10);
      assertTrue(goldGoal.isFulfilled(player));
    }
  }

  @Nested
  class HealthGoalTest {

    @Test
    void isFulfilled() {
      HealthGoal healthGoal = new HealthGoal(10);
      assertTrue(healthGoal.isFulfilled(player));
    }
  }

  @Nested
  class InventoryGoalTest {

    @Test
    void isFulfilled() {
      InventoryGoal inventoryGoal = new InventoryGoal(inventory);
      assertTrue(inventoryGoal.isFulfilled(player));
    }
  }

  @Nested
  class ScoreGoalTest {

    @Test
    void isFulfilled() {
      ScoreGoal scoreGoal = new ScoreGoal(10);
      assertTrue(scoreGoal.isFulfilled(player));
    }
  }
}
