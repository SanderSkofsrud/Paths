package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.FileHandlerController;
import edu.ntnu.idatt2001.paths.controllers.GameController;
import edu.ntnu.idatt2001.paths.controllers.LoadGameController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

public class LoadGameView extends View{
  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  private Game game;
  private GameController gameController = GameController.getInstance();
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  LoadGameController loadGameController = LoadGameController.getInstance();
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
    TableView<FileEntry> pathsTableView = new TableView<>();

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
            game = fileHandlerController.loadGame(fileEntry.getFileName());
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

    pathsTableView.getColumns().addAll(pathsFileNameColumn, pathsClickableTextColumn);
    pathsTableView.setItems(loadGameController.getSavedGames("paths"));
    pathsTableView.setMaxWidth(400);

    // Create the TableView for .json files
    TableView<FileEntry> jsonTableView = new TableView<>();

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
    stackPane.getChildren().add(hBox);
    borderPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true))));
    borderPane.setTop(backButton);
    borderPane.getStylesheets().add("stylesheet.css");
  }


  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
