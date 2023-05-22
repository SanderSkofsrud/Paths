package edu.ntnu.idatt2001.paths.models.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a player in a game. A player has different attributes
 * that can be affected by a story
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
   * The character model of the player.
   * Used to save the chosen character model of the player
   */
  private final String characterModel;

  /**
   * The constructor of the Player class.
   * The constructor is used to set the name, health, score, gold and inventory of the player.
   * The constructor is private and can only be accessed by the PlayerBuilder class.
   *
   * @param builder The PlayerBuilder object that is used to set the attributes of the player.
   */

  private Player(PlayerBuilder builder) {
    this.name = builder.name;
    this.health = builder.health;
    this.score = builder.score;
    this.gold = builder.gold;
    this.inventory = builder.inventory;
    this.characterModel = builder.characterModel;
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
   * @throws IllegalArgumentException if health is less than 0.
   */

  public void addHealth(int health) throws IllegalArgumentException {
    if (this.health + health <= 0) {
      throw new IllegalArgumentException("Health cannot be less than 0");
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

  public void addScore(int points) throws IllegalArgumentException {
    if (this.score + points < 0) {
      throw new IllegalArgumentException("Score cannot be less than 0");
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
  public void addGold(int gold) throws IllegalArgumentException {
    if (this.gold + gold < 0) {
      throw new IllegalArgumentException("Gold cannot be less than 0");
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
   * Returns the character model of the player.
   *
   * @return The character model of the player.
   */
  public String getCharacterModel() {
    return characterModel;
  }

  /**
   * Static inner class for building Player objects.
   */
  public static class PlayerBuilder {
    private final String name;
    private int health = 100;
    private int score = 0;
    private int gold = 0;
    private List<String> inventory = new ArrayList<>();
    private String characterModel = "m.png";

    /**
     * Constructor for PlayerBuilder.
     * Name is required to build a player, but health, score, gold and inventory are optional.
     *
     * @param name The name of the player.
     * @throws NullPointerException     if name is null.
     * @throws IllegalArgumentException if the name is empty.
     */
    public PlayerBuilder(String name) {
      Objects.requireNonNull(name, "The name can't be null");
      if (name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be empty");
      }
      this.name = name;
    }

    // Setter methods for configuring the builder

    /**
     * Sets the health of the player.
     *
     * @param health The health of the player.
     * @return The PlayerBuilder object.
     */
    public PlayerBuilder health(int health) {
      if (health <= 0) {
        throw new IllegalArgumentException("Health cannot be less than 0");
      }
      this.health = health;
      return this;
    }

    /**
     * Sets the score of the player.
     * Throws an IllegalArgumentException if score is less than 0.
     *
     * @param score The score of the player.
     * @return The PlayerBuilder object.
     * @throws IllegalArgumentException if score is less than 0.
     */
    public PlayerBuilder score(int score) throws IllegalArgumentException {
      if (score < 0) {
        throw new IllegalArgumentException("Score cannot be less than 0");
      }
      this.score = score;
      return this;
    }

    /**
     * Sets the gold of the player.
     * Throws an IllegalArgumentException if gold is less than 0.
     *
     * @param gold The gold of the player
     * @return The PlayerBuilder object.
     * @throws IllegalArgumentException if gold is less than 0.
     */
    public PlayerBuilder gold(int gold) throws IllegalArgumentException {
      if (gold < 0) {
        throw new IllegalArgumentException("Gold cannot be less than 0");
      }
      this.gold = gold;
      return this;
    }

    /**
     * Sets the inventory of the player.
     *
     * @param inventory The inventory of the player.
     * @return The PlayerBuilder object.
     */
    public PlayerBuilder inventory(List<String> inventory) {
      this.inventory = new ArrayList<>(inventory);
      return this;
    }

    /**
     * Sets the character model of the player.
     *
     * @param characterModel The character model of the player.
     * @return The PlayerBuilder object.
     */
    public PlayerBuilder characterModel(String characterModel) {
      this.characterModel = characterModel;
      return this;
    }

    /**
     * Builds the Player object.
     *
     * @return The constructed Player object.
     */
    public Player build() {
      return new Player(this);
    }
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
    return "Name: "
        + getName()
        + "\nHealth: "
        + getHealth()
        + "\nScore: "
        + getScore()
        + "\nGold: "
        + getGold()
        + "\nInventory: "
        + sb;
  }
}
