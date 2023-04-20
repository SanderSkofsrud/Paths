package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.utility.FileHandler;

import java.io.FileNotFoundException;

public class FileHandlerController {
  static Player player;
  static String path = "src/main/resources/paths/";

  public static void saveGame(String name, Game game) {
    FileHandler.saveGame(game, path + name + ".paths");
  }

  public static Game loadGame(String name) throws FileNotFoundException {
    return FileHandler.loadGame(path + name);
  }
}
