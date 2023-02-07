package edu.ntnu.idatt2001;

import java.util.List;

public class Player {

  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;

  public Player(String name, int health, int score, int gold) {

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

    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
  }
}
