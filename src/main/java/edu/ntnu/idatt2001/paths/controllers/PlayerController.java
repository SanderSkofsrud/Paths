package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {
  static Player player;
  static List<String> inventory = new ArrayList<>();

  public static Player addDefaultPlayer(String dificulty) {
    switch (dificulty) {
      case "easy" -> {
        player = new Player.Builder(dificulty).health(250).gold(250).inventory(inventory).build();
        inventory.add("Sword");
      }
      case "medium" -> player = new Player.Builder(dificulty).build();
      default -> player = new Player.Builder(dificulty).health(25).gold(0).build();
    }
    return player;
  }

  public static Player addCusomPlayer(String name, int health, int gold) {
    player = new Player.Builder(name).health(health).gold(gold).inventory(inventory).build();
    return player;
  }
}
