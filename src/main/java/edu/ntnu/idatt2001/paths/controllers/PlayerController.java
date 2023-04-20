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

  public Player addDefaultPlayer(String name, String dificulty) {
    System.out.println(dificulty.trim().toLowerCase());
    switch (dificulty.trim().toLowerCase()) {
      case "easy" -> {
        player = new Player.Builder(name).health(250).gold(250).inventory(inventory).build();
        inventory.add("Sword");
      }
      case "medium" -> player = new Player.Builder(name).build();
      default -> player = new Player.Builder(name).health(25).gold(0).build();
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
