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
  private Label playerHealthLabel = new Label();
  /**
   * The Player score label.
   * The playerScoreLabel is the label that shows the score of the player.
   */
  private Label playerScoreLabel = new Label();
  /**
   * The Player inventory label.
   * The playerInventoryLabel is the label that shows the inventory of the player.
   */
  private Label playerInventoryLabel = new Label();
  /**
   * The Player gold label.
   * The playerGoldLabel is the label that shows the gold of the player.
   */
  private Label playerGoldLabel = new Label();
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
   * The Undo button.
   * The undoButton is the button that is used to undo the last action.
   */
  private Button undoButton;
  /**
   * The File handler controller.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();

  /**
   * The Player controller.
   */
  PlayerController playerController = PlayerController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
  private ImageView soundImageView;
  private boolean isMuted = false;
  private SoundPlayer soundPlayer = new SoundPlayer();
  private double playerStartHealth;

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
    //borderPane.setTop(attributesBox);
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
    soundPlayer.playOnLoop("/sounds/ambiance.wav");
    game = gameController.getGame();
    currentPassage = game.getCurrentPassage();
    player = game.getPlayer();
    goals = game.getGoals();
    story = game.getStory();
    playerStartHealth = player.getHealth();

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

    EventHandler<MouseEvent> setOnMouseClicked = event -> {
      Pair<Timeline, Passage> timelineAndPassage = (Pair<Timeline, Passage>) textFlow.getUserData();
      timeline = timelineAndPassage.getKey();
      Passage passage = timelineAndPassage.getValue();
      if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
        timeline.stop();
        String[] displayedWords = passageContent.getText().split("\\s+");
        String[] allWords = passage.getContent().split("\\s+");
        String remainingText = String.join(" ", Arrays.copyOfRange(allWords, displayedWords.length, allWords.length));
        passageContent.appendText(remainingText);
      }
    };

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

    setupTopBar(textFlow);
    setupAttributesBox();

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
  void resetPane() {
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
  private Pair<Timeline, Passage> updateUIWithPassage(TextFlow textFlow, Passage passage) {
    passageContent.clear();
    setupTopBar(textFlow);
    setupAttributesBox();

    previousPassage = currentPassage;
    currentPassage = passage;

    String translatedContent = languageController.translate(currentPassage.getContent());

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
            for (Action action : link.getActions()) {
              action.execute(player);
            }
          } catch (Exception e) {
            ShowAlert.showError(e.getMessage(), e.getMessage());
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
          if (game.getStory().getBrokenLinks().contains(link)) {
            ShowAlert.showInformation(languageController.getTranslation(Dictionary.BROKEN_LINK.getKey()), languageController.getTranslation(Dictionary.LINK_BROKEN.getKey()));
            button.setDisable(true);
          } else {
            Passage nextPassage = game.go(link);
            undoButton.setDisable(false);
            timeline.stop();
            updateUIWithPassage(textFlow, nextPassage);
            game.setCurrentPassage(nextPassage);

            if (!MinigameView.hasPlayed()) {
              int random = (int) (Math.random() * 100) + 1;
              if (random <= 10) {
                playerController.setPlayer(player);
                screenController.activate("Minigame");
              }
            }
          }
          updatePlayerInfo();
        });

        buttonsBox.getChildren().add(button);
        button.setId("subMenuButton");
      }

      updatePlayerInfo();
      textFlow.setUserData(new Pair<>(timeline, currentPassage)); // Store the Pair object in userData
      timeline.play();
      timeline.setOnFinished(event -> soundPlayer.stop());
      return new Pair<>(timeline, currentPassage);
    } else {
      Button credits = new Button(languageController.getTranslation(Dictionary.CREDITS.getKey()));
      credits.setOnAction(event1 -> {
        screenController.activate("FinalPassageView");
        resetPane();
      });
      credits.setId("subMenuButton");
      buttonsBox.getChildren().clear();
      buttonsBox.getChildren().add(credits);
      return null;
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

    ProgressBar healthBar = new ProgressBar();
    healthBar.setMinWidth(100);
    healthBar.setMaxWidth(Double.MAX_VALUE);
    healthBar.setProgress(player.getHealth() / playerStartHealth);
    healthBar.setStyle("-fx-accent: red;");

    playerHealthLabel.setText(languageController.getTranslation(Dictionary.HEALTH.getKey()) + ": " + player.getHealth());
    playerHealthLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerHealthLabel.setPadding(new Insets(10, 10, 10, 10));

    playerGoldLabel.setText(languageController.getTranslation(Dictionary.GOLD.getKey()) + ": " + player.getGold());
    playerGoldLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerGoldLabel.setPadding(new Insets(10, 10, 10, 10));

    playerScoreLabel.setText(languageController.getTranslation(Dictionary.SCORE.getKey()) + ": " + player.getScore());
    playerScoreLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerScoreLabel.setPadding(new Insets(10, 10, 10, 10));

    inventoryImageBox = new HBox();
    inventoryImageBox.setSpacing(7);
    for (String item : player.getInventory()) {
      String resourcePath = "/images/" + item + ".png";
      InputStream imageStream = getClass().getResourceAsStream(resourcePath);

      if (imageStream != null) {
        ImageView itemImageView = new ImageView(new Image(imageStream));
        itemImageView.setFitWidth(20);
        itemImageView.setFitHeight(20);
        inventoryImageBox.getChildren().add(itemImageView);
      } else {
        inventoryImageBox.getChildren().add(new Label(item));
      }
    }


    playerInventoryLabel.setText(languageController.getTranslation(Dictionary.INVENTORY.getKey()) + ": ");

    inventoryBox = new HBox(playerInventoryLabel, inventoryImageBox);
    inventoryBox.setPadding(new Insets(10, 10, 0, 10));
    inventoryBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

    Image undoImage = new Image(getClass().getResourceAsStream("/images/undo.png"));
    Image exitImage = new Image(getClass().getResourceAsStream("/images/exit.png"));
    Image helpImage = new Image(getClass().getResourceAsStream("/images/help.png"));
    Image homeImage = new Image(getClass().getResourceAsStream("/images/home.png"));
    Image restartImage = new Image(getClass().getResourceAsStream("/images/restart.png"));

    ImageView undoImageView = new ImageView(undoImage);
    undoImageView.setFitWidth(30);
    undoImageView.setFitHeight(30);

    ImageView exitImageView = new ImageView(exitImage);
    exitImageView.setFitWidth(30);
    exitImageView.setFitHeight(30);

    ImageView helpImageView= new ImageView(helpImage);
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
        soundPlayer.playOnLoop("/sounds/ambiance.wav");
        Image soundImageOn = new Image(getClass().getResourceAsStream("/images/sound.png"));
        soundImageView.setImage(soundImageOn);
        isMuted = false;
      } else {
        soundPlayer.stop();
        Image soundImageOff = new Image(getClass().getResourceAsStream("/images/nosound.png"));
        soundImageView.setImage(soundImageOff);
        isMuted = true;
      }
    });

    undoButton = new Button();
    undoButton.setGraphic(undoImageView);
    undoButton.setStyle("-fx-background-color: transparent;");
    undoButton.setDisable(true);

    undoButton.setOnAction(event -> {
      redo(textFlow);
      undoButton.setDisable(true);
    });

    Button exitButton = new Button();
    exitButton.setGraphic(exitImageView);
    exitButton.setStyle("-fx-background-color: transparent;");

    exitButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.EXIT.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary.CHOOSE_AN_OPTION.getKey()));
      alert.setContentText(languageController.getTranslation(Dictionary.LOSE_PROGRESS.getKey()));

      ButtonType cancel = new ButtonType(languageController.getTranslation(Dictionary.CANCEL.getKey()));
      ButtonType saveAndExit = new ButtonType(languageController.getTranslation(Dictionary.SAVE_EXIT.getKey()));
      ButtonType exitWithoutSaving = new ButtonType(languageController.getTranslation(Dictionary.EXIT.getKey()));

      alert.getButtonTypes().setAll(saveAndExit,exitWithoutSaving, cancel);

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == cancel) {
          alert.close();
        } else if (result.get() == saveAndExit) {
          FileHandlerController.getInstance().saveGameJson(player.getName(), null, game);
          Platform.exit();
        } else if (result.get() == exitWithoutSaving) {
          Platform.exit();
        }
      }
      borderPane.getStylesheets().add("stylesheet.css");
    });


    Button questionButton = new Button();
    questionButton.setGraphic(helpImageView);
    questionButton.setStyle("-fx-background-color: transparent;");
    questionButton.setOnAction(actionEvent -> {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.GAME_HELP.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary.GAME_HELP.getKey()));
      alert.setContentText(languageController.getTranslation(Dictionary.GAME_HELP_TEXT.getKey()));

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      alert.showAndWait();

    });

    Button homeButton = new Button();
    homeButton.setGraphic(homeImageView);
    homeButton.setStyle("-fx-background-color: transparent;");
    homeButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.HOME.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary.CHOOSE_AN_OPTION.getKey()));
      alert.setContentText(languageController.getTranslation(Dictionary.LOSE_PROGRESS_HOME.getKey()));

      ButtonType cancel = new ButtonType(languageController.getTranslation(Dictionary.CANCEL.getKey()));
      ButtonType saveAndGoHome = new ButtonType(languageController.getTranslation(Dictionary.SAVE_HOME.getKey()));
      ButtonType goHomeWithoutSaving = new ButtonType(languageController.getTranslation(Dictionary.HOME.getKey()));

      alert.getButtonTypes().setAll(saveAndGoHome,goHomeWithoutSaving, cancel);

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == cancel) {
          alert.close();
        } else if (result.get() == saveAndGoHome) {
          FileHandlerController.getInstance().saveGameJson(player.getName(), null, game);
          gameController.resetGame(); // Add this line
          screenController.activate("MainMenu");
          resetPane();
        } else if (result.get() == goHomeWithoutSaving) {
          gameController.resetGame(); // Add this line
          screenController.activate("MainMenu");
          resetPane();
        }
      }
      stackPane.getStylesheets().add("stylesheet.css");
    });
    Button restartButton = new Button();
    restartButton.setGraphic(restartImageView);
    restartButton.setStyle("-fx-background-color: transparent;");

    restartButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Restart");
      alert.setHeaderText("Do you want to restart the game?");

      ButtonType cancel = new ButtonType("Cancel");
      ButtonType restart = new ButtonType("Restart");

      alert.getButtonTypes().setAll(restart, cancel);

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == cancel) {
          alert.close();
        } else if (result.get() == restart) {
          try {
            game = FileHandlerController.getInstance().loadGameJson(player.getName() + ".json", "src/main/resources/initialGame/");
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          player = game.getPlayer();
          words = null;
          characters = null;
          timeline.stop();
          updateUIWithPassage(textFlow,game.begin());
        }
      }
      borderPane.getStylesheets().add("stylesheet.css");
    });

    HBox topRightBox = new HBox();
    topRightBox.setAlignment(Pos.TOP_RIGHT);
    topRightBox.setPadding(new Insets(10, 10, 10, 10));
    topRightBox.getChildren().addAll(undoButton, restartButton, homeButton, questionButton, exitButton, soundButton);

    VBox goalsVbox = goalsVbox();

    attributesBox.getChildren().addAll(playerHealthLabel, healthBar, playerGoldLabel, playerScoreLabel, inventoryBox, goalsVbox);

    HBox topBox = new HBox();
    topBox.getChildren().addAll(attributesBox, topRightBox);
    topBox.setHgrow(attributesBox, Priority.ALWAYS);

    borderPane.setTop(topBox);
  }

  /**
   * Updates the player attributes.
   */
  private void updatePlayerAttributes() {
    playerHealthLabel = new Label(languageController.getTranslation(Dictionary.HEALTH.getKey()) + ": " + player.getHealth());
    playerGoldLabel = new Label(languageController.getTranslation(Dictionary.GOLD.getKey()) + ": " + player.getGold());
    playerScoreLabel = new Label(languageController.getTranslation(Dictionary.SCORE.getKey()) + ": " + player.getGold());
    playerInventoryLabel = new Label(languageController.getTranslation(Dictionary.INVENTORY.getKey()) + ": " + player.getGold());
  }

  /**
   * Creates the goals VBox.
   *
   * @return the goals VBox
   */
  private VBox goalsVbox() {
    VBox goalsVbox = new VBox();
    goalsVbox.setPadding(new Insets(10, 10, 10, 10));
    goalsVbox.setSpacing(10);
    goalsVbox.setAlignment(Pos.TOP_CENTER);

    Label goalsLabel = new Label(languageController.getTranslation(Dictionary.GOALS_IN_GAME.getKey()));
    goalsVbox.getChildren().add(goalsLabel);

    for (Goal goal : game.getGoals()) {
      HBox goalHbox = new HBox();
      Label goalLabel = new Label(goal.toString());
      ProgressBar goalProgressBar = new ProgressBar();
      goalProgressBar.setProgress(0);
      double progress = 0.0;
      if (goal.getClass() == ScoreGoal.class) {
        progress = (double) player.getScore() / ((ScoreGoal) goal).getMinimumScore();
      } else if (goal.getClass() == HealthGoal.class) {
        progress = (double) player.getHealth() / ((HealthGoal) goal).getMinimumHealth();
      } else if (goal.getClass() == InventoryGoal.class) {
        progress = (double) player.getInventory().size() / ((InventoryGoal) goal).getMandatoryItems().size();
      } else if (goal.getClass() == GoldGoal.class) {
        progress = (double) player.getGold() / ((GoldGoal) goal).getMinimumGold();
      }
      if (progress != 0.0) {
        goalProgressBar.setProgress(progress);
      } else {
        goalProgressBar.setProgress(0.0);
      }
      goalHbox.getChildren().addAll(goalLabel, goalProgressBar);
      goalsVbox.getChildren().add(goalHbox);
    }

    return goalsVbox;
  }

  /**
   * Updates the player info.
   */
  private void updatePlayerInfo() {
    updatePlayerAttributes();
    VBox goalsVbox = goalsVbox();
    attributesBox.getChildren().setAll(playerHealthLabel, playerGoldLabel, playerScoreLabel, playerInventoryLabel, goalsVbox);
  }

  private void redo(TextFlow textFlow) {
    if (previousPassage != null) {
      Passage tempPassage = currentPassage;
      currentPassage = previousPassage;
      for (Link link : currentPassage.getLinks()) {
        if (Objects.equals(link.getReference(), tempPassage.getTitle())) {
          for (Action action : link.getActions()) {
            action.execute(player);
          }
        }
      }
      previousPassage = tempPassage;

      timeline.stop();
      updateUIWithPassage(textFlow, currentPassage);
    }
  }

}
