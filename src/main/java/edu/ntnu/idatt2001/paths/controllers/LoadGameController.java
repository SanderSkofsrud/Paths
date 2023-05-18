package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
import edu.ntnu.idatt2001.paths.utility.GameData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The type Load game controller.
 * This class is responsible for loading games.
 * It is a singleton class, and can be accessed from anywhere in the program.
 * It is used to show the user a list of saved games, and to load the selected game.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class LoadGameController {
  /**
   * The constant instance of the class.
   * This is a singleton class, and can be accessed from anywhere in the program.
   */
  private static LoadGameController instance;
  /**
   * The file type.
   */
  private String fileType;
  /**
   * The Missing data.
   */
  private StringBuilder missingData;
  /**
   * Instantiates a new Load game controller.
   */
  private LoadGameController() {
  }

  /**
   * Returns the instance of the class.
   *
   * @return the instance of the class
   */
  public static LoadGameController getInstance() {
    if (instance == null) {
      instance = new LoadGameController();
    }
    return instance;
  }

  /**
   * Returns a list of saved games.
   * It takes in a file type, and returns a list of all the files of that type.
   *
   * @param fileType the file type
   * @return the saved games as a list of FileEntry
   */
  public ObservableList<FileEntry> getSavedGames(String fileType) {
    this.fileType = fileType;
    ObservableList<FileEntry> filesList = FXCollections.observableArrayList();
    Set<String> fileNames = new HashSet<>();

    // Load files from file system
    try {
      Path resourcesPath = Paths.get("src/main/resources/" + fileType);
      if (Files.exists(resourcesPath)) {
        try (Stream<Path> stream = Files.list(resourcesPath)) {
          stream.forEach(file -> {
            if (Files.isRegularFile(file) && file.toString().endsWith(fileType)) {
              fileNames.add(file.getFileName().toString());
            }
          });
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Load files from classpath (when running from a JAR)
    try {
      URL resource = LoadGameController.class.getResource("/" + fileType);
      if (resource != null) {
        Path path = Paths.get(resource.toURI());
        try (Stream<Path> stream = Files.list(path)) {
          stream.forEach(file -> {
            if (Files.isRegularFile(file) && file.toString().endsWith(fileType)) {
              fileNames.add(file.getFileName().toString());
            }
          });
        }
      }
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }

    // Add unique file names to the filesList
    for (String fileName : fileNames) {
      filesList.add(new FileEntry(fileName, "src/main/resources/" + fileType + "/" + fileName));
    }

    return filesList;
  }

  /**
   * Adds a saved game to the appropriate resources' directory.
   * It takes in a file name, a file type, and a selected file.
   *
   * @param fileName     the file name of the saved game
   * @param fileType     the file type of the saved game
   * @param selectedFile the selected file to be saved
   * @throws IOException the io exception if the file is not found
   */
  public void addSavedGame(String fileName, String fileType, File selectedFile) throws IOException {
    // Get the path to the appropriate resources directory based on the fileType
    Path resourcesPath = Paths.get("src/main/resources/" + fileType);

    // Create the resources directory if it doesn't exist
    if (!Files.exists(resourcesPath)) {
      Files.createDirectories(resourcesPath);
    }

    // Copy the selected file to the appropriate resources directory
    Path sourcePath = selectedFile.toPath();
    Path destPath = resourcesPath.resolve(fileName);
    Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * Handle game data string.
   * It takes in game data, a game controller, and a screen controller.
   * It checks if the game data is valid, and returns a string with the missing data if it is not.
   *
   * @param gameData         the game data to be checked
   * @param gameController   the game controller to be set
   * @param screenController the screen controller to be set
   * @return the string with the missing data
   */
  public String handleGameData(GameData gameData, GameController gameController, ScreenController screenController) {
    Story story = gameData.getStory();
    Player player = gameData.getPlayer();
    List<Goal> goals = gameData.getGoals();
    StringBuilder missingDataBuilder = null;

    if (story != null && player != null && !goals.isEmpty()) {
      Game game = new Game(player, story, goals);
      gameController.setGame(game);
      //screenController.activate("MainGame");
    } else {
      missingDataBuilder = new StringBuilder("Missing data:");
      if (player == null) {
        missingDataBuilder.append(" Player");
      }
      if (goals.isEmpty()) {
        if (player == null) {
          missingDataBuilder.append(",");
        }
        missingDataBuilder.append(" Goals");
      }
    }

    return missingDataBuilder != null ? missingDataBuilder.toString() : "";
  }

  /**
   * Returns the file type.
   *
   * @return the file type
   */
  public String getFileType() {
    return fileType;
  }

}
