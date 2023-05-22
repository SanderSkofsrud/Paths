package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.actions.Action;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Main game view.
 * The class is used to create the GUI of the game and to start the game.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class MainGameView extends View{
  /**
   * The Border pane.
   * The borderPane is the main pane of the GUI.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   * The stackPane is used to stack the different panes on top of each other.
   */
  protected StackPane stackPane;
  /**
   * The Screen controller.
   * The screenController is used to switch between the different views of the GUI.
   */
  private ScreenController screenController;
  /**
   * The Game controller.
   * The gameController is used to control the game.
   */
  private GameController gameController = GameController.getInstance();
  /**
   * The Game.
   * The game is the game that is being played.
   */
  private Game game;
  /**
   * The Current passage.
   * The currentPassage is the passage that the player is currently in.
   */
  private Passage currentPassage;
  /**
   * The player.
   * The player is the player that is playing the game.
   */
  private Player player;
  /**
   * The Goals.
   * The goals are the goals of the game.
   */
  private List<Goal> goals;
  /**
   * The Passage content.
   * The passageContent is the text of the passage that the player is currently in.
   */
  private TextArea passageContent;
  /**
   * The Player health label.
   * The playerHealthLabel is the label that shows the health of the player.
   */
  private Label playerHealthLabel;
  /**
   * The Player score label.
   * The playerScoreLabel is the label that shows the score of the player.
   */
  private Label playerScoreLabel;
  /**
   * The Player inventory label.
   * The playerInventoryLabel is the label that shows the inventory of the player.
   */
  private Label playerInventoryLabel;
  /**
   * The Player gold label.
   * The playerGoldLabel is the label that shows the gold of the player.
   */
  private Label playerGoldLabel;
  /**
   * The buttons box.
   * The buttonsBox is the box that contains the buttons of the GUI.
   */
  private HBox buttonsBox;
  /**
   * The Attributes box.
   * The attributesBox is the box that contains the attributes of the player.
   */
  private HBox attributesBox = new HBox();
  /**
   * The Inventory image box.
   * The inventoryImageBox is the box that contains the images of the inventory of the player.
   */
  private HBox inventoryImageBox = new HBox();
  /**
   * The Inventory box.
   * The inventoryBox is the box that contains the inventory of the player.
   */
  private HBox inventoryBox = new HBox();
  /**
   * The array words.
   * The words array is the array that contains the words of the passage that the player is currently in.
   * The words are stored in an array so that they can be displayed in a text area.
   */
  private String[] words;
  private char[] characters;
  /**
   * The timeline.
   * The timeline is the timeline of the animation.
   * The timeline is used to animate the text of the passage.
   */
  private Timeline timeline;
  /**
   * The Story.
   * The story is the story that the player is playing.
   */
  private Story story;
  /**
   * The Previous passage.
   * The previousPassage is the passage that the player was in before the current passage.
   * Used for redo.
   */
  private Passage previousPassage;

  private ShowAlert showAlert = new ShowAlert();
  /**
   * The File handler controller.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();

  /**
   * The Player controller.
   */
  PlayerController playerController = PlayerController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
  MainGameController mainGameController = new MainGameController(this);
  private ImageView soundImageView;
  private boolean isMuted = false;
  private SoundPlayer soundPlayerLoop = new SoundPlayer();
  private double playerStartHealth;
  private boolean hasPlayed = false;
  /**
   * Instantiates a new Main game view.
   *
   * @param screenController the screen controller
   */
  public MainGameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }

  /**
   * Returns the pane of the GUI.
   * @return the pane of the GUI.
   */
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the top bar of the GUI.
   */
  private void setupButtonsBox() {
    buttonsBox = new HBox();
    borderPane.setBottom(buttonsBox);
    buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
  }

  /**
   * Sets up the attribute box.
   */
  private void setupAttributesBox() {
    attributesBox = new HBox();
    attributesBox.setAlignment(Pos.TOP_LEFT);
    attributesBox.setPadding(new Insets(10, 10, 10, 10));
  }

  /**
   * Sets up the main game view.
   * The method is used to set up the GUI of the game.
   * The method sets up the buttons, the attributes of the player,
   * the inventory of the player and the text of the passage.
   * The method also sets up the animation of the text of the passage.
   * The method also sets up the background of the GUI.
   * The method also sets up the music of the GUI.
   */
  @Override
  public void setup() {
    soundPlayerLoop.playOnLoop("/sounds/ambiance.wav");
    mainGameController.setup();
    currentPassage = mainGameController.getCurrentPassage();

    passageContent = new TextArea();
    passageContent.setWrapText(true);
    passageContent.setEditable(false);

    TextFlow textFlow = new TextFlow(passageContent);

    setupButtonsBox();

    ScrollPane scrollPane = new ScrollPane(textFlow);
    scrollPane.setFitToWidth(true);
    scrollPane.setMaxWidth(500);
    scrollPane.setMaxHeight(197);
    scrollPane.setPrefViewportHeight(200);

    scrollPane.setStyle("-fx-background-color: transparent;");
    passageContent.setStyle("-fx-background-color: transparent;");
    textFlow.setStyle("-fx-background-color: transparent;");

    EventHandler<MouseEvent> setOnMouseClicked = mainGameController.createMouseClickedEventHandler(textFlow, passageContent);

    passageContent.setOnMouseClicked(setOnMouseClicked);
    textFlow.setOnMouseClicked(setOnMouseClicked);
    scrollPane.setOnMouseClicked(setOnMouseClicked);

    VBox scrollPaneAndButtonsBox = new VBox(scrollPane, buttonsBox);
    scrollPaneAndButtonsBox.setAlignment(Pos.BOTTOM_CENTER);
    borderPane.setBottom(scrollPaneAndButtonsBox);

    HBox info = new HBox();
    info.setAlignment(Pos.CENTER);
    info.setPadding(new Insets(10, 10, 10, 10));

    ImageView characterImage = new ImageView(new Image(getClass().getResourceAsStream("/images/" + playerController.getActiveCharacter())));
    characterImage.setFitHeight(250);
    characterImage.setFitWidth(250);
    characterImage.setPreserveRatio(true);
    info.getChildren().addAll(characterImage, scrollPane);

    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    stackPane.getChildren().add(info);
    stackPane.setStyle("-fx-background-color: transparent;");
    stackPane.setAlignment(info, Pos.CENTER);

    borderPane.setCenter(stackPane);

    HBox buttonsBox = new HBox();
    borderPane.setBottom(buttonsBox);

    textFlow.setUserData(updateUIWithPassage(textFlow, currentPassage));

    Image background = new Image(getClass().getResourceAsStream("/images/gameBackground.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    borderPane.setBackground(new Background(backgroundImage));
    borderPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * Resets the pane of the GUI.
   */
  @Override
  public void resetPane() {
    stackPane.getChildren().clear();
    borderPane.setTop(null);
    borderPane.setBottom(null);
    borderPane.setCenter(null);
    inventoryBox.getChildren().clear();
    inventoryImageBox.getChildren().clear();
    characters = null;
    words = null;
    timeline.stop();
  }

  /**
   * Updates ut the GUI with the passage.
   * The method is used to update the GUI with the passage.
   * The method updates the text of the passage and the buttons of the passage.
   * The method also updates the attributes of the player.
   *
   * @param textFlow the text flow
   * @param passage  the passage
   * @return the pair of the timeline and the passage
   */
  public Pair<Timeline, Passage> updateUIWithPassage(TextFlow textFlow, Passage passage) {
    passageContent.clear();
    setupAttributesBox();
    setupTopBar(textFlow);

    previousPassage = currentPassage;
    currentPassage = passage;

    String translatedContent = languageController.translate(mainGameController.setupCurrentPassage());

    characters = translatedContent.toCharArray();

    SoundPlayer soundPlayer = new SoundPlayer();
    if (!isMuted) {
      soundPlayer.playOnLoop("/sounds/typing.wav");
    }

    timeline = new Timeline();
    timeline.getKeyFrames().clear();
    for (int i = 0; i < characters.length; i++) {
      int charIndex = i;
      timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 25), event -> {
        passageContent.appendText(Character.toString(characters[charIndex]));
      }));
    }

    HBox buttonsBox = (HBox) borderPane.getBottom();
    buttonsBox.getChildren().clear();
    buttonsBox.setAlignment(Pos.CENTER);
    buttonsBox.setPadding(new Insets(10, 0, 100, 0));
    buttonsBox.setSpacing(10);

    if (passage.hasLinks()) {
      for (Link link : currentPassage.getLinks()) {
        Button button = new Button(languageController.translate(link.getText()));
        button.setOnAction(event -> {
          try {
            mainGameController.executeActions(link);
          } catch (Exception e) {
            ShowAlert.showError(e.getMessage(), e.getMessage());
            soundPlayerLoop.stop();
            soundPlayer.stop();
            characters = null;
            timeline.stop();
            passageContent.clear();
            passageContent.setText(languageController.getTranslation(Dictionary.LOST_GAME.getKey()));
            Button credits = new Button(languageController.getTranslation(Dictionary.CREDITS.getKey()));
            credits.setOnAction(event1 -> {
              screenController.activate("FinalPassageView");
              resetPane();
            });
            credits.setId("subMenuButton");
            buttonsBox.getChildren().clear();
            buttonsBox.getChildren().add(credits);
            return;
          }
          if (mainGameController.getGame().getStory().getBrokenLinks().contains(link)) {
            ShowAlert.showInformation(languageController.getTranslation(Dictionary.BROKEN_LINK.getKey()), languageController.getTranslation(Dictionary.LINK_BROKEN.getKey()));
            button.setDisable(true);
          } else {
            if (mainGameController.minigameCheck() && !hasPlayed) {
              characters = null;
              timeline.stop();
              soundPlayer.stop();
              soundPlayerLoop.stop();
              soundPlayer.play("/sounds/confirm.wav");
              hasPlayed = true;
              screenController.activate("Minigame");
            } else {
              characters = null;
              timeline.stop();
              soundPlayer.stop();
              soundPlayerLoop.stop();
              soundPlayer.play("/sounds/confirm.wav");
              mainGameController.go(link, textFlow);
            }
          }
        });

        buttonsBox.getChildren().add(button);
        button.setId("subMenuButton");
      }

      textFlow.setUserData(new Pair<>(timeline, currentPassage)); // Store the Pair object in userData
      timeline.play();
      timeline.setOnFinished(event -> soundPlayer.stop());
      return new Pair<>(timeline, currentPassage);
    } else {
      timeline.play();
      timeline.setOnFinished(event -> {
        soundPlayer.stop();
        characters = null;
        timeline.stop();
        passageContent.clear();
        passageContent.setText(languageController.getTranslation(Dictionary.END_OF_GAME.getKey()));
        Button credits = new Button(languageController.getTranslation(Dictionary.CREDITS.getKey()));
        credits.setOnAction(event1 -> {
          soundPlayerLoop.stop();
          screenController.activate("FinalPassageView");
          resetPane();
        });
        credits.setId("subMenuButton");
        buttonsBox.getChildren().clear();
        buttonsBox.getChildren().add(credits);
      });
      return new Pair<>(timeline, currentPassage);
    }
  }

  /**
   * Sets up the top bar of the GUI.
   * The top bar contains the stats of the player,
   * as well as buttons to control the game.
   */
  private void setupTopBar(TextFlow textFlow) {
    attributesBox.getChildren().clear();

    attributesBox.setAlignment(Pos.TOP_LEFT);
    attributesBox.setPadding(new Insets(10, 10, 10, 10));

    ProgressBar healthBar = mainGameController.createHealthBar();

    playerHealthLabel = mainGameController.createLabel(languageController.getTranslation(Dictionary.HEALTH.getKey()), "Health");

    HBox healthBox = new HBox(playerHealthLabel, healthBar);
    healthBox.setPadding(new Insets(10, 10, 0, 10));

    playerGoldLabel = mainGameController.createLabel(languageController.getTranslation(Dictionary.GOLD.getKey()), "Gold");
    playerScoreLabel = mainGameController.createLabel(languageController.getTranslation(Dictionary.SCORE.getKey()), "Score");

    inventoryImageBox = mainGameController.createInventoryBox();

    playerInventoryLabel = new Label(languageController.getTranslation(Dictionary.INVENTORY.getKey()) + ": ");

    inventoryBox = new HBox(playerInventoryLabel, inventoryImageBox);
    inventoryBox.setPadding(new Insets(10, 10, 0, 10));

    Image exitImage = new Image(getClass().getResourceAsStream("/images/exit.png"));
    Image helpImage = new Image(getClass().getResourceAsStream("/images/help.png"));
    Image homeImage = new Image(getClass().getResourceAsStream("/images/home.png"));
    Image restartImage = new Image(getClass().getResourceAsStream("/images/restart.png"));

    ImageView exitImageView = new ImageView(exitImage);
    exitImageView.setFitWidth(30);
    exitImageView.setFitHeight(30);

    ImageView helpImageView = new ImageView(helpImage);
    helpImageView.setFitWidth(30);
    helpImageView.setFitHeight(30);

    ImageView homeImageView = new ImageView(homeImage);
    homeImageView.setFitWidth(30);
    homeImageView.setFitHeight(30);

    ImageView restartImageView = new ImageView(restartImage);
    restartImageView.setFitWidth(30);
    restartImageView.setFitHeight(30);

    Image soundImage;
    if (!isMuted) {
      soundImage = new Image(getClass().getResourceAsStream("/images/sound.png"));
    } else {
      soundImage = new Image(getClass().getResourceAsStream("/images/nosound.png"));
    }
    soundImageView = new ImageView(soundImage);
    soundImageView.setFitWidth(30);
    soundImageView.setFitHeight(30);

    Button soundButton = new Button();
    soundButton.setGraphic(soundImageView);
    soundButton.setStyle("-fx-background-color: transparent;");

    soundButton.setOnAction(event -> {
      if (isMuted) {
        soundPlayerLoop.playOnLoop("/sounds/ambiance.wav");
        Image soundImageOn = new Image(getClass().getResourceAsStream("/images/sound.png"));
        soundImageView.setImage(soundImageOn);
        isMuted = false;
      } else {
        soundPlayerLoop.stop();
        Image soundImageOff = new Image(getClass().getResourceAsStream("/images/nosound.png"));
        soundImageView.setImage(soundImageOff);
        isMuted = true;
      }
    });

    Button exitButton = null;
    try {
      exitButton = mainGameController.createExitButton();
    } catch (RuntimeException e) {
      ShowAlert.showError(languageController.translate(e.getMessage()), languageController.translate(e.getMessage()));
    }
    exitButton.setGraphic(exitImageView);
    exitButton.setStyle("-fx-background-color: transparent;");

    Button helpButton = mainGameController.createHelpButton();
    helpButton.setGraphic(helpImageView);
    helpButton.setStyle("-fx-background-color: transparent;");

    Button homeButton = null;
    try {
      homeButton = mainGameController.createHomeButton(screenController);
    } catch (RuntimeException e) {
      ShowAlert.showError(languageController.translate(e.getMessage()), languageController.translate(e.getMessage()));
    }
    homeButton.setGraphic(homeImageView);
    homeButton.setStyle("-fx-background-color: transparent;");

    Button restartButton = null;
    try {
      restartButton = mainGameController.createRestartButton(textFlow);
    } catch (RuntimeException e) {
      restartButton.setDisable(true);
      ShowAlert.showError(languageController.translate(e.getMessage()), languageController.translate(e.getMessage()));
    }
    restartButton.setGraphic(restartImageView);
    restartButton.setStyle("-fx-background-color: transparent;");


    HBox topRightBox = new HBox();
    topRightBox.setAlignment(Pos.TOP_RIGHT);
    topRightBox.setPadding(new Insets(10, 10, 10, 10));
    topRightBox.getChildren().addAll(restartButton, homeButton, helpButton, exitButton, soundButton);

    VBox goalsVbox = goalsVbox();

    attributesBox.getChildren().addAll(healthBox, playerGoldLabel, playerScoreLabel, inventoryBox, goalsVbox);

    HBox topBox = new HBox();
    topBox.getChildren().addAll(attributesBox, topRightBox);
    topBox.setHgrow(attributesBox, Priority.ALWAYS);

    borderPane.setTop(topBox);
  }

  /**
   * Creates the goals VBox.
   *
   * @return the goals VBox
   */
  private VBox goalsVbox() {
    VBox goalsVbox = mainGameController.setupGoals();
    goalsVbox.setPadding(new Insets(10, 10, 10, 10));
    goalsVbox.setSpacing(10);
    goalsVbox.setAlignment(Pos.TOP_CENTER);
    return goalsVbox;
  }

  public void setCharacters(char[] characters) {
    this.characters = characters;
    timeline.stop();
  }

  public void stopTimeline() {
    timeline.stop();
  }
}
