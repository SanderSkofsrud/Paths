package edu.ntnu.idatt2001.paths.models.actions;

/**
 * The type Action factory.
 * The ActionFactory class is used to create different types of actions.
 * The class is used in the Action class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 20.05.2023
 */
public class ActionFactory {

  /**
   * Create a new action.
   * The createAction method is used to create different types of actions.
   *
   * @param type  the type of action
   * @param value the value of the action
   * @return the action that is created
   */
  public static Action createAction(ActionEnum type, String value) {
    return switch (type) {
      case GOLD -> new GoldAction(Integer.parseInt(value));
      case HEALTH -> new HealthAction(Integer.parseInt(value));
      case SCORE -> new ScoreAction(Integer.parseInt(value));
      case INVENTORY -> new InventoryAction(value);
      default -> throw new NullPointerException("Invalid action type: " + type);
    };
  }
}
