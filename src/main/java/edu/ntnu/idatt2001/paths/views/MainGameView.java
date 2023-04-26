package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.GameController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.actions.Action;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class MainGameView extends View{

  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  private GameController gameController = GameController.getInstance();
  private Game game;
  private Passage currentPassage;
  private Player player;
  private List<Goal> goals;
  private TextArea passageContent;
  private Label playerHealthLabel;
  private Label playerScoreLabel;
  private Label playerInventoryLabel;
  private Label playerGoldLabel;

  public MainGameView(ScreenController screenController) {
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
    game = gameController.getGame();
    player = game.getPlayer();
    goals = game.getGoals();

    passageContent = new TextArea();
    passageContent.setWrapText(true);
    passageContent.setEditable(false);

    TextFlow textFlow = new TextFlow(passageContent);
    textFlow.setStyle("-fx-border-color: black; -fx-border-radius: 10;");

    ScrollPane scrollPane = new ScrollPane(textFlow);
    scrollPane.setFitToWidth(true);
    scrollPane.setMaxWidth(500);
    scrollPane.setPrefViewportHeight(200);
    scrollPane.setStyle("-fx-padding: 0; -fx-background-color: transparent;");

    textFlow.setOnMouseClicked(event -> {
      Pair<Timeline, Passage> timelineAndPassage = (Pair<Timeline, Passage>) textFlow.getUserData();
      Timeline timeline = timelineAndPassage.getKey();
      Passage passage = timelineAndPassage.getValue();
      if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
        timeline.stop();
        String[] displayedWords = passageContent.getText().split("\\s+");
        String[] allWords = passage.getContent().split("\\s+");
        String remainingText = String.join(" ", Arrays.copyOfRange(allWords, displayedWords.length, allWords.length));
        passageContent.appendText(remainingText);
      }
    });

    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    stackPane.getChildren().add(scrollPane);
    stackPane.setAlignment(scrollPane, Pos.CENTER);

    borderPane.setCenter(stackPane);

    HBox buttonsBox = new HBox();
    borderPane.setBottom(buttonsBox);

    setupTopBar();

    textFlow.setUserData(updateUIWithPassage(textFlow, game.begin()));
  }


  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }

  private Pair<Timeline, Passage> updateUIWithPassage(TextFlow textFlow, Passage passage) {
    passageContent.clear();
    updatePlayerAttributes();

    String[] words = passage.getContent().split("\\s+");

    Timeline timeline = new Timeline();
    timeline.getKeyFrames().clear();
    for (int i = 0; i < words.length; i++) {
      int wordIndex = i;
      timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), event -> {
        passageContent.appendText(words[wordIndex] + " ");
      }));
    }
    timeline.play();

    HBox buttonsBox = (HBox) borderPane.getBottom();
    buttonsBox.getChildren().clear();

    for (Link link : passage.getLinks()) {
      Button button = new Button(link.getText());
      button.setOnAction(event -> {
        Passage nextPassage = game.go(link);
        timeline.stop();
        updateUIWithPassage(textFlow, nextPassage);
      });
      buttonsBox.getChildren().add(button);
    }

    textFlow.setUserData(new Pair<>(timeline, passage)); // Store the Pair object in userData
    return new Pair<>(timeline, passage);
  }

  private void setupTopBar() {
    HBox attributesBox = new HBox();
    attributesBox.setAlignment(Pos.TOP_LEFT);
    attributesBox.setPadding(new Insets(10, 10, 10, 10));

    playerHealthLabel = new Label("Health: " + player.getHealth());
    playerHealthLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerHealthLabel.setPadding(new Insets(10, 10, 10, 10));

    playerGoldLabel = new Label("Gold: " + player.getGold());
    playerGoldLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerGoldLabel.setPadding(new Insets(10, 10, 10, 10));

    playerScoreLabel = new Label("Score: " + player.getScore());
    playerScoreLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerScoreLabel.setPadding(new Insets(10, 10, 10, 10));

    playerInventoryLabel = new Label("Inventory: " + player.getInventory());
    playerInventoryLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    playerInventoryLabel.setPadding(new Insets(10, 10, 10, 10));

    Button questionButton = new Button("Question");

    Button exitButton = new Button("Exit");

    HBox topRightBox = new HBox();
    topRightBox.setAlignment(Pos.TOP_RIGHT);
    topRightBox.setPadding(new Insets(10, 10, 10, 10));
    topRightBox.getChildren().addAll(questionButton, exitButton);

    attributesBox.getChildren().addAll(playerHealthLabel, playerGoldLabel, playerScoreLabel, playerInventoryLabel);

    HBox topBox = new HBox();
    topBox.getChildren().addAll(attributesBox, topRightBox);
    topBox.setHgrow(attributesBox, Priority.ALWAYS);

    borderPane.setTop(topBox);
  }



  private void updatePlayerAttributes() {
    playerHealthLabel = new Label("Health: " + player.getHealth());
    playerGoldLabel = new Label("Gold: " + player.getGold());
    playerScoreLabel = new Label("Score: " + player.getScore());
    playerInventoryLabel = new Label("Inventory: " + player.getInventory());
  }
}
