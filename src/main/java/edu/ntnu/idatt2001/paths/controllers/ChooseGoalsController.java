package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
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
public class ChooseGoalsController {
  /**
   * The constant instance of the class.
   * This is a singleton class, and can be accessed from anywhere in the program.
   */
  private static ChooseGoalsController instance;

  /**
   * The file type.
   */
  private String fileType;

  /**
   * Instantiates a new Load game controller.
   */
  private ChooseGoalsController() {
  }

  /**
   * Returns the instance of the class.
   *
   * @return the instance of the class
   */
  public static ChooseGoalsController getInstance() {
    if (instance == null) {
      instance = new ChooseGoalsController();
    }
    return instance;
  }

  public Set<String> fetchTemplates(String fileType) {
    this.fileType = fileType;
    ObservableList<FileEntry> filesList = FXCollections.observableArrayList();
    Set<String> fileNames = new HashSet<>();

    // Load files from file system
    try {
      Path resourcesPath = Paths.get("src/main/resources/templates/");
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
    return fileNames;
  }
}
