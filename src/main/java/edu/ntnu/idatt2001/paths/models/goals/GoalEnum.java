package edu.ntnu.idatt2001.paths.models.goals;

/**
 * The enum Goal enum.
 * The enum Goal enum is used to determine what type of goal the player has to reach.
 * The enum is used in the Goal factory class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public enum GoalEnum {
  /**
   * Gold goal enum.
   * The player has to reach a certain amount of gold.
   */
  GOLD,
  /**
   * Health goal enum.
   * The player has to reach a certain amount of health.
   */
  HEALTH,
  /**
   * Score goal enum.
   * The player has to reach a certain amount of score.
   */
  SCORE,
  /**
   * Inventory goal enum.
   * The player has to reach certain items in the inventory.
   */
  INVENTORY;
}
