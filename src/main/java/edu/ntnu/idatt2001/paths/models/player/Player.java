package edu.ntnu.idatt2001.paths.models.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a player in a game. A player has different attributes that can be affected by a story
 * A player has a name, health, score, gold and an inventory and represent who is playing the game.
 * When a player has 0 or less health, the player is dead and the game is over.
 *
 * @author Helle R. & Sander S.
 * @version 0.2 - 11.04.2023
 */

public class Player {
  /**
   * The name of the player.
   */
  private final String name;
  /**
   * The health of the player.
   */
  private int health;
  /**
   * The score of the player.
   */
  private int score;
  /**
   * The gold of the player.
   */
  private int gold;
  /**
   * The inventory of the player.
   */
  private final List<String> inventory;

  /**
   * The constructor of the Player class.
   * The constructor is used to set the name, health, score, gold and inventory of the player.
   * The constructor is private and can only be accessed by the PlayerBuilder class.
   *
   * @param builder The PlayerBuilder object that is used to set the attributes of the player.
   */

  Player(PlayerBuilder builder) {
    name = builder.name;
    health = builder.health;
    score = builder.score;
    gold = builder.gold;
    inventory = builder.inventory;
  }

  /**
   * Returns the name of the player.
   *
   * @return The name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a positive or negative number to the health of the player.
   * If the health is less than 0, the health is set to 0.
   *
   * @param health Number to add to health of the player - can be positive or negative.
   */

  public void addHealth(int health) {
    this.health += health;
    if (this.health + health < 0) {
      this.health = 0;
    }
  }

  /**
   * Checks if the player has health above 0.
   *
   * @return True if the player is alive, false if the player is dead.
   */
  public boolean isAlive() {
    return health > 0;
  }

  /**
   * Returns the health of the player.
   *
   * @return The health of the player.
   */

  public int getHealth() {
    return health;
  }

  /**
   * Sets the score of the player.
   *
   * @param points The score of the player.
   * @throws IllegalArgumentException if score is less than 0.
   */

  public void addScore(int points) throws IllegalArgumentException{
    if (this.score + points < 0) {
      throw new IllegalArgumentException("Player points can not be less than 0");
    }
    this.score += points;
  }

  /**
   * Returns the score of the player.
   *
   * @return The score of the player.
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets the gold of the player.
   *
   * @param gold The gold of the player.
   * @throws IllegalArgumentException if gold is less than 0.
   */
  public void addGold(int gold) throws IllegalArgumentException{
    if (this.gold + gold < 0) {
      throw new IllegalArgumentException("Player gold can not be less than 0");
    }
    this.gold += gold;
  }

  /**
   * Returns the gold of the player.
   *
   * @return The gold of the player.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Adds an item to the inventory of the player.
   * Trims to lower case when adding to inventory, as the inventory is stored in lower case.
   *
   * @param item The item to be added to the inventory.
   * @throws NullPointerException if item is null.
   * @throws IllegalArgumentException if item is blank.
   */
  public void addToInventory(String item) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(item, "Item can not be null");
    if (item.isBlank()) {
      throw new IllegalArgumentException("Item can not be empty");
    }
    inventory.add(item.trim().toLowerCase());
  }

  /**
   * Returns the inventory of the player.
   *
   * @return The inventory of the player.
   */
  public List<String> getInventory() {
    return inventory;
  }

  /**
   * Returns a string representation of the player.
   * The string representation is formatted as follows:
   * ;;name;health;score;gold;[inventory]
   *
   * @return A string representation of the player.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int count = inventory.size();
    for (int i = 0; i < count; i++) {
      sb.append(inventory.get(i));
      if (i < count - 1) {
        sb.append(", ");
      }
    }
    return ";;"
        + getName()
        + ";"
        + getHealth()
        + ";"
        + getScore()
        + ";"
        + getGold()
        + ";["
        + sb
        + "]";
  }
}
