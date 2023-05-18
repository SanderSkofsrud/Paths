package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.GameData;
import edu.ntnu.idatt2001.paths.utility.json.JsonReader;
import edu.ntnu.idatt2001.paths.utility.json.JsonWriter;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import edu.ntnu.idatt2001.paths.utility.paths.PathsWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The type File handler controller.
 * This class is responsible for handling the saving and loading of games.
 * It is a singleton class, and can be accessed from anywhere in the program.
 * It can save and load games, both from json and from paths.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class FileHandlerController {
  /**
   * The constant instance of the class.
   * This is a singleton class, and can be accessed from anywhere in the program.
   */
  private static FileHandlerController instance;
  /**
   * The Path to the paths folder.
   */
  String path = "src/main/resources/paths/";
  /**
   * The Path to the templates folder.
   */
  String templatesPath = "src/main/resources/templates/";
  /**
   * The Json path to the json folder.
   */
  String jsonPath = "src/main/resources/json/";
  /**
   * The Current game data.
   */
  private GameData currentGameData;
  /**
   * The Current game.
   */
  private FileHandlerController() {
  }

  /**
   * Returns the instance of the class.
   *
   * @return the instance of the class
   */
  public static FileHandlerController getInstance() {
    if (instance == null) {
      instance = new FileHandlerController();
    }
    return instance;
  }

  public Story loadTemplate(String name) throws FileNotFoundException {
     return PathsReader.loadStory(templatesPath + name);
  }

  /**
   * A method to save a game to a paths file.
   * It takes in a name, a story, player, goals, and image, and saves them to a file with the name.
   *
   * @param story  the story to be saved
   * @param player the player to be saved
   * @param goals  the goals to be saved
   * @param image  the chosen image file name
   */
  public void saveGame(Story story, Player player, List<Goal> goals, String image) {
    String directory = path + player.getName();
    PathsWriter.saveGame(story, player, goals, directory, image);
  }

  /**
   * A method to load a game from a paths file.
   * It takes in a name, and loads the game from a file with the name.
   *
   * @param name the name of the file to be loaded
   * @return the game data
   * @throws FileNotFoundException the file not found exception
   */
  public GameData loadGame(String name) throws FileNotFoundException {
    try {
      currentGameData = PathsReader.loadGame(path + name);
    } catch (FileNotFoundException e) {
      String directory = null;
      try {
        directory = name.substring(0, name.lastIndexOf("."));
        currentGameData = PathsReader.loadGame(path + directory + "/" + name);
      } catch (FileNotFoundException ex) {
        System.out.println(path + directory + "/" + name);
        throw new FileNotFoundException(ex.getMessage());
      }
    }
    return currentGameData;
  }

  /**
   * A method to save a game to a json file.
   * It takes in a name and a game, and saves the game to a file with the name.
   *
   * @param name the name of the player, and the name of the file
   * @param game the game to be saved
   */
  public void saveGameJson(String name, Game game) {
    JsonWriter.saveGameJSON(game, jsonPath + name + ".json");
  }

  /**
   * A method to load a game from a json file.
   * It takes in a name, and loads the game from a file with the name.
   *
   * @param name the name of the file to be loaded
   * @return the game from the json file
   * @throws FileNotFoundException the file not found exception
   */
  public Game loadGameJson(String name) throws FileNotFoundException {
    return JsonReader.loadGameJSON(jsonPath + name);
  }

  /**
   * Returns the current game data.
   * This is the data of the game that is currently being played.
   *
   * @return the current game data
   */
  public GameData getCurrentGameData() {
    return currentGameData;
  }

  /**
   * Sets the current game data.
   * This is the data of the game that is currently being played.
   *
   * @param currentGameData the current game data
   */
  public void setCurrentGame(GameData currentGameData) {
    this.currentGameData = currentGameData;
  }
}
