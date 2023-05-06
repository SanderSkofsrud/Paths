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

public class LoadGameController {
  private static LoadGameController instance;
  private String fileType;
  private StringBuilder missingData;
  private LoadGameController() {
  }
  public static LoadGameController getInstance() {
    if (instance == null) {
      instance = new LoadGameController();
    }
    return instance;
  }
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

  public String handleGameData(GameData gameData, GameController gameController, ScreenController screenController) {
    Story story = gameData.getStory();
    Player player = gameData.getPlayer();
    List<Goal> goals = gameData.getGoals();
    StringBuilder missingDataBuilder = null;

    if (story != null && player != null && !goals.isEmpty()) {
      Game game = new Game(player, story, goals);
      gameController.setGame(game);
      screenController.activate("MainGame");
    } else {
      missingDataBuilder = new StringBuilder("Missing data:");
      if (player == null) {
        missingDataBuilder.append(" Player");
      }
      if (goals.isEmpty()) {
        missingDataBuilder.append(" Goals");
      }
    }

    return missingDataBuilder != null ? missingDataBuilder.toString() : "";
  }

  public String getFileType() {
    return fileType;
  }

}
