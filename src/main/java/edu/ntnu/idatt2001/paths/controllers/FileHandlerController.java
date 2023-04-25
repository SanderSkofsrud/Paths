package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.json.JsonReader;
import edu.ntnu.idatt2001.paths.utility.json.JsonWriter;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import edu.ntnu.idatt2001.paths.utility.paths.PathsWriter;

import java.io.FileNotFoundException;

public class FileHandlerController {
  private static FileHandlerController instance;
  String path = "src/main/resources/paths/";
  String jsonPath = "src/main/resources/json/";
  private FileHandlerController() {
  }
  public static FileHandlerController getInstance() {
    if (instance == null) {
      instance = new FileHandlerController();
    }
    return instance;
  }

  public void saveGame(String name, Story story) {
    PathsWriter.saveGame(story, path + name + ".paths");
  }

  public Game loadGame(String name) throws FileNotFoundException {
    return PathsReader.loadGame(path + name);
  }

  public void saveGameJson(String name, Game game) {
    JsonWriter.saveGameJSON(game, jsonPath + name + ".json");
  }

public Game loadGameJson(String name) throws FileNotFoundException {
    return JsonReader.loadGameJSON(jsonPath + name);
  }
}
