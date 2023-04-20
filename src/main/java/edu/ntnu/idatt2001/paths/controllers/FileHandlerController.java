package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.utility.FileHandler;

import java.io.FileNotFoundException;

public class FileHandlerController {
  private static FileHandlerController instance;
  String path = "src/main/resources/paths/";
  private FileHandlerController() {
  }
  public static FileHandlerController getInstance() {
    if (instance == null) {
      instance = new FileHandlerController();
    }
    return instance;
  }

  public void saveGame(String name, Game game) {
    FileHandler.saveGame(game, path + name + ".paths");
  }

  public Game loadGame(String name) throws FileNotFoundException {
    return FileHandler.loadGame(path + name);
  }
}
