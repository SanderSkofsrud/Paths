package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
import edu.ntnu.idatt2001.paths.utility.GameData;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Load game view.
 * The class is used to create the GUI of the load game view.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class LoadGameView extends View{
  /**
   * The Border pane is the main pane of the GUI.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   * The stackPane is used to stack the different components of the GUI on top of each other.
   */
  protected StackPane stackPane;
  /**
   * The Screen controller.
   * The screenController is used to switch between the different views of the GUI.
   */
  private ScreenController screenController;
  /**
   * The Game.
   * The game to be loaded.
   */
  private Game game;
  /**
   * The Game controller.
   * The gameController is used to control the game.
   */
  private GameController gameController = GameController.getInstance();
  /**
   * The File handler controller.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The Load game controller.
   */
  LoadGameController loadGameController = LoadGameController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
  /**
   * The Paths table view.
   */
  TableView<FileEntry> pathsTableView;
  /**
   * The Json table view.
   */
  TableView<FileEntry> jsonTableView;

  /**
   * Instantiates a new Load game view.
   *
   * @param screenController the screen controller
   */
  public LoadGameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }

  /**
   * Gets pane.
   * The method is used to get the pane of the GUI.
   *
   * @return the pane
   */
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * The method is used to create the GUI of the load game view.
   */
  @Override
  public void setup() {
    // Create the TableView for .paths files
    pathsTableView = new TableView<>();

    TableColumn<FileEntry, String> pathsFileNameColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_NAME.getKey()));
    pathsFileNameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));

    TableColumn<FileEntry, String> pathsFileLocationColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_LOCATION.getKey()));
    pathsFileLocationColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getFileLocation()));

    TableColumn<FileEntry, FileEntry> pathsBrokenLinksColumn = new TableColumn<>(languageController.getTranslation(Dictionary.BROKEN_LINKS.getKey()));
    pathsBrokenLinksColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    pathsBrokenLinksColumn.setCellFactory(column -> new TableCell<>() {
      @Override
      protected void updateItem(FileEntry item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
          setText(null);
        } else {
          String fileName = item.getFileName();
          Story story = null;
          try {
            story = fileHandlerController.loadGame(fileName).getStory();
          } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("Incorrect file format: " + e.getMessage());
          }
          List<Link> brokenLinks = null;
          if (story != null) {
            brokenLinks = story.getBrokenLinks();
          }

          if (brokenLinks != null && !brokenLinks.isEmpty()) {
            setText(brokenLinks.stream().map(Link::getReference).collect(Collectors.joining(", ")));
          } else {
            setText(languageController.getTranslation(Dictionary.NO_BROKEN_LINKS.getKey()));
          }
        }
      }
    });

    TableColumn<FileEntry, FileEntry> pathsClickableTextColumn = new TableColumn<>(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));
    pathsClickableTextColumn.setCellFactory(col -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));

      {
        hyperlink.setOnAction(event -> {
          FileEntry fileEntry = getTableView().getItems().get(getIndex());
          System.out.println("Clicked on file: " + fileEntry.getFileName());
          try {
            GameData gameData = fileHandlerController.loadGame(fileEntry.getFileName());
            String missingData = loadGameController.handleGameData(gameData, gameController, screenController);
            if (!missingData.isEmpty()) {
              showAlertWindow(missingData);
            } else {
              screenController.activate("MainGame");
              resetPane();
            }
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
        });
      }

      @Override
      protected void updateItem(FileEntry item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(hyperlink);
        }
      }
    });


    pathsTableView.getColumns().addAll(pathsFileNameColumn, pathsFileLocationColumn, pathsBrokenLinksColumn, pathsClickableTextColumn);

    // Filter files based on directory name
    String pathsDirectoryName = "paths"; // The name of the "paths" directory
    Path pathsDirectoryPath = Paths.get("src/main/resources", pathsDirectoryName);

    try {
      Stream<Path> pathStream = Files.walk(pathsDirectoryPath)
              .filter(Files::isRegularFile)
              .filter(path -> !path.getParent().equals(pathsDirectoryPath)) // Exclude files directly under the "paths" directory
              .filter(path -> path.toString().endsWith(".paths"));

      List<FileEntry> fileEntries = pathStream.map(path -> {
        String fileName = path.getFileName().toString();
        String directoryName = "src/main/resources/" + pathsDirectoryName + "/" + path.getParent().getFileName().toString() + "/" + fileName;
        return new FileEntry(fileName, directoryName);
      }).collect(Collectors.toList());

      pathsTableView.setItems(FXCollections.observableArrayList(fileEntries));
    } catch (IOException e) {
      e.printStackTrace();
    }

    pathsTableView.setPrefWidth(650);

    // Create the TableView for .json files
    jsonTableView = new TableView<>();

    TableColumn<FileEntry, String> jsonFileNameColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_NAME.getKey()));
    jsonFileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFileName()));

    TableColumn<FileEntry, FileEntry> jsonFileLocationColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_LOCATION.getKey()));
    jsonFileLocationColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    jsonFileLocationColumn.setCellFactory(column -> new TableCell<FileEntry, FileEntry>() {
      @Override
      protected void updateItem(FileEntry item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setText(null);
          setGraphic(null);
        } else {
          setText(item.getFileLocation());
          setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
              String fileName = item.getFileLocation();

              Story story = null;

              try {
                story = fileHandlerController.loadGame(fileName).getStory();
              } catch (FileNotFoundException e) {
                e.printStackTrace();
              } catch (IllegalArgumentException e) {
                System.err.println("Incorrect file format: " + e.getMessage());
              }

              if (story != null) {
                String fileType = loadGameController.getFileType();
                setText("src/main/resources/" + fileType + "/" + item.getFileName());
              }
            }
          });
        }
      }
    });

    TableColumn<FileEntry, FileEntry> jsonBrokenLinksColumn = new TableColumn<>(languageController.getTranslation(Dictionary.BROKEN_LINKS.getKey()));
    jsonBrokenLinksColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    jsonBrokenLinksColumn.setCellFactory(column -> {
      return new TableCell<FileEntry, FileEntry>() {
        @Override
        protected void updateItem(FileEntry item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            String fileName = item.getFileName();
            Story story = null;
            try {
              story = fileHandlerController.loadGame(fileName).getStory();
            } catch (FileNotFoundException | IllegalArgumentException e) {
              System.out.println("Incorrect file format: " + e.getMessage());
            }
            List<Link> brokenLinks = null;
            if (story != null) {
              brokenLinks = story.getBrokenLinks();
            }

            if (brokenLinks != null && !brokenLinks.isEmpty()) {
              setText(brokenLinks.stream().map(Link::getReference).collect(Collectors.joining(", ")));
            } else {
              setText(languageController.getTranslation(Dictionary.NO_BROKEN_LINKS.getKey()));
            }
          }
        }
      };
    });


    TableColumn<FileEntry, String> jsonClickableTextColumn = new TableColumn<>(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));
    jsonClickableTextColumn.setPrefWidth(100);
    jsonClickableTextColumn.setCellFactory(tc -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));

      {
        hyperlink.setOnAction(event -> {
          FileEntry fileEntry = getTableView().getItems().get(getIndex());
          System.out.println("Clicked on file: " + fileEntry.getFileName());
          try {
            game = fileHandlerController.loadGameJson(fileEntry.getFileName());
            gameController.setGame(game);
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          screenController.activate("MainGame");
          resetPane();
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

    jsonTableView.getColumns().addAll(jsonFileNameColumn, jsonFileLocationColumn, jsonBrokenLinksColumn, jsonClickableTextColumn);
    jsonTableView.setItems(loadGameController.getSavedGames("json"));
    jsonTableView.setPrefWidth(650);

    ImageView backImage = new ImageView(new Image(getClass().getResourceAsStream("/images/back.png")));
    Button backButton = new Button();
    backButton.setId("seeThroughButton");
    backButton.setGraphic(backImage);
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setOnAction(e -> screenController.activate("MainMenu"));
    resetPane();

    // Add both tables to the stack pane
    HBox hBox = new HBox();
    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    hBox.getChildren().addAll(pathsTableView, jsonTableView);
    hBox.setSpacing(20);
    hBox.setPadding(new Insets(20, 20, 20, 20));
    hBox.setAlignment(Pos.CENTER);

    Button uploadButton = new Button(languageController.getTranslation(Dictionary.UPLOAD_FILE.getKey()));

    uploadButton.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Upload Game File");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("PATHS Files", "*.paths"),
              new FileChooser.ExtensionFilter("JSON Files", "*.json")
      );

      File selectedFile = fileChooser.showOpenDialog(null);

      if (selectedFile != null) {
        System.out.println("Selected file: " + selectedFile.getName());
        String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

        if (extension.equals("paths")) {
          try {
            loadGameController.addSavedGame(selectedFile.getName(), "paths", selectedFile);
            Platform.runLater(this::refreshTables);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        } else if (extension.equals("json")) {
          try {
            loadGameController.addSavedGame(selectedFile.getName(), "json", selectedFile);
            Platform.runLater(this::refreshTables);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        pathsTableView.setItems(loadGameController.getSavedGames("paths"));
        jsonTableView.setItems(loadGameController.getSavedGames("json"));
        refreshTables();
      }
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(hBox, uploadButton);
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20, 20, 20, 20));
    vBox.setAlignment(Pos.CENTER);

    stackPane.getChildren().add(vBox);
    borderPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true))));
    borderPane.setTop(backButton);
    borderPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * Show an alert window if the file is missing data.
   *
   * @param missingData The missing data.
   */
  private void showAlertWindow(String missingData) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(languageController.getTranslation(Dictionary.MISSING_DATA.getKey()));
    alert.setHeaderText(languageController.getTranslation(Dictionary.SOME_DATA_IS_MISSING.getKey()) + ":");
    alert.setContentText(languageController.translate(missingData) + "\n" + languageController.getTranslation(Dictionary.DO_YOU_WANT_TO_CONTINUE.getKey()) + "?");

    ButtonType buttonTypeContinue = new ButtonType(languageController.getTranslation(Dictionary.CONTINUE.getKey()), ButtonBar.ButtonData.OK_DONE);
    ButtonType buttonTypeCancel = new ButtonType(languageController.getTranslation(Dictionary.CANCEL.getKey()), ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(buttonTypeContinue, buttonTypeCancel);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add("stylesheet.css");

    alert.showAndWait().ifPresent(response -> {
      if (response == buttonTypeContinue && missingData.equals("Missing data: Player, Goals") || missingData.equals("Missing data: Player")) {
        screenController.activate("NewGame");
        resetPane();
      } else if (response == buttonTypeContinue && missingData.equals("Missing data: Goals")) {
        screenController.activate("ChooseGoals");
        resetPane();
      } else {
        alert.close();
      }
      stackPane.getStylesheets().add("stylesheet.css");

    });
  }


  /**
   * Reset the pane.
   */
  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }

  /**
   * Refresh the tables.
   */
  private void refreshTables() {
    pathsTableView.getItems().clear();
    String pathsDirectoryName = "paths"; // The name of the "paths" directory
    Path pathsDirectoryPath = Paths.get("src/main/resources", pathsDirectoryName);

    try {
      Stream<Path> pathStream = Files.walk(pathsDirectoryPath)
              .filter(Files::isRegularFile)
              .filter(path -> !path.getParent().equals(pathsDirectoryPath)) // Exclude files directly under the "paths" directory
              .filter(path -> path.toString().endsWith(".paths"));

      List<FileEntry> fileEntries = pathStream.map(path -> {
        String fileName = path.getFileName().toString();
        String directoryName = path.getParent().getFileName().toString();
        return new FileEntry(fileName, directoryName);
      }).collect(Collectors.toList());

      pathsTableView.setItems(FXCollections.observableArrayList(fileEntries));
    } catch (IOException e) {
      e.printStackTrace();
    }
    pathsTableView.refresh();

    jsonTableView.getItems().clear();
    jsonTableView.setItems(loadGameController.getSavedGames("json"));
    jsonTableView.refresh();
  }

}
