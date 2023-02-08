package edu.ntnu.idatt2001;

import java.util.ArrayList;
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
    this.inventory = new ArrayList<String>();
  }

  public Player(Player player) {
    this.name = player.name;
    this.health = player.health;
    this.score = player.score;
    this.gold = player.gold;
    this.inventory = player.inventory;
  }

  public String getName() {
    return name;
  }

  public void addHealth(int health){}

  public int getHealth() {
    return health;
  }

  public void addScore(int points){}

  public int getScore() {
    return score;
  }

  public void addGold(int gold){}
  public int getGold() {
    return gold;
  }

  public void addToInventory(String item){}

  public List<String> getInventory() {
    return inventory;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + getName() + '\'' +
        ", health=" + getHealth() +
        ", score=" + getScore() +
        ", gold=" + getGold() +
        ", inventory=" + getInventory() +
        '}';
  }
}
