package edu.ntnu.idatt2001.paths.models;

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

  private final String name;
  private int health;
  private int score;
  private int gold;
  private final List<String> inventory;

  /**
   * Constructor for Player.
   *
   *
   *
   */

  private Player(Builder builder) {
    name = builder.name;
    health = builder.health;
    score = builder.score;
    gold = builder.gold;
    inventory = builder.inventory;
  }

  /**
   * Builder class for Player.
   * Name is required and can not be null or blank.
   * Health, score and gold is optional - default health is set to 100, score and gold to 0.
   * A player can have zero health, but can not start with zero or less than zero health.
   */
  public static class Builder {

    //Required parameters
    private final String name;

    //Optional parameters - default health is set to 100, score and gold to 0 and inventory to an empty list
    private int health = 100;
    private int score = 0;
    private int gold = 0;
    private List<String> inventory = new ArrayList<>();

    /**
     * Constructor for Builder.
     * Name is required and can not be null or blank
     * @param name The name of the player.
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if the name is empty
     */
    public Builder(String name) {
      Objects.requireNonNull(name, "The name can´t be null");
      if (name.isBlank()) {
        throw new IllegalArgumentException("Name can not be empty");
      }
      this.name = name;
    }

    /**
     * Sets the health of the player.
     * Health can not be zero or less than zero.
     * @param health The health of the player.
     * @return The player´s health.
     * @throws IllegalArgumentException if health is zero or less than zero.
     */
    public Builder health(int health) {
      if (health <= 0) {
        throw new IllegalArgumentException("Health can not be less than 0");
      }
      this.health = health;
      return this;
    }

    /**
     * Sets the score of the player.
     * Score can not be less than zero.
     * @param score The score of the player.
     * @return The player´s score.
     * @throws IllegalArgumentException if score is less than zero.
     */
    public Builder score(int score) {
      if (score < 0) {
        throw new IllegalArgumentException("Score can not be less than 0");
      }
      this.score = score;
      return this;
    }

    /**
     * Sets the gold of the player.
     * Gold can not be less than zero.
     * @param gold The gold of the player.
     * @return The player´s gold.
     * @throws IllegalArgumentException if gold is less than zero.
     */
    public Builder gold(int gold) {
        if (gold < 0) {
          throw new IllegalArgumentException("Score can not be less than 0");
        }
      this.gold = gold;
      return this;
    }

    /**
     * Sets the inventory of the player.
     * Loops through inventory and trims the strings to lowercase.
     * @param inventory The inventory of the player.
     * @return The player´s inventory.
     */
    public Builder inventory(List<String> inventory) {
      (this.inventory = new ArrayList<>()).addAll
          (inventory.stream().map(String::trim).map(String::toLowerCase).toList());
      return this;
    }

    /**
     * Builds the player.
     * @return The player.
     */
    public Player build() {
      return new Player(this);
    }
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
