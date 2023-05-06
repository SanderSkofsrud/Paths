package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The type App.
 */
public class App extends Application {

  private BorderPane frontPage = new BorderPane();
  private Scene frontPageScene = new Scene(frontPage);
  /**
   * The Screen controller.
   */
  protected ScreenController screenController = new ScreenController(frontPageScene);
  private NewGameView newGameView = new NewGameView(screenController);
  private ChooseGoalsView ChooseGoalsView = new ChooseGoalsView(screenController);
  private MainGameView mainGameView = new MainGameView(screenController);
  private LoadGameView loadGameView = new LoadGameView(screenController);
  /**
   * The constant primaryStage.
   */
  public static Stage primaryStage;

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

    primaryStage.setTitle("Paths");
    primaryStage.setWidth(500);
    primaryStage.setHeight(500);
    primaryStage.setMaximized(true);

    primaryStage.setScene(frontPageScene);
    primaryStage.show();

    this.setup();
  }

  private void setup() {
    SoundPlayer soundPlayer = new SoundPlayer();
    ImageView logo = new ImageView(new Image("logo.png"));
    logo.preserveRatioProperty().set(true);
    logo.setFitWidth(500);
    ImageView tagline = new ImageView(new Image("tagline.png"));
    tagline.preserveRatioProperty().set(true);
    tagline.setFitWidth(300);
    VBox vBox = new VBox();
    vBox.getChildren().addAll(logo, tagline);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(25);
    frontPage.setCenter(vBox);

    Button newGame = new Button("New Game");
    Button loadGame = new Button("Load Game");
    newGame.setId("mainMenuButton");
    loadGame.setId("mainMenuButton");

    newGame.setOnAction(e -> {
      screenController.activate("NewGame");
      soundPlayer.play("sounds/confirm.wav");
    });

    loadGame.setOnAction(e -> {
      screenController.activate("LoadGame");
      soundPlayer.play("sounds/confirm.wav");
    });

    HBox hBox = new HBox();
    hBox.getChildren().addAll(newGame, loadGame);
    hBox.setAlignment(Pos.CENTER);
    hBox.getStylesheets().add("stylesheet.css");
    hBox.setSpacing(20);
    hBox.setPadding(new Insets(0, 0, 150, 0));
    frontPage.setBottom(hBox);

    Image background = new Image("background.png");
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    frontPage.setBackground(new Background(backgroundImage));
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
