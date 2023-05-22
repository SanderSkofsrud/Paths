package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;


/**
 * The type Load game controller.
 * This class is responsible for loading games.
 * It is used to show the user a list of saved games, and to load the selected game.
 *
 * @author Helle R. and Sander S.
 * @version 1.3 20.05.2023
 */
public class LoadGameController {
  /**
   * The Game.
   * This is the game that is being loaded.
   */
  private Game game;
  /**
   * The file handler controller.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The language controller.
   */
  LanguageController languageController = LanguageController.getInstance();
  /**
   * The game controller.
   */
  GameController gameController = GameController.getInstance();
  /**
   * The player controller.
   */
  PlayerController playerController = PlayerController.getInstance();

  /**
   * Instantiates a new Load game controller.
   */
  public LoadGameController() {
  }

  /**
   * Returns a list of saved games.
   * It takes in a file type, and returns a list of all the files of that type.
   *
   * @param fileType the file type
   * @return the saved games as a list of File
   */
  public ObservableList<File> getSavedGames(String fileType) {
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
      throw new RuntimeException("Failed to load files from file system", e);
    }

    filesList.addAll(fileSet);
    return filesList;
  }

  /**
   * Helper method to get the broken links as a concatenated string.
   * It takes in a file, and returns a string of all the broken links in that file.
   *
   * @param file the file to get the broken links from
   * @return the broken links as a string
   */
  public String getBrokenLinksString(File file) {
    String fileName = file.getName();
    Game game;
    try {
      game = fileHandlerController.loadGameJson(fileName, null);
    } catch (FileNotFoundException | IllegalArgumentException e) {
      throw new RuntimeException("Failed to load game from file", e);
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

  /**
   * Helper method to create a table view.
   * It takes in a screen controller, and returns a table view of all the saved games.
   *
   * @param screenController the screen controller
   * @return the table view of saved games
   */
  public TableView<File> createTableView(ScreenController screenController) {
    try {
      TableView<File> jsonTableView = new TableView<>();
      TableColumn<File, String> fileNameColumn = new TableColumn<>(languageController
          .getTranslation(Dictionary.FILE_NAME.getKey()));
      fileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData
          .getValue().getName()));

      TableColumn<File, String> fileLocationColumn = new TableColumn<>(languageController
          .getTranslation(Dictionary.FILE_LOCATION.getKey()));
      fileLocationColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData
          .getValue().getAbsolutePath()));
      fileLocationColumn.setCellFactory(tc -> new TableCell<>() {
        private final Text text = new Text();
        private final VBox vbox = new VBox(text);
        private final Pane container = new Pane(vbox);
        private final TranslateTransition tt = new TranslateTransition(Duration.seconds(6.5), text);
        private final PauseTransition ptBefore = new PauseTransition(Duration.seconds(1));
        private final PauseTransition ptAfter = new PauseTransition(Duration.seconds(1));
        private final SequentialTransition st = new SequentialTransition(ptBefore, tt, ptAfter);

        {
          st.setCycleCount(Timeline.INDEFINITE);

          text.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            container.setMinWidth(newValue.getWidth());
            tt.setFromX(container.getWidth());
            tt.setToX(-newValue.getWidth() + 365);
          });
          container.setPrefWidth(100);
          vbox.setPrefWidth(100);
          vbox.setFillWidth(true);
          vbox.setAlignment(Pos.CENTER);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
            setGraphic(null);
            text.setText(null);
            st.stop();
          } else {
            text.setText(item);
            setGraphic(container);
            st.play();
          }
        }

        @Override
        public void startEdit() {
          super.startEdit();
          st.pause();
        }

        @Override
        public void cancelEdit() {
          super.cancelEdit();
          st.play();
        }
      });


      fileLocationColumn.setPrefWidth(350);

      TableColumn<File, String> brokenLinksColumn = new TableColumn<>(languageController
          .getTranslation(Dictionary.BROKEN_LINKS.getKey()));
      brokenLinksColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
          getBrokenLinksString(param.getValue())));

      TableColumn<File, String> loadColumn = new TableColumn<>(languageController
          .getTranslation(Dictionary.LOAD_GAME.getKey()));
      loadColumn.setPrefWidth(80);
      loadColumn.setCellFactory(tc -> new TableCell<>() {
        private final Hyperlink hyperlink = new Hyperlink(languageController
            .getTranslation(Dictionary.LOAD_GAME.getKey()));

        {
          hyperlink.setOnAction(event -> {
            String selectedFile = getTableView().getItems().get(getIndex()).getName();
            try {
              game = fileHandlerController.loadGameJson(selectedFile, null);
              gameController.setGame(game);
              playerController.setActiveCharacter(game.getPlayer().getCharacterModel());
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e.getMessage());
            }
            SoundPlayer.play("/sounds/confirm.wav");
            screenController.activate("MainGame");
          });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          if (empty) {
            setGraphic(null);
          } else {
            setGraphic(hyperlink);
          }
        }
      });

      jsonTableView.getColumns().addAll(fileNameColumn, fileLocationColumn,
          brokenLinksColumn, loadColumn);
      try {
        jsonTableView.setItems(getSavedGames("json"));
      } catch (RuntimeException e) {
        throw new RuntimeException(e.getMessage());
      }
      jsonTableView.setPrefWidth(720);
      return jsonTableView;
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
