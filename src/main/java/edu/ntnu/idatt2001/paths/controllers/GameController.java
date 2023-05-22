package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;

/**
 * The type Game controller.
 * This class is responsible for the game.
 * Singleton class for the game
 * Can be accessed from anywhere in the program.
 *
 * @author Helle R. and Sander S.
 * @version 1.1 22.05.2023
 */
public class GameController {
  /*
   * The constant instance of the class.
   * This is a singleton class, and can be accessed from anywhere in the program.
   */
  private static GameController instance;
  /**
   * The Game that is being played.
   */
  private Game game;

  /**
   * Instantiates a new Game controller.
   */
  private GameController() {
  }

  /**
   * Returns the instance of the class.
   *
   * @return the instance of the class
   */
  public static GameController getInstance() {
    if (instance == null) {
      instance = new GameController();
    }
    return instance;
  }

  /**
   * Returns the game that is being played.
   *
   * @return the game that is being played
   */
  public Game getGame() {
    return game;
  }

  /**
   * Sets the game that is being played.
   *
   * @param game the game that is being played
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * Resets the game that is being played.
   */
  public void resetGame() {
    this.game = null;
  }
}
