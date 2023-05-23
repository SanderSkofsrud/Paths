package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.LanguageController;
import edu.ntnu.idatt2001.paths.controllers.NewGameController;
import edu.ntnu.idatt2001.paths.controllers.PlayerController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.ShowAlert;
import edu.ntnu.idatt2001.paths.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * The type New game view.
 * The class is used to create the view of the GUI where the player can create a new game.
 * The class extends the View class.
 *
 * @author Helle R. and Sander S.
 * @version 1.2 19.05.2023
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
   * The Player controller.
   * The playerController is used to get the player.
   */
  PlayerController playerController = PlayerController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
  NewGameController newGameController = new NewGameController();
  private String EASY_STRING;
  private String STANDARD_STRING;
  private String HARD_STRING;
  private String CUSTOM_STRING;

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
    newGameController.setupStrings();
    EASY_STRING = languageController.getTranslation(Dictionary.EASY.getKey());
    STANDARD_STRING = languageController.getTranslation(Dictionary.STANDARD.getKey());
    HARD_STRING = languageController.getTranslation(Dictionary.HARD.getKey());
    CUSTOM_STRING = languageController.getTranslation(Dictionary.CUSTOM.getKey());

    Label labelName = new Label(languageController.getTranslation(Dictionary.SELECT_NAME.getKey()));
    TextField textFieldName = new TextField();
    textFieldName.setPromptText(languageController.getTranslation(Dictionary.ENTER_NAME.getKey()));
    HBox hboxName = new HBox();
    hboxName.getChildren().addAll(labelName, textFieldName);
    hboxName.setSpacing(10);
    hboxName.setAlignment(Pos.CENTER);
    hboxName.setPadding(new Insets(10, 10, 10, 10));

    Label labelDifficulty = new Label(languageController
        .getTranslation(Dictionary.SELECT_DIFFICULTY.getKey()));
    ToggleGroup toggleGroupDifficulty = new ToggleGroup();
    ToggleButton easy = new ToggleButton(EASY_STRING);
    ToggleButton standard = new ToggleButton(STANDARD_STRING);
    ToggleButton hard = new ToggleButton(HARD_STRING);
    ToggleButton custom = new ToggleButton(CUSTOM_STRING);
    easy.setToggleGroup(toggleGroupDifficulty);
    standard.setToggleGroup(toggleGroupDifficulty);
    hard.setToggleGroup(toggleGroupDifficulty);
    custom.setToggleGroup(toggleGroupDifficulty);
    easy.setId("subMenuButton");
    standard.setId("subMenuButton");
    hard.setId("subMenuButton");
    custom.setId("subMenuButton");
    HBox hboxDifficulty = new HBox();
    hboxDifficulty.getChildren().addAll(labelDifficulty, easy, standard, hard, custom);
    hboxDifficulty.setSpacing(10);
    hboxDifficulty.setAlignment(Pos.CENTER);
    hboxDifficulty.setPadding(new Insets(10, 10, 10, 10));


    Label labelHealth = new Label();
    TextField textFieldHealth = new TextField();
    textFieldHealth.setPromptText(languageController.getTranslation(Dictionary
        .ENTER_HEALTH.getKey()));
    HBox hboxHealth = new HBox();
    hboxHealth.getChildren().addAll(labelHealth);
    hboxHealth.setSpacing(10);
    hboxHealth.setAlignment(Pos.CENTER);
    hboxHealth.setPadding(new Insets(10, 10, 10, 10));
    hboxHealth.setVisible(false);

    Label labelGold = new Label();
    TextField textFieldGold = new TextField();
    textFieldGold.setPromptText(languageController.getTranslation(Dictionary
        .ENTER_GOLD.getKey()));
    HBox hboxGold = new HBox();
    hboxGold.getChildren().addAll(labelGold);
    hboxGold.setSpacing(10);
    hboxGold.setAlignment(Pos.CENTER);
    hboxGold.setPadding(new Insets(10, 10, 10, 10));
    hboxGold.setVisible(false);

    Label labelInventory = new Label();
    TextField textFieldInventory = new TextField();
    textFieldInventory.setPromptText(languageController.getTranslation(Dictionary
        .ENTER_GOLD.getKey()));
    HBox hboxInventory = new HBox();
    hboxInventory.getChildren().addAll(labelInventory);
    hboxInventory.setSpacing(10);
    hboxInventory.setAlignment(Pos.CENTER);
    hboxInventory.setPadding(new Insets(10, 10, 10, 10));
    hboxInventory.setVisible(false);

    toggleGroupDifficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (toggleGroupDifficulty.getSelectedToggle() != null) {
        ToggleButton selectedButton = (ToggleButton) toggleGroupDifficulty.getSelectedToggle();
        String difficulty = selectedButton.getText();

        if (difficulty.equals(CUSTOM_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.SELECT_HEALTH.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.SELECT_GOLD.getKey()));
          hboxHealth.getChildren().add(textFieldHealth);
          hboxGold.getChildren().add(textFieldGold);
          hboxGold.setAlignment(Pos.CENTER);
          hboxHealth.setVisible(true);
          hboxGold.setVisible(true);
          hboxInventory.setVisible(false);
        } else if (difficulty.equals(EASY_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_EASY.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.GOLD_EASY.getKey()));
          labelInventory.setText(languageController.getTranslation(Dictionary
              .INVENTORY_EASY.getKey()));
          hboxHealth.getChildren().remove(textFieldHealth);
          hboxGold.getChildren().remove(textFieldGold);
          hboxInventory.getChildren().remove(textFieldInventory);
          hboxGold.setAlignment(Pos.CENTER);
          hboxHealth.setVisible(true);
          hboxGold.setVisible(true);
          hboxInventory.setVisible(true);
        } else if (difficulty.equals(HARD_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_HARD.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.GOLD_HARD.getKey()));
          hboxHealth.getChildren().remove(textFieldHealth);
          hboxGold.getChildren().remove(textFieldGold);
          hboxGold.setAlignment(Pos.CENTER);
          hboxHealth.setVisible(true);
          hboxGold.setVisible(true);
          hboxInventory.setVisible(false);
        } else if (difficulty.equals(STANDARD_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary
              .HEALTH_STANDARD.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary
              .GOLD_STANDARD.getKey()));
          labelInventory.setText(languageController.getTranslation(Dictionary
              .INVENTORY_STANDARD.getKey()));
          hboxHealth.getChildren().remove(textFieldHealth);
          hboxGold.getChildren().remove(textFieldGold);
          hboxInventory.getChildren().remove(textFieldInventory);
          hboxGold.setAlignment(Pos.CENTER);
          hboxHealth.setVisible(true);
          hboxGold.setVisible(true);
          hboxInventory.setVisible(true);
        } else {
          hboxHealth.setVisible(false);
          hboxGold.setVisible(false);
          hboxInventory.setVisible(false);
        }

      }
    });


    Label labelCharacter = new Label(languageController.getTranslation(Dictionary
        .SELECT_CHARACTER.getKey()));
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

    newGameController.swapCharacter(right, left, characterMale, characterFemale);

    StackPane imageContainer = new StackPane(characterMale, characterFemale);

    HBox hboxCharacter = new HBox();
    hboxCharacter.getChildren().addAll(labelCharacter, left, imageContainer, right);
    hboxCharacter.setSpacing(10);
    hboxCharacter.setAlignment(Pos.CENTER);
    hboxCharacter.setPadding(new Insets(10, 10, 10, 10));


    Button button = null;
    button = new Button(languageController.getTranslation(Dictionary
        .CREATE_CHARACTER.getKey()));
    button.setId("subMenuButton");
    button.setOnAction(e -> {
      if (textFieldName.getText().isEmpty()) {
        textFieldName.setPromptText(languageController.getTranslation(Dictionary
            .ENTER_NAME.getKey()));
        return;
      } else if (toggleGroupDifficulty.getSelectedToggle() == null) {
        labelDifficulty.setText(languageController.getTranslation(Dictionary
            .SELECT_DIFFICULTY.getKey()));
        return;
      } else if (toggleGroupDifficulty.getSelectedToggle().equals(custom)) {
        try {
          int health = Integer.parseInt(textFieldHealth.getText());
          int gold = Integer.parseInt(textFieldGold.getText());
        if (health <= 0 || gold <= 0) {
            ShowAlert.showError(languageController.getTranslation(Dictionary
                .INVALID_INPUT.getKey()), languageController.getTranslation(Dictionary
                .GOAL_AND_HEALTH_VALUES_MUST_BE_NUMBERS_ABOVE_ZERO.getKey()));
            return;
          }

        } catch (NumberFormatException ex) {
          ShowAlert.showError(languageController.getTranslation(Dictionary
              .INVALID_INPUT.getKey()), languageController.getTranslation(Dictionary
              .GOAL_AND_HEALTH_VALUES_MUST_BE_NUMBERS_ABOVE_ZERO.getKey()));
          return;
        }
      }

      String activeCharacter = characterMale.isVisible() ? "m.png" : "f.png";
      playerController.setActiveCharacter(activeCharacter);

      Toggle selectedToggle = toggleGroupDifficulty.getSelectedToggle();
      newGameController.updatePlayer(textFieldName.getText(), selectedToggle,
          activeCharacter, textFieldHealth, textFieldGold);

      SoundPlayer.play("/sounds/confirm.wav");
      screenController.activate("ChooseGoals");
      resetPane();
    });

    ImageView imageView = new ImageView(new Image(getClass()
            .getResourceAsStream("/images/create.png")));

    VBox vBox = new VBox();
    vBox.getChildren().addAll(imageView, hboxName, hboxDifficulty, hboxHealth,
        hboxGold, hboxInventory, hboxCharacter, button);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10, 10, 10, 10));

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(vBox);

    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1.0, 1.0, true, true, false, true));
    stackPane.setBackground(new Background(backgroundImage));

    stackPane.getChildren().add(borderPane);
    stackPane.getStylesheets().add("stylesheet.css");

    ImageView backImage = new ImageView(new Image(getClass()
        .getResourceAsStream("/images/back.png")));
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
}
