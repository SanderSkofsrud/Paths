package edu.ntnu.idatt2001.paths.backend;

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
   * Name can not be null or blank, score and gold can not be negative and health can not be zero og negative.
   * A player can have zero health, but can not start with zero or less than zero health.
   * Inventory
   *
   * @param name The name of the player.
   * @param health The health of the player.
   * @param score The score of the player.
   * @param gold The gold of the player.
   * @throws NullPointerException if name is null.
   * @throws IllegalArgumentException if name is blank, health is zero or less, score or gold is negative
   */

  public Player(String name, int health, int score, int gold, List<String> inventory)
      throws IllegalArgumentException, NullPointerException {
    Objects.requireNonNull(name, "The name can´t be null");
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name can not be empty");
    }
    if (health <= 0) {
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

    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
    (this.inventory = new ArrayList<>()).addAll
        (inventory.stream().map(String::trim).map(String::toLowerCase).toList());
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

  public static class Builder {

    //Required parameters
    private final String name;

    //Optional parameters - default health is set to 100, score and gold to 0 and inventory to an empty list
    private int health = 100;
    private int score = 0;
    private int gold = 0;
    private List<String> inventory = new ArrayList<>();

    public Builder(String name) {
      this.name = name;
    }

    public Builder health(int health) {
      this.health = health;
      return this;
    }

    public Builder score(int score) {
      this.score = score;
      return this;
    }

    public Builder gold(int gold) {
      this.gold = gold;
      return this;
    }

    public Builder inventory(List<String> inventory) {
      this.inventory = inventory;
      return this;
    }

    public Player build() {
      return new Player(name, health, score, gold, inventory);
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
