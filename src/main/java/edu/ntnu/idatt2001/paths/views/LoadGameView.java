package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.FileHandlerController;
import edu.ntnu.idatt2001.paths.controllers.GameController;
import edu.ntnu.idatt2001.paths.controllers.LoadGameController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
import edu.ntnu.idatt2001.paths.utility.GameData;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class LoadGameView extends View{
  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  private Game game;
  private GameController gameController = GameController.getInstance();
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  LoadGameController loadGameController = LoadGameController.getInstance();
  TableView<FileEntry> pathsTableView;
  TableView<FileEntry> jsonTableView;

  public LoadGameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  @Override
  public void setup() {
    // Create the TableView for .paths files
    pathsTableView = new TableView<>();

    TableColumn<FileEntry, String> pathsFileNameColumn = new TableColumn<>("File Name");
    pathsFileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFileName()));

    pathsFileNameColumn.setPrefWidth(300);

    TableColumn<FileEntry, String> pathsClickableTextColumn = new TableColumn<>("Load game");
    pathsClickableTextColumn.setPrefWidth(100);
    pathsClickableTextColumn.setCellFactory(tc -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink("Load game");

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
            }
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
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

    pathsTableView.getColumns().addAll(pathsFileNameColumn, pathsClickableTextColumn);
    pathsTableView.setItems(loadGameController.getSavedGames("paths"));
    pathsTableView.setMaxWidth(400);

    // Create the TableView for .json files
    jsonTableView = new TableView<>();

    TableColumn<FileEntry, String> jsonFileNameColumn = new TableColumn<>("File Name");
    jsonFileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFileName()));

    jsonFileNameColumn.setPrefWidth(300);

    TableColumn<FileEntry, String> jsonClickableTextColumn = new TableColumn<>("Load game");
    jsonClickableTextColumn.setPrefWidth(100);
    jsonClickableTextColumn.setCellFactory(tc -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink("Load game");

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

    jsonTableView.getColumns().addAll(jsonFileNameColumn, jsonClickableTextColumn);
    jsonTableView.setItems(loadGameController.getSavedGames("json"));
    jsonTableView.setMaxWidth(400);

    ImageView backImage = new ImageView(new Image("back.png"));
    Button backButton = new Button();
    backButton.setId("seeThroughButton");
    backButton.setGraphic(backImage);
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setOnAction(e -> screenController.activate("MainMenu"));

    // Add both tables to the stack pane
    HBox hBox = new HBox();
    Image background = new Image("background.png");
    hBox.getChildren().addAll(pathsTableView, jsonTableView);
    hBox.setSpacing(20);
    hBox.setPadding(new Insets(20, 20, 20, 20));
    hBox.setAlignment(Pos.CENTER);

    Button uploadButton = new Button("Upload File");

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

  private void showAlertWindow(String missingData) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Missing Data");
    alert.setHeaderText("Some data is missing in the file:");
    alert.setContentText(missingData + "\nDo you want to continue and add this to the file?");

    ButtonType buttonTypeContinue = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(buttonTypeContinue, buttonTypeCancel);

    alert.showAndWait().ifPresent(response -> {
      if (response == buttonTypeContinue && missingData.equals("Missing data: Player Goals") || missingData.equals("Missing data: Player")) {
        screenController.activate("NewGame");
      } else if (response == buttonTypeContinue && missingData.equals("Missing data: Goals")) {
        screenController.activate("ChooseGoals");
      } else {
        alert.close();
      }
    });
  }



  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }

  private void refreshTables() {
    pathsTableView.getItems().clear();
    pathsTableView.setItems(loadGameController.getSavedGames("paths"));
    pathsTableView.refresh();

    jsonTableView.getItems().clear();
    jsonTableView.setItems(loadGameController.getSavedGames("json"));
    jsonTableView.refresh();
  }


}
