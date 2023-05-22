package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.json.JsonReader;
import edu.ntnu.idatt2001.paths.utility.json.JsonWriter;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import java.io.FileNotFoundException;

/**
 * The type File handler controller.
 * This class is responsible for handling the saving and loading of games.
 * It is a singleton class, and can be accessed from anywhere in the program.
 * It can save and load games, both from json and from paths.
 *
 * @author Helle R. and Sander S.
 * @version 1.3 20.05.2023
 */
public class FileHandlerController {
  /**
   * The constant instance of the class.
   * This is a singleton class, and can be accessed from anywhere in the program.
   */
  private static FileHandlerController instance;
  /**
   * The Path to the templates' folder.
   */
  String templatesPath = "src/main/resources/templates/";
  /**
   * The Json path to the json folder.
   */
  String jsonPath = "src/main/resources/json/";

  /**
   * Instantiates a new File handler controller.
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

  /**
   * A method to load a template from a file.
   * It takes in a name, and loads the template from a file with the name.
   *
   * @param name the name of the file to be loaded
   * @return the template from the file
   * @throws FileNotFoundException the file not found exception
   */
  public Story loadTemplate(String name) throws FileNotFoundException {
    return PathsReader.loadStory(templatesPath + name);
  }

  /**
   * A method to save a game to a json file.
   * It takes in a name and a game, and saves the game to a file with the name.
   *
   * @param name the name of the player, and the name of the file
   * @param game the game to be saved
   */
  public void saveGameJson(String name, String path, Game game) throws IllegalArgumentException {
    if (path == null) {
      path = jsonPath;
    }
    JsonWriter.saveGameJSON(game, path + name + ".json");
  }

  /**
   * A method to load a game from a json file.
   * It takes in a name, and loads the game from a file with the name.
   *
   * @param name the name of the file to be loaded
   * @return the game from the json file
   * @throws FileNotFoundException the file not found exception
   */
  public Game loadGameJson(String name, String path) throws FileNotFoundException {
    if (path == null) {
      path = jsonPath;
    }
    return JsonReader.loadGameJSON(path + name);
  }
}
