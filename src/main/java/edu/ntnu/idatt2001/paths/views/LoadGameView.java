package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.FileHandlerController;
import edu.ntnu.idatt2001.paths.controllers.LoadGameController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.utility.FileEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;

public class LoadGameView extends View{
  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  protected Game game;
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
    TableView<FileEntry> tableView = new TableView<>();

    TableColumn<FileEntry, String> fileNameColumn = new TableColumn<>("File Name");
    fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
    fileNameColumn.setPrefWidth(300);

    TableColumn<FileEntry, String> clickableTextColumn = new TableColumn<>("Load game");
    clickableTextColumn.setPrefWidth(100);
    clickableTextColumn.setCellFactory(tc -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink("Load game");

      {
        hyperlink.setOnAction(event -> {
          FileEntry fileEntry = getTableView().getItems().get(getIndex());
          System.out.println("Clicked on file: " + fileEntry.getFileName());
          try {
            game = fileHandlerController.loadGame(fileEntry.getFileName());
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

    tableView.getColumns().addAll(fileNameColumn, clickableTextColumn);
    tableView.setItems(loadGameController.getSavedGames());
    tableView.setMaxWidth(400);

    stackPane.getChildren().add(tableView);
  }

  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
