package edu.ntnu.idatt2001.paths.models.goals;
import java.util.List;

public class GoalFactory {

  public static Goal createGoal(GoalEnum type, int value) {
    return switch (type) {
      case GOLD -> new GoldGoal(value);
      case HEALTH -> new HealthGoal(value);
      case SCORE -> new ScoreGoal(value);
      default -> throw new IllegalArgumentException("Invalid goal type: " + type);
    };
  }

  public static Goal createInventoryGoal(String... items) {
    return new InventoryGoal(List.of(items));
  }

  // Optional: createInventoryGoal overloaded method to take in a list of items instead
}
