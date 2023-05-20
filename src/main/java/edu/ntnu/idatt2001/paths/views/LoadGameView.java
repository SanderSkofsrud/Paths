package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    jsonTableView = new TableView<>();

    TableColumn<File, String> fileNameColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_NAME.getKey()));
    fileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));

    TableColumn<File, String> fileLocationColumn = new TableColumn<>(languageController.getTranslation(Dictionary.FILE_LOCATION.getKey()));
    fileLocationColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAbsolutePath()));
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
        container.setPrefWidth(100);  // adjust to desired width
        vbox.setPrefWidth(100);  // adjust to desired width
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


    fileLocationColumn.setPrefWidth(370);

    TableColumn<File, String> brokenLinksColumn = new TableColumn<>(languageController.getTranslation(Dictionary.BROKEN_LINKS.getKey()));
    brokenLinksColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(loadGameController.getBrokenLinksString(param.getValue())));

    TableColumn<File, String> loadColumn = new TableColumn<>(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));
    loadColumn.setPrefWidth(100);
    loadColumn.setCellFactory(tc -> new TableCell<>() {
      private final Hyperlink hyperlink = new Hyperlink(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));

      {
        hyperlink.setOnAction(event -> {
          String selectedFile = getTableView().getItems().get(getIndex()).getName();
          System.out.println("Clicked on file: " + selectedFile);
          try {
            game = fileHandlerController.loadGameJson(selectedFile);
            gameController.setGame(game);
            playerController.setActiveCharacter(game.getPlayer().getCharacterModel());
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

    jsonTableView.getColumns().addAll(fileNameColumn, fileLocationColumn, brokenLinksColumn, loadColumn);
    jsonTableView.setItems(loadGameController.getSavedGames("json"));
    jsonTableView.setPrefWidth(650);

    HBox hBox = new HBox();
    hBox.getChildren().add(jsonTableView);
    hBox.setAlignment(Pos.CENTER);

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
   * Reset the pane.
   */
  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
