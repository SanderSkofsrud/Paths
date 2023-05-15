  package edu.ntnu.idatt2001.paths.views;

  import edu.ntnu.idatt2001.paths.controllers.LanguageController;
  import edu.ntnu.idatt2001.paths.controllers.ScreenController;
  import edu.ntnu.idatt2001.paths.utility.Dictionary;
  import edu.ntnu.idatt2001.paths.utility.Language;
  import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
  import javafx.application.Application;
  import javafx.beans.value.ChangeListener;
  import javafx.beans.value.ObservableValue;
  import javafx.collections.FXCollections;
  import javafx.concurrent.Task;
  import javafx.geometry.Insets;
  import javafx.geometry.Pos;
  import javafx.scene.Cursor;
  import javafx.scene.Scene;
  import javafx.scene.control.Button;
  import javafx.scene.control.ComboBox;
  import javafx.scene.control.ListCell;
  import javafx.scene.control.Tooltip;
  import javafx.scene.image.Image;
  import javafx.scene.image.ImageView;
  import javafx.scene.layout.*;
  import javafx.stage.Stage;

  /**
   * The type App.
   * The class is used to create the GUI of the game and to start the game.
   *
   * @author Helle R. and Sander S.
   * @version 0.1 08.05.2023
   */
  public class App extends Application {
    /**
     * The frontPage is the main pane of the GUI.
     */
    private BorderPane frontPage = new BorderPane();
    /**
     * The frontPageScene is the main scene of the GUI.
     */
    private Scene frontPageScene = new Scene(frontPage);
    /**
     * The Screen controller.
     * The screenController is used to switch between the different views of the GUI.
     */
    protected ScreenController screenController = new ScreenController(frontPageScene);
    /**
     * The newGameView is the view of the GUI where the player can create a new game.
     */
    private NewGameView newGameView = new NewGameView(screenController);
    /**
     * The ChooseGoalsView is the view of the GUI where the player can choose the goals of the game.
     */
    private ChooseGoalsView ChooseGoalsView = new ChooseGoalsView(screenController);
    /**
     * The mainGameView is the view of the GUI where the player can play the game.
     */
    private MainGameView mainGameView = new MainGameView(screenController);
    /**
     * The loadGameView is the view of the GUI where the player can load a saved game.
     */
    private LoadGameView loadGameView = new LoadGameView(screenController);
    /**
     * The minigameView is the view of the GUI where the player can play the minigame.
     */
    private MinigameView minigameView = new MinigameView(screenController);
    private LanguageController languageController = LanguageController.getInstance();
    private ComboBox<Language> languageComboBox = new ComboBox<>(FXCollections.observableArrayList(Language.values()));
    /**
     * The constant primaryStage.
     * The primaryStage is used to set the stage of the GUI.
     */
    public static Stage primaryStage;

    /**
     * The entry point of the JavaFX application.
     * The start method is used to start the GUI.
     * The start method is used to set the title, width, height and scene of the GUI.
     * The start method is used to add the different views to the screenController.
     * The start method is used to show the GUI.
     *
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
      App.primaryStage = primaryStage;

      screenController.addScreen("MainMenu", new View() {
        @Override
        public Pane getPane() {
          return frontPage;
        }
        @Override
        public void setup() {
        }
        @Override
        void resetPane() {
        }
      });
      screenController.addScreen("NewGame", newGameView);
      screenController.addScreen("ChooseGoals", ChooseGoalsView);
      screenController.addScreen("MainGame", mainGameView);
      screenController.addScreen("LoadGame", loadGameView);
      screenController.addScreen("Minigame", minigameView);

      primaryStage.setTitle("Paths");
      primaryStage.setWidth(500);
      primaryStage.setHeight(500);
      primaryStage.setMaximized(true);

      primaryStage.setScene(frontPageScene);
      primaryStage.show();

      this.setup();
    }

    /**
     * The setup method is used to set up the GUI.
     * The setup method is used to add the logo, tagline, buttons and background to the GUI.
     * The setup method sets up the frontPage.
     */
    private void setup() {
      SoundPlayer soundPlayer = new SoundPlayer();
      ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
      logo.preserveRatioProperty().set(true);
      logo.setFitWidth(500);
      ImageView tagline = new ImageView(new Image(getClass().getResourceAsStream("/images/tagline.png")));
      tagline.preserveRatioProperty().set(true);
      tagline.setFitWidth(300);
      VBox vBox = new VBox();
      vBox.getChildren().addAll(logo, tagline);
      vBox.setAlignment(Pos.CENTER);
      vBox.setSpacing(25);
      frontPage.setCenter(vBox);

      Button newGame = new Button(Dictionary.NEW_GAME.getKey());
      Button loadGame = new Button(Dictionary.LOAD_GAME.getKey());
      newGame.setId("mainMenuButton");
      loadGame.setId("mainMenuButton");

      newGame.setOnAction(e -> {
        screenController.activate("NewGame");
        soundPlayer.play("/sounds/confirm.wav");
      });

      loadGame.setOnAction(e -> {
        screenController.activate("LoadGame");
        soundPlayer.play("/sounds/confirm.wav");
      });

      languageComboBox.setId("mainMenuComboBox");
      languageComboBox.setCellFactory(param -> createLanguageCell());
      languageComboBox.setButtonCell(createLanguageCell());
      languageComboBox.setDisable(true);

      if (languageController.isInternetAvailable()) {
        languageComboBox.setDisable(false);
      } else {
        languageComboBox.setTooltip(new Tooltip("You can not change language while not connected to the internet"));
      }

      languageComboBox.setValue(languageController.getCurrentLanguageEnum());
      languageComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        soundPlayer.play("/sounds/confirm.wav");
        Task<Void> languageSettingTask = new Task<>() {
          @Override
          protected Void call() {
            languageController.setLanguage(newValue.toString());
            return null;
          }
          @Override
          protected void running() {
            super.running();
            frontPageScene.setCursor(Cursor.WAIT);
          }
          @Override
          protected void succeeded() {
            super.succeeded();
            frontPageScene.setCursor(Cursor.DEFAULT);
            newGame.setText(languageController.getTranslation(Dictionary.NEW_GAME.getKey()));
            loadGame.setText(languageController.getTranslation(Dictionary.LOAD_GAME.getKey()));
          }
          @Override
          protected void failed() {
            super.failed();
            frontPageScene.setCursor(Cursor.DEFAULT);
          }
        };
        new Thread(languageSettingTask).start();
      });

      HBox hBox = new HBox();
      hBox.getChildren().addAll(newGame, loadGame, languageComboBox);
      hBox.setAlignment(Pos.CENTER);
      hBox.getStylesheets().add("stylesheet.css");
      hBox.setSpacing(20);
      hBox.setPadding(new Insets(0, 0, 150, 0));
      frontPage.setBottom(hBox);

      Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
      BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
      frontPage.setBackground(new Background(backgroundImage));

      primaryStage.focusedProperty().addListener((ov, t, t1) -> languageComboBox.setDisable(!languageController.isInternetAvailable()));

    }

    private ListCell<Language> createLanguageCell() {
      return new ListCell<>() {
        private final ImageView flagImageView = new ImageView();

        @Override
        protected void updateItem(Language item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            setText(null);
            setGraphic(null);
          } else {
            try {
              Image flagImage = new Image(getClass().getResourceAsStream("/images/flags/" + item.toString() + ".png"));
              if (flagImage.isError()) {
                setText(item.toString());
                setGraphic(null);
              } else {
                flagImageView.setImage(flagImage);
                flagImageView.setFitWidth(16);
                flagImageView.setFitHeight(16);
                setText(item.toString());
                setGraphic(flagImageView);
              }
            } catch (NullPointerException e) {
              setText(item.toString());
              setGraphic(null);
            }
          }
        }
      };
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
      launch(args);
    }
  }
