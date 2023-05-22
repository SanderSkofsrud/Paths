package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.actions.Action;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.views.MainGameView;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.util.Pair;


/**
 * The type Main game controller.
 * This class is responsible for the main game view.
 * It is used to show the user the current passage, and to handle the user's actions.
 *
 * @author Helle R. and Sander S.
 * @version 1.0 21.05.2023
 */
public class MainGameController {
  /**
   * The Game.
   * This is the game that is being played.
   */
  Game game;
  /**
   * The Player.
   * This is the player that is playing the game.
   */
  Player player;
  /**
   * The Goals.
   * This is the list of goals that the player has to achieve.
   */
  static List<Goal> goals;
  /**
   * The Story.
   * This is the story that the player is playing.
   */
  Story story;
  /**
   * The Current passage.
   * This is the current passage that the player is in.
   */
  Passage currentPassage;
  /**
   * The Player start health.
   * This is the health that the player starts with.
   */
  int playerStartHealth;
  /**
   * The Game controller.
   */
  GameController gameController = GameController.getInstance();
  /**
   * The Player controller.
   */
  PlayerController playerController = PlayerController.getInstance();
  /**
   * The Language controller.
   */
  LanguageController languageController = LanguageController.getInstance();
  /**
   * The Main game view.
   */
  MainGameView mainGameView;

  /**
   * Instantiates a new Main game controller.
   *
   * @param mainGameView the main game view
   */
  public MainGameController(MainGameView mainGameView) {
    this.mainGameView = mainGameView;
  }

  /**
   * Sets up the game.
   * This method is called when the game is started.
   */
  public void setup() {
    game = gameController.getGame();
    player = game.getPlayer();
    goals = game.getGoals();
    story = game.getStory();
    currentPassage = game.getCurrentPassage();
    if (playerStartHealth == 0) {
      playerStartHealth = player.getHealth();
    }
  }

  /**
   * Create mouse clicked event handler event handler.
   * This method is used to handle the user's mouse clicks.
   * It is used to skip the text animation.
   *
   * @param textFlow       the text flow that is being clicked
   * @param passageContent the passage content that is being clicked
   * @return the event handler that handles the mouse click
   */
  public static EventHandler<MouseEvent> createMouseClickedEventHandler(TextFlow textFlow,
                                                                        TextArea passageContent) {
    return event -> handleMouseClickedEvent(textFlow, passageContent);
  }

  /**
   * Handle mouse clicked event.
   * This method is used to handle the user's mouse clicks.
   * It is used to skip the text animation.
   * It is called by the createMouseClickedEventHandler method.
   * It is called when the user clicks on the text flow.
   * If the animation is running, it will stop the animation and show the rest of the text.
   *
   * @param textFlow       the text flow that is being clicked
   * @param passageContent the passage content that is being clicked
   */
  private static void handleMouseClickedEvent(TextFlow textFlow, TextArea passageContent) {
    Pair<Timeline, Passage> timelineAndPassage = (Pair<Timeline, Passage>) textFlow.getUserData();
    Timeline timeline = timelineAndPassage.getKey();
    Passage passage = timelineAndPassage.getValue();

    if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
      timeline.stop();
      String remainingText = passage.getContent().substring(passageContent.getLength());
      passageContent.appendText(remainingText);
    } else {
      char[] characters = passage.getContent().toCharArray();
      int currentLength = passageContent.getLength();

      timeline = new Timeline();
      timeline.getKeyFrames().clear();
      for (int i = currentLength; i < characters.length; i++) {
        int charIndex = i;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis((charIndex - currentLength) * 25),
            event -> passageContent.appendText(Character.toString(characters[charIndex]))));
      }
      timeline.play();
    }
  }

  /**
   * Execute actions.
   * This method is used to execute the actions that the link has.
   * It is called when the user clicks on a link.
   *
   * @param link the link that is being clicked
   */
  public void executeActions(Link link) {
    for (Action action : link.getActions()) {
      action.execute(player);
    }
  }

  /**
   * Minigame check boolean.
   * This method is used to check if the player has to play a minigame.
   * It is called when the player clicks on a link.
   * It has a 10% chance of returning true.
   * If it returns true, the player has to play a minigame.
   *
   * @return the boolean that indicates if the player has to play a minigame
   */
  public boolean minigameCheck() {
    int random = (int) (Math.random() * 100) + 1;
    if (random <= 10 && player.getGold() > 10) {
      playerController.setPlayer(player);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Create the inventory box.
   * This method is used to create the inventory box.
   * It stores the items that the player has.
   *
   * @return the HBox that contains the inventory
   */
  public HBox createInventoryBox() {
    HBox inventoryImageBox = new HBox();
    inventoryImageBox.setSpacing(7);
    for (String item : player.getInventory()) {
      String resourcePath = "/images/" + item + ".png";
      InputStream imageStream = getClass().getResourceAsStream(resourcePath);

      if (imageStream != null) {
        ImageView itemImageView = new ImageView(new Image(imageStream));
        itemImageView.setFitWidth(35);
        itemImageView.setFitHeight(35);
        inventoryImageBox.getChildren().add(itemImageView);
      } else {
        inventoryImageBox.getChildren().add(new Label(item));
      }
    }
    return inventoryImageBox;
  }

  /**
   * Create exit button button.
   * This method is used to create the exit button.
   * It is used to exit the game.
   *
   * @return the button that exits the game
   */
  public Button createExitButton() {
    try {
      Button exitButton = new Button();
      exitButton.setOnAction(event -> {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(languageController.getTranslation(Dictionary.EXIT.getKey()));
        alert.setHeaderText(languageController.getTranslation(Dictionary
            .CHOOSE_AN_OPTION.getKey()));
        alert.setContentText(languageController.getTranslation(Dictionary
            .LOSE_PROGRESS.getKey()));

        ButtonType cancel = new ButtonType(languageController.getTranslation(Dictionary
            .CANCEL.getKey()));
        ButtonType saveAndExit = new ButtonType(languageController.getTranslation(Dictionary
            .SAVE_EXIT.getKey()));
        ButtonType exitWithoutSaving = new ButtonType(languageController.getTranslation(Dictionary
            .EXIT.getKey()));

        alert.getButtonTypes().setAll(saveAndExit, exitWithoutSaving, cancel);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("stylesheet.css");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
          if (result.get() == cancel) {
            alert.close();
          } else if (result.get() == saveAndExit) {
            try {
              FileHandlerController.getInstance().saveGameJson(player.getName(), null, game);
            } catch (IllegalArgumentException e) {
              throw new RuntimeException(e.getMessage());
            }
            Platform.exit();
          } else if (result.get() == exitWithoutSaving) {
            Platform.exit();
          }
        }
      });
      return exitButton;
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Create help button button.
   * This method is used to create the help button.
   * It is used to show the help text.
   *
   * @return the button that shows the help text
   */
  public Button createHelpButton() {
    Button questionButton = new Button();
    questionButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.GAME_HELP.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary.GAME_HELP.getKey()));
      alert.setContentText(languageController.getTranslation(Dictionary.GAME_HELP_TEXT.getKey())
              + "\n\n" + languageController.getTranslation(Dictionary.GAME_HELP_TEXT_1.getKey())
              + "\n\n" + languageController.getTranslation(Dictionary.GAME_HELP_TEXT_2.getKey())
              + "\n\n" + languageController.getTranslation(Dictionary.GAME_HELP_TEXT_3.getKey())
              + "\n\n" + languageController.getTranslation(Dictionary.GAME_HELP_TEXT_4.getKey())
              + "\n\n" + languageController.getTranslation(Dictionary.GAME_HELP_TEXT_5.getKey()));

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      alert.showAndWait();
    });
    return questionButton;
  }

  /**
   * Create home button button.
   * This method is used to create the home button.
   * It is used to go back to the home screen.
   *
   * @param screenController the screen controller that is used to switch screens
   * @return the button that goes back to the home screen
   */
  public Button createHomeButton(ScreenController screenController) {
    Button homeButton = new Button();
    homeButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.HOME.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary
          .CHOOSE_AN_OPTION.getKey()));
      alert.setContentText(languageController.getTranslation(Dictionary
          .LOSE_PROGRESS_HOME.getKey()));

      ButtonType cancel = new ButtonType(languageController.getTranslation(Dictionary
          .CANCEL.getKey()));
      ButtonType saveAndGoHome = new ButtonType(languageController.getTranslation(Dictionary
          .SAVE_HOME.getKey()));
      ButtonType goHomeWithoutSaving = new ButtonType(languageController.getTranslation(Dictionary
          .HOME.getKey()));

      alert.getButtonTypes().setAll(saveAndGoHome, goHomeWithoutSaving, cancel);

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == cancel) {
          alert.close();
        } else if (result.get() == saveAndGoHome) {
          try {
            FileHandlerController.getInstance().saveGameJson(player.getName(), null, game);
          } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
          }
          gameController.resetGame();
          goals.clear();
          playerController.resetPlayer();
          screenController.activate("MainMenu");
          mainGameView.resetPane();
        } else if (result.get() == goHomeWithoutSaving) {
          gameController.resetGame();
          goals.clear();
          playerController.resetPlayer();
          screenController.activate("MainMenu");
          mainGameView.resetPane();
        }
      }
    });
    return homeButton;
  }

  /**
   * Create restart button button.
   * This method is used to create the restart button.
   * It is used to restart the game.
   *
   * @param textFlow the text flow that is used to show the text
   * @return the button that restarts the game
   */
  public Button createRestartButton(TextFlow textFlow) {
    Button restartButton = new Button();
    restartButton.setOnAction(event -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle(languageController.getTranslation(Dictionary.RESTART.getKey()));
      alert.setHeaderText(languageController.getTranslation(Dictionary.RESTART_GAME.getKey()));

      ButtonType cancel = new ButtonType(languageController.getTranslation(Dictionary
          .CANCEL.getKey()));
      ButtonType restart = new ButtonType(languageController.getTranslation(Dictionary
          .RESTART.getKey()));

      alert.getButtonTypes().setAll(restart, cancel);

      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add("stylesheet.css");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.isPresent()) {
        if (result.get() == cancel) {
          alert.close();
        } else if (result.get() == restart) {
          try {
            game = FileHandlerController.getInstance().loadGameJson(player
                .getName() + ".json", "src/main/resources/initialGame/");
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
          player = game.getPlayer();
          mainGameView.setCharacters(null);
          mainGameView.updateUIWithPassage(textFlow, game.begin());
        }
      }
    });
    return restartButton;
  }

  /**
   * Create health bar progress bar.
   * This method is used to create the health bar.
   * It is used to show the health of the player.
   * The color of the health bar changes depending on the health of the player.
   *
   * @return the progress bar that shows the health of the player
   */
  public ProgressBar createHealthBar() {
    ProgressBar healthBar = new ProgressBar();
    healthBar.setMinWidth(100);
    healthBar.setMaxWidth(Double.MAX_VALUE);
    healthBar.setProgress((double) player.getHealth() / playerStartHealth);
    if (player.getHealth() <= 0) {
      healthBar.setStyle("-fx-accent: red;");
    } else if (player.getHealth() <= playerStartHealth / 4) {
      healthBar.setStyle("-fx-accent: orange;");
    } else {
      healthBar.setStyle("-fx-accent: green;");
    }
    return healthBar;
  }

  /**
   * Create label label.
   * This method is used to create a label.
   * It is used to show the health, score and gold of the player.
   *
   * @param text the text that is shown on the label
   * @param type the type of the label
   * @return the label that shows the health, score and gold of the player
   */
  public Label createLabel(String text, String type) {
    Label label = new Label();
    switch (type.toLowerCase()) {
      case "health" -> {
        label.setText(text + ": " + player.getHealth());
        label.setPadding(new Insets(0, 10, 0, 0));
      }
      case "score" -> {
        label.setText(text + ": " + player.getScore());
        label.setPadding(new Insets(10, 10, 10, 10));
      }
      case "gold" -> {
        label.setText(text + ": " + player.getGold());
        label.setPadding(new Insets(10, 10, 10, 10));
      }
      default -> label.setText(text);
    }
    return label;
  }

  /**
   * Setup goals v box.
   * This method is used to setup the goals.
   * It is used to show the goals of the game.
   *
   * @return the goals v box
   */
  public VBox setupGoals() {
    VBox goalsVbox = new VBox();
    goalsVbox.getChildren().add(new Label(languageController.getTranslation(Dictionary
        .GOALS_IN_GAME.getKey())));
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
        List<String> inventoryItems = player.getInventory();
        List<String> mandatoryItems = ((InventoryGoal) goal).getMandatoryItems();

        long matchingItems = mandatoryItems.stream()
                .filter(mandatoryItem -> inventoryItems.stream()
                        .anyMatch(item -> item.equalsIgnoreCase(mandatoryItem)))
                .count();

        progress = (double) matchingItems / mandatoryItems.size();
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
   * This method is used to set the current passage.
   *
   * @return the current passage as a string
   */
  public String setupCurrentPassage() {
    return game.getCurrentPassage().getContent();
  }

  /**
   * Gets current passage.
   * This method is used to get the current passage.
   *
   * @return the current passage
   */
  public Passage getCurrentPassage() {
    return game.getCurrentPassage();
  }

  /**
   * Gets game.
   * This method is used to get the game.
   *
   * @return the game that is played
   */
  public Game getGame() {
    return game;
  }
  public static void resetGoals() {
    goals.clear();
  }

  /**
   * Go.
   * This method is used to go to the next passage.
   * It is used to go to the next passage when a link is clicked.
   * It also updates the UI with the next passage.
   *
   * @param link     the link that is clicked
   * @param textFlow the text flow that is updated with the next passage
   */
  public void go(Link link, TextFlow textFlow) {
    Passage nextPassage = game.go(link);
    mainGameView.stopTimeline();
    game.setCurrentPassage(nextPassage);
    mainGameView.updateUIWithPassage(textFlow, nextPassage);
  }
}
