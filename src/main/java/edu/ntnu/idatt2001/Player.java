package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a player in a game.
 */

public class Player {

  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;

  /**
   * Constructor for Player.
   *
   * @param name The name of the player.
   * @param health The health of the player.
   * @param score The score of the player.
   * @param gold The gold of the player.
   * @throws NullPointerException if name is null.
   * @throws IllegalArgumentException if health, score or gold is less than 0.
   */

  public Player(String name, int health, int score, int gold)
      throws IllegalArgumentException, NullPointerException {

    if (name == null || name.isBlank()) {
      throw new NullPointerException("Name can not be empty");
    }
    if (health < 0) {
      throw new IllegalArgumentException("Health can not be less than 0");
    }
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name must not be empty");
    }
    if (score < 0) {
      throw new IllegalArgumentException("Score can not be less than 0");
    }
    if (gold < 0) {
      throw new IllegalArgumentException("Score can not be less than 0");
    }
    Objects.requireNonNull(name, "The name can´t be null");

    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = new ArrayList<String>();
  }

  /**
   * Copy constructor for Player.
   *
   * @param player The player to be copied.
   * @throws NullPointerException if player is null.
   */
  public Player(Player player) {
    this.name = player.name;
    this.health = player.health;
    this.score = player.score;
    this.gold = player.gold;
    this.inventory = player.inventory;
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
   * Sets the health of the player.
   *
   * @param health The health of the player.
   * @throws IllegalArgumentException if health is less than 0.
   */

  public void addHealth(int health) throws IllegalArgumentException {
    if (this.health + health < 0) {
      throw new IllegalArgumentException("Player health can not be less than 0");
    }
    this.health += health;
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

  public void addScore(int points) {
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
  public void addGold(int gold) {
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
   *
   * @param item The item to be added to the inventory.
   * @throws NullPointerException if item is null.
   */
  public void addToInventory(String item) {
    if (item == null || item.isBlank()) {
      throw new NullPointerException("Item can not be null");
    }
    inventory.add(item);
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
   *
   * @return A string representation of the player.
   */
  @Override
  public String toString() {
    return "Player{"
        + "name='"
        + getName()
        + '\''
        + ", health="
        + getHealth()
        + ", score="
        + getScore()
        + ", gold="
        + getGold()
        + ", inventory="
        + getInventory()
        + '}';
  }
}
