package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.utility.FileEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LoadGameController {
  private static LoadGameController instance;
  private LoadGameController() {
  }
  public static LoadGameController getInstance() {
    if (instance == null) {
      instance = new LoadGameController();
    }
    return instance;
  }
  public ObservableList<FileEntry> getSavedGames(String fileType) {
    ObservableList<FileEntry> filesList = FXCollections.observableArrayList();
    URL resource = LoadGameController.class.getResource("/" + fileType);
    try {
      if (resource != null) {
        Path path = Paths.get(resource.toURI());
        try (Stream<Path> stream = Files.list(path)) {
          stream.forEach(file -> {
            if (Files.isRegularFile(file) && file.toString().endsWith(fileType)) {
              filesList.add(new FileEntry(file.getFileName().toString()));
            }
          });
        }
      }
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    return filesList;
  }


}
