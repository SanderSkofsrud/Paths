package edu.ntnu.idatt2001.paths.models.goals;
import java.util.List;

/**
 * The type Goal factory.
 * The GoalFactory class is used to create different types of goals.
 * The class is used in the Game class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class GoalFactory {

  /**
   * Create a new goal.
   * The createGoal method is used to create different types of goals.
   *
   * @param type  the type of goal
   * @param value the value of the goal
   * @return the goal that is created
   */
  public static Goal createGoal(GoalEnum type, int value) {
    return switch (type) {
      case GOLD -> new GoldGoal(value);
      case HEALTH -> new HealthGoal(value);
      case SCORE -> new ScoreGoal(value);
      default -> throw new NullPointerException("Invalid goal type: " + type);
    };
  }

  /**
   * Create a new goal with a list of items.
   * The createInventoryGoal method is used to create a goal where the player has to collect a list of items.
   *
   * @param items the items the player has to collect
   * @return the goal that is created
   */
  public static Goal createInventoryGoal(String... items) {
    return new InventoryGoal(List.of(items));
  }

  // Optional: createInventoryGoal overloaded method to take in a list of items instead
}
