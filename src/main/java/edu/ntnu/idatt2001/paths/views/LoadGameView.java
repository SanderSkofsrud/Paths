package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
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

              Game game = null;

              try {
                game = fileHandlerController.loadGameJson(fileName);
              } catch (FileNotFoundException e) {
                e.printStackTrace();
              } catch (IllegalArgumentException e) {
                System.err.println("Incorrect file format: " + e.getMessage());
              }

              if (game != null) {
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
            Game game = null;
            try {
              game = fileHandlerController.loadGameJson(fileName);
            } catch (FileNotFoundException | IllegalArgumentException e) {
              System.out.println("Incorrect file format: " + e.getMessage());
            }
            List<Link> brokenLinks = null;
            if (game != null) {
              brokenLinks = game.getStory().getBrokenLinks();
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
    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));

    Button uploadButton = new Button(languageController.getTranslation(Dictionary.UPLOAD_FILE.getKey()));

    uploadButton.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Upload Game File");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

      fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("PATHS Files", "*.paths")
      );

      File selectedFile = fileChooser.showOpenDialog(null);

      if (selectedFile != null) {
        System.out.println("Selected file: " + selectedFile.getName());
        String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

        if (extension.equals("paths")) {
          try {
            loadGameController.addSavedGame(selectedFile.getName(), "paths", selectedFile);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(jsonTableView, uploadButton);
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20, 20, 20, 20));
    vBox.setAlignment(Pos.CENTER);

    stackPane.getChildren().add(vBox);
    borderPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true))));
    borderPane.setTop(backButton);
    borderPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * Reset the pane.
   */
  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
