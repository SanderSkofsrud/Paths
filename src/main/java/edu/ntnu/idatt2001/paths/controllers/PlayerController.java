package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {
  private static PlayerController instance;
  Player player;
  List<String> inventory = new ArrayList<>();
  PlayerController() {
  }

  public static PlayerController getInstance() {
    if (instance == null) {
      instance = new PlayerController();
    }
    return instance;
  }

  public Player addDefaultPlayer(String name, String difficulty) {
    System.out.println(difficulty.trim().toLowerCase());
    switch (difficulty.trim().toLowerCase()) {
      case "easy" -> {
        inventory.add("Sword");
        player = new Player.Builder(name).health(250).gold(250).inventory(inventory).build();
      }
      case "hard" -> player = new Player.Builder(name).health(50).gold(0).build();
      default -> player = new Player.Builder(name).health(100).gold(100).build();
    }
    return player;
  }

  public Player addCusomPlayer(String name, int health, int gold) {
    player = new Player.Builder(name).health(health).gold(gold).inventory(inventory).build();
    return player;
  }

  public Player getPlayer() {
    return player;
  }
}
