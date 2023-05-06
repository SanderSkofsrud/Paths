package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.player.Difficulty;
import edu.ntnu.idatt2001.paths.models.player.Item;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.player.PlayerBuilder;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {
  private static PlayerController instance;
  private Player player;
  private List<String> inventory = new ArrayList<>();

  private PlayerController() {}

  public static PlayerController getInstance() {
    if (instance == null) {
      instance = new PlayerController();
    }
    return instance;
  }

  public Player addDefaultPlayer(String name, Difficulty difficulty) {
    inventory.addAll(difficulty.getStartingInventory());
    player = new PlayerBuilder(name)
            .health(difficulty.getHealth())
            .gold(difficulty.getGold())
            .inventory(inventory)
            .build();
    return player;
  }

  public Player addCustomPlayer(String name, int health, int gold) {
    player = new PlayerBuilder(name)
            .health(health)
            .gold(gold)
            .inventory(inventory)
            .build();
    return player;
  }

  public Player getPlayer() {
    return player;
  }
}
