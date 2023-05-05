package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;

public class GameController {
  private static GameController instance;
  private Game game;

  private GameController() {
  }

  public static GameController getInstance() {
    if (instance == null) {
      instance = new GameController();
    }
    return instance;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public void resetGame() {
    this.game = null;
  }
}
