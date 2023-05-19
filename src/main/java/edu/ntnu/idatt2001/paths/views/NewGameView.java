package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.GameData;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import static edu.ntnu.idatt2001.paths.models.player.Difficulty.*;

/**
 * The type New game view.
 * The class is used to create the view of the GUI where the player can create a new game.
 * The class extends the View class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class NewGameView extends View {
  /**
   * The Border pane.
   * The borderPane is the main pane of the view.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   * The stackPane is used to stack the different panes of the view.
   */
  protected StackPane stackPane;
  /**
   * The Screen controller.
   * The screenController is used to switch between the different views of the GUI.
   */
  private ScreenController screenController;
  /**
   * The Player.
   * The player is used to store the player.
   */
  Player player;
  /**
   * The Player controller.
   * The playerController is used to get the player.
   */
  PlayerController playerController = PlayerController.getInstance();
  /**
   * The File handler controller.
   * The fileHandlerController is used to save the game.
   */
  FileHandlerController fileHandlerController = FileHandlerController.getInstance();
  /**
   * The Game controller.
   * The gameController is used to create a new game.
   */
  GameController gameController = GameController.getInstance();
  LanguageController languageController = LanguageController.getInstance();

  /**
   * Instantiates a new New game view.
   *
   * @param screenController the screen controller
   */
  public NewGameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }
  /**
   * Gets pane.
   * The method is used to get the pane of the view.
   *
   * @return the pane
   */
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Setup.
   * The method is used to setup the view.
   * The method is used to create the view of the GUI where the player can create a new game.
   */
  public void setup() {
    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/create.png")));

    Label labelName = new Label(languageController.getTranslation(Dictionary.SELECT_NAME.getKey()));
    TextField textFieldName = new TextField();
    textFieldName.setPromptText(languageController.getTranslation(Dictionary.ENTER_NAME.getKey()));
    HBox hBoxName = new HBox();
    hBoxName.getChildren().addAll(labelName, textFieldName);
    hBoxName.setSpacing(10);
    hBoxName.setAlignment(Pos.CENTER);
    hBoxName.setPadding(new Insets(10, 10, 10, 10));

    Label labelDifficulty = new Label(languageController.getTranslation(Dictionary.SELECT_DIFFICULTY.getKey()));
    ToggleGroup toggleGroupDifficulty = new ToggleGroup();
    ToggleButton easy = new ToggleButton(languageController.getTranslation(Dictionary.EASY.getKey()));
    ToggleButton standard = new ToggleButton(languageController.getTranslation(Dictionary.STANDARD.getKey()));
    ToggleButton hard = new ToggleButton(languageController.getTranslation(Dictionary.HARD.getKey()));
    ToggleButton custom = new ToggleButton(languageController.getTranslation(Dictionary.CUSTOM.getKey()));
    easy.setToggleGroup(toggleGroupDifficulty);
    standard.setToggleGroup(toggleGroupDifficulty);
    hard.setToggleGroup(toggleGroupDifficulty);
    custom.setToggleGroup(toggleGroupDifficulty);
    easy.setId("subMenuButton");
    standard.setId("subMenuButton");
    hard.setId("subMenuButton");
    custom.setId("subMenuButton");
    HBox hBoxDifficulty = new HBox();
    hBoxDifficulty.getChildren().addAll(labelDifficulty, easy, standard, hard, custom);
    hBoxDifficulty.setSpacing(10);
    hBoxDifficulty.setAlignment(Pos.CENTER);
    hBoxDifficulty.setPadding(new Insets(10, 10, 10, 10));


    Label labelHealth = new Label();
    TextField textFieldHealth = new TextField();
    textFieldHealth.setPromptText(languageController.getTranslation(Dictionary.ENTER_HEALTH.getKey()));
    HBox hBoxHealth = new HBox();
    hBoxHealth.getChildren().addAll(labelHealth);
    hBoxHealth.setSpacing(10);
    hBoxHealth.setAlignment(Pos.CENTER);
    hBoxHealth.setPadding(new Insets(10, 10, 10, 10));
    hBoxHealth.setVisible(false);

    Label labelGold = new Label();
    TextField textFieldGold = new TextField();
    textFieldGold.setPromptText(languageController.getTranslation(Dictionary.ENTER_GOLD.getKey()));
    HBox hBoxGold = new HBox();
    hBoxGold.getChildren().addAll(labelGold);
    hBoxGold.setSpacing(10);
    hBoxGold.setAlignment(Pos.CENTER);
    hBoxGold.setPadding(new Insets(10, 10, 10, 10));
    hBoxGold.setVisible(false);

    toggleGroupDifficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (toggleGroupDifficulty.getSelectedToggle() != null) {
        ToggleButton selectedButton = (ToggleButton) toggleGroupDifficulty.getSelectedToggle();
        String difficulty = selectedButton.getText();

        switch (difficulty) {
          case "Custom" -> {
            labelHealth.setText(languageController.getTranslation(Dictionary.SELECT_HEALTH.getKey()));
            labelGold.setText(languageController.getTranslation(Dictionary.SELECT_GOLD.getKey()));
            hBoxHealth.getChildren().add(textFieldHealth);
            hBoxGold.getChildren().add(textFieldGold);
            hBoxGold.setAlignment(Pos.CENTER);
            hBoxHealth.setVisible(true);
            hBoxGold.setVisible(true);
          }
          case "Easy" -> {
            labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_EASY.getKey()));
            labelGold.setText(languageController.getTranslation(Dictionary.GOLD_EASY.getKey()));
            hBoxHealth.getChildren().remove(textFieldHealth);
            hBoxGold.getChildren().remove(textFieldGold);
            hBoxGold.setAlignment(Pos.CENTER);
            hBoxHealth.setVisible(true);
            hBoxGold.setVisible(true);
          }
          case "Hard" -> {
            labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_HARD.getKey()));
            labelGold.setText(languageController.getTranslation(Dictionary.GOLD_HARD.getKey()));
            hBoxHealth.getChildren().remove(textFieldHealth);
            hBoxGold.getChildren().remove(textFieldGold);
            hBoxGold.setAlignment(Pos.CENTER);
            hBoxHealth.setVisible(true);
            hBoxGold.setVisible(true);
          }
          case "Standard" -> {
            labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_STANDARD.getKey()));
            labelGold.setText(languageController.getTranslation(Dictionary.GOLD_STANDARD.getKey()));
            hBoxHealth.getChildren().remove(textFieldHealth);
            hBoxGold.getChildren().remove(textFieldGold);
            hBoxGold.setAlignment(Pos.CENTER);
            hBoxHealth.setVisible(true);
            hBoxGold.setVisible(true);
          }
          default -> {
            hBoxHealth.setVisible(false);
            hBoxGold.setVisible(false);
          }
        }
      }
    });


    Label labelCharacter = new Label(languageController.getTranslation(Dictionary.SELECT_CHARACTER.getKey()));
    Image male = new Image(getClass().getResourceAsStream("/images/m.png"));
    Image female = new Image(getClass().getResourceAsStream("/images/f.png"));
    ImageView characterMale = new ImageView(male);
    ImageView characterFemale = new ImageView(female);
    characterFemale.setVisible(false);
    characterMale.setFitHeight(100);
    characterMale.setFitWidth(100);
    characterFemale.setFitHeight(100);
    characterFemale.setFitWidth(100);

    Button right = new Button(">");
    Button left = new Button("<");
    right.setId("squareButton");
    left.setId("squareButton");

    right.setOnAction(e -> {
      ImageView current = characterMale.isVisible() ? characterMale : characterFemale;
      ImageView next = characterMale.isVisible() ? characterFemale : characterMale;
      swapImages(current, next, "RIGHT");
    });

    left.setOnAction(e -> {
      ImageView current = characterMale.isVisible() ? characterMale : characterFemale;
      ImageView next = characterMale.isVisible() ? characterFemale : characterMale;
      swapImages(current, next, "LEFT");
    });

    StackPane imageContainer = new StackPane(characterMale, characterFemale);

    HBox hBoxCharacter = new HBox();
    hBoxCharacter.getChildren().addAll(labelCharacter, left, imageContainer, right);
    hBoxCharacter.setSpacing(10);
    hBoxCharacter.setAlignment(Pos.CENTER);
    hBoxCharacter.setPadding(new Insets(10, 10, 10, 10));

    Button button = new Button(languageController.getTranslation(Dictionary.CREATE_CHARACTER.getKey()));
    button.setId("subMenuButton");
    button.setOnAction(e -> {
      if (textFieldName.getText().isEmpty()) {
        textFieldName.setPromptText(languageController.getTranslation(Dictionary.ENTER_NAME.getKey()));
        return;
      } else if (toggleGroupDifficulty.getSelectedToggle() == null) {
        labelDifficulty.setText(languageController.getTranslation(Dictionary.SELECT_DIFFICULTY.getKey()));
        return;
      }

      if (toggleGroupDifficulty.getSelectedToggle().equals(custom)) {
        player = playerController.addCustomPlayer(textFieldName.getText(), Integer.parseInt(textFieldHealth.getText()), Integer.parseInt(textFieldGold.getText()));
      }  else if (toggleGroupDifficulty.getSelectedToggle().equals(easy)) {
        player = playerController.addDefaultPlayer(textFieldName.getText(), EASY);
      } else if (toggleGroupDifficulty.getSelectedToggle().equals(hard)) {
        player = playerController.addDefaultPlayer(textFieldName.getText(),HARD);
      } else {
        player = playerController.addDefaultPlayer(textFieldName.getText(), STANDARD);
      }

      String activeCharacter = characterMale.isVisible() ? "m.png" : "f.png";
      playerController.setActiveCharacter(activeCharacter);

      GameData currentGameData = fileHandlerController.getCurrentGameData();
      if (currentGameData == null || currentGameData.getGoals().isEmpty()) {
        screenController.activate("ChooseGoals");
        resetPane();
        return;
      }

      System.out.printf("Player created: %s%n", player.toString());
      resetPane();

      Game game = new Game(player, currentGameData.getStory(), currentGameData.getGoals());
      fileHandlerController.saveGame(game.getStory(), player, game.getGoals(), activeCharacter);
      fileHandlerController.saveGameJson(player.getName(), game);
      gameController.setGame(game);
      screenController.activate("MainGame");
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(imageView, hBoxName, hBoxDifficulty, hBoxHealth, hBoxGold, hBoxCharacter, button);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10, 10, 10, 10));

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(vBox);

    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    stackPane.setBackground(new Background(backgroundImage));

    stackPane.getChildren().add(borderPane);
    stackPane.getStylesheets().add("stylesheet.css");

    ImageView backImage = new ImageView(new Image(getClass().getResourceAsStream("/images/back.png")));
    Button backButton = new Button();
    backButton.setId("seeThroughButton");
    backButton.setGraphic(backImage);
    backButton.setPadding(new Insets(10, 10, 10, 10));
    backButton.setOnAction(e -> {
      screenController.activate("MainMenu");
      resetPane();
    });

    borderPane.setTop(backButton);
    borderPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * Resets the pane.
   */
  public void resetPane() {
    stackPane.getChildren().clear();
  }

  /**
   * Swaps the images.
   *
   * @param current the current image
   * @param next    the next image
   * @param direction the direction
   */
  private void swapImages(ImageView current, ImageView next, String direction) {
    double currentWidth = current.getFitWidth();

    TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), current);
    slideOut.setFromX(0);
    slideOut.setToX(direction.equals("RIGHT") ? -currentWidth : currentWidth);

    TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), next);
    slideIn.setFromX(direction.equals("RIGHT") ? currentWidth : -currentWidth);
    slideIn.setToX(0);

    current.setVisible(true);
    next.setVisible(false);

    slideOut.play();
    slideIn.play();

    slideOut.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.greaterThanOrEqualTo(Duration.millis(100))) {
        current.setVisible(false);
        next.setVisible(true);
      }
    });
  }
}
