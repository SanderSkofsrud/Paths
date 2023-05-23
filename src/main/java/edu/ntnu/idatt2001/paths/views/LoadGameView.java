package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.File;


/**
 * The type Load game view.
 * The class is used to create the GUI of the load game view.
 *
 * @author Helle R. and Sander S.
 * @version 1.3 20.05.2023
 */
public class LoadGameView extends View {
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
   * The File handler controller.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The Load game controller.
   */
  LoadGameController loadGameController = new LoadGameController();
  LanguageController languageController = LanguageController.getInstance();
  PlayerController playerController = PlayerController.getInstance();
  /**
   * The Json table view.
   */
  TableView<File> jsonTableView;

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
    try {
      jsonTableView = loadGameController.createTableView(screenController);
    } catch (RuntimeException e) {
      ShowAlert.showError(e.getMessage(), e.getMessage());
    }

    HBox hbox = new HBox();
    hbox.getChildren().add(jsonTableView);
    hbox.setAlignment(Pos.CENTER);

    ImageView backImage = new ImageView(new Image(getClass()
        .getResourceAsStream("/images/back.png")));
    Button backButton = new Button();
    backButton.setId("seeThroughButton");
    backButton.setGraphic(backImage);
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setOnAction(e -> screenController.activate("MainMenu"));
    resetPane();

    VBox vbox = new VBox();
    vbox.getChildren().addAll(hbox);
    vbox.setSpacing(20);
    vbox.setPadding(new Insets(20, 20, 20, 20));
    vbox.setAlignment(Pos.CENTER);

    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));

    stackPane.getChildren().add(vbox);
    borderPane.setBackground(new Background(new BackgroundImage(background,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1.0, 1.0, true, true, false, true))));
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
