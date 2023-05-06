package edu.ntnu.idatt2001.paths.models.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Builder class for Player.
 * Name is required and can not be null or blank.
 * Health, score and gold is optional - default health is set to 100, score and gold to 0.
 * A player can have zero health, but can not start with zero or less than zero health.
 */
public class PlayerBuilder {

  // Required parameters
  final String name;

  // Optional parameters - default health is set to 100, score and gold to 0 and inventory to an empty list
  int health = 100;
  int score = 0;
  int gold = 0;
  List<String> inventory = new ArrayList<>();

  /**
   * Constructor for Builder.
   * Name is required and can not be null or blank
   * @param name The name of the player.
   * @throws NullPointerException if name is null.
   * @throws IllegalArgumentException if the name is empty
   */
  public PlayerBuilder(String name) {
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
  public PlayerBuilder health(int health) {
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
  public PlayerBuilder score(int score) {
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
  public PlayerBuilder gold(int gold) {
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
  public PlayerBuilder inventory(List<String> inventory) {
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

