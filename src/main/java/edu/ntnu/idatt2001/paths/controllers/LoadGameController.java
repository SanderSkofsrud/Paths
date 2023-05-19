package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
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
   * @return the saved games as a list of File
   */
  public ObservableList<File> getSavedGames(String fileType) {
    this.fileType = fileType;
    ObservableList<File> filesList = FXCollections.observableArrayList();
    Set<File> fileSet = new LinkedHashSet<>();

    // Load files from file system
    try {
      Path resourcesPath = Paths.get("src/main/resources/" + fileType);
      if (Files.exists(resourcesPath)) {
        try (Stream<Path> stream = Files.list(resourcesPath)) {
          stream.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(fileType))
                  .map(Path::toFile)
                  .forEach(fileSet::add);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    filesList.addAll(fileSet);
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
    Path resourcesPath = Paths.get("src/main/resources/templates");

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
   * Helper method to get the broken links as a concatenated string.
   *
   * @param file the file
   * @return the broken links string
   */
  public String getBrokenLinksString(File file) {
    String fileName = file.getName();
    Game game = null;
    try {
      game = fileHandlerController.loadGameJson(file.getAbsolutePath());
    } catch (FileNotFoundException | IllegalArgumentException e) {
      System.out.println("Incorrect file format: " + e.getMessage());
    }
    List<Link> brokenLinks = null;
    if (game != null) {
      brokenLinks = game.getStory().getBrokenLinks();
    }

    if (brokenLinks != null && !brokenLinks.isEmpty()) {
      return brokenLinks.stream().map(Link::getReference).collect(Collectors.joining(", "));
    } else {
      return languageController.getTranslation(Dictionary.NO_BROKEN_LINKS.getKey());
    }
  }

}
