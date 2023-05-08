package edu.ntnu.idatt2001.paths.models.player;

import java.util.ArrayList;
import java.util.List;

/**
 * The enum Difficulty.
 * The enum Difficulty is used to determine what difficulty the player wants to play on.
 * The enum determines the player's health, gold and starting inventory.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public enum Difficulty {
  /**
   * The Easy difficulty.
   */
  EASY(250, 250, new ArrayList<>(List.of(Item.SWORD.toString(), Item.SHIELD.toString()))),
  /**
   * The Standard difficulty.
   */
  STANDARD(100, 100, new ArrayList<>(List.of(Item.SWORD.toString()))),
  /**
   * The Hard difficulty.
   */
  HARD(50, 0, new ArrayList<>());

  /*
   * The health of the player.
   */
  private final int health;
  /*
   * The gold of the player.
   */
  private final int gold;
  /*
   * The starting inventory of the player.
   */
  private final List<String> startingInventory;

  /**
   * The constructor of the Difficulty class.
   * The constructor is used to set the health, gold and starting inventory of the player.
   *
   * @param health            the health of the player
   * @param gold              the gold of the player
   * @param startingInventory the starting inventory of the player
   */
  Difficulty(int health, int gold, List<String> startingInventory) {
    this.health = health;
    this.gold = gold;
    this.startingInventory = startingInventory;
  }

  /**
   * Gets health of the player.
   *
   * @return the health of the player
   */
  public int getHealth() {
    return health;
  }

  /**
   * Gets gold of the player.
   *
   * @return the gold of the player
   */
  public int getGold() {
    return gold;
  }

  /**
   * Gets starting inventory of the player.
   *
   * @return the starting inventory of the player
   */
  public List<String> getStartingInventory() {
    return startingInventory;
  }
}