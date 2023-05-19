package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.*;
import edu.ntnu.idatt2001.paths.models.goals.*;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

import java.util.Arrays;
import java.util.List;

/**
 * The type Main game view.
 * The class is used to create the GUI of the game and to start the game.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class FinalPassageView extends View{
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

  /**
   * Instantiates a new Main game view.
   *
   * @param screenController the screen controller
   */
  public FinalPassageView(ScreenController screenController) {
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
    player = game.getPlayer();
    goals = game.getGoals();
    story = game.getStory();
    currentPassage = game.getCurrentPassage();

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

    System.out.println(playerController.getActiveCharacter());
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

    words = translatedContent.split("\\s+");

    timeline = new Timeline();
    timeline.getKeyFrames().clear();
    for (int i = 0; i < words.length; i++) {
      int wordIndex = i;
      timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), event -> {
        passageContent.appendText(words[wordIndex] + " ");
      }));
    }

    HBox buttonsBox = (HBox) borderPane.getBottom();
    buttonsBox.getChildren().clear();
    buttonsBox.setAlignment(Pos.CENTER);
    buttonsBox.setPadding(new Insets(10, 0, 100, 0));
    buttonsBox.setSpacing(10);

    Button button = new Button("Credtis:");
    buttonsBox.getChildren().add(button);
    button.setId("subMenuButton");

    textFlow.setUserData(new Pair<>(timeline, currentPassage)); // Store the Pair object in userData
    timeline.play();
    return new Pair<>(timeline, currentPassage);
  }

  /**
   * Sets up the top bar of the GUI.
   * The top bar contains the stats of the player,
   * as well as buttons to control the game.
   */
  private void setupTopBar(TextFlow textFlow) {
    attributesBox.getChildren().clear();

    Image soundImage = new Image(getClass().getResourceAsStream("/images/sound.png"));
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

    HBox topRightBox = new HBox();
    topRightBox.setAlignment(Pos.TOP_RIGHT);
    topRightBox.setPadding(new Insets(10, 10, 10, 10));
    topRightBox.getChildren().addAll(soundButton);

    attributesBox.getChildren().addAll(playerHealthLabel, playerGoldLabel, playerScoreLabel, inventoryBox);

    HBox topBox = new HBox();
    topBox.getChildren().addAll(attributesBox, topRightBox);
    topBox.setHgrow(attributesBox, Priority.ALWAYS);

    borderPane.setTop(topBox);
  }
}
