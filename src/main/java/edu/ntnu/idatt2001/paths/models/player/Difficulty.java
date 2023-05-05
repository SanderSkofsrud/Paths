package edu.ntnu.idatt2001.paths.models.player;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
  EASY(250, 250, new ArrayList<>(List.of(Item.SWORD.toString(), Item.SHIELD.toString()))),
  STANDARD(100, 100, new ArrayList<>()),
  HARD(50, 0, new ArrayList<>());

  private final int health;
  private final int gold;
  private final List<String> startingInventory;

  Difficulty(int health, int gold, List<String> startingInventory) {
    this.health = health;
    this.gold = gold;
    this.startingInventory = startingInventory;
  }

  public int getHealth() {
    return health;
  }

  public int getGold() {
    return gold;
  }

  public List<String> getStartingInventory() {
    return startingInventory;
  }
}