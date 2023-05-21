package edu.ntnu.idatt2001.paths.models.actions;

/**
 * The enum Action enum.
 * The enum Action enum is used to determine what type of action will be executed on the player.
 * The enum is used in the Action factory class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public enum ActionEnum {

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