package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
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
   * The Player controller.
   * The playerController is used to get the player.
   */
  PlayerController playerController = PlayerController.getInstance();
  LanguageController languageController = LanguageController.getInstance();
  NewGameController newGameController = new NewGameController();
  private String EASY_STRING;
  private String STANDARD_STRING ;
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

    Label labelInventory = new Label();
    TextField textFieldInventory = new TextField();
    textFieldInventory.setPromptText(languageController.getTranslation(Dictionary.ENTER_GOLD.getKey()));
    HBox hBoxInventory = new HBox();
    hBoxInventory.getChildren().addAll(labelInventory);
    hBoxInventory.setSpacing(10);
    hBoxInventory.setAlignment(Pos.CENTER);
    hBoxInventory.setPadding(new Insets(10, 10, 10, 10));
    hBoxInventory.setVisible(false);

    toggleGroupDifficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (toggleGroupDifficulty.getSelectedToggle() != null) {
        ToggleButton selectedButton = (ToggleButton) toggleGroupDifficulty.getSelectedToggle();
        String difficulty = selectedButton.getText();

        if (difficulty.equals(CUSTOM_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.SELECT_HEALTH.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.SELECT_GOLD.getKey()));
          hBoxHealth.getChildren().add(textFieldHealth);
          hBoxGold.getChildren().add(textFieldGold);
          hBoxGold.setAlignment(Pos.CENTER);
          hBoxHealth.setVisible(true);
          hBoxGold.setVisible(true);
          hBoxInventory.setVisible(false);
        } else if (difficulty.equals(EASY_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_EASY.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.GOLD_EASY.getKey()));
          labelInventory.setText(languageController.getTranslation(Dictionary.INVENTORY_EASY.getKey()));
          hBoxHealth.getChildren().remove(textFieldHealth);
          hBoxGold.getChildren().remove(textFieldGold);
          hBoxInventory.getChildren().remove(textFieldInventory);
          hBoxGold.setAlignment(Pos.CENTER);
          hBoxHealth.setVisible(true);
          hBoxGold.setVisible(true);
          hBoxInventory.setVisible(true);
        } else if (difficulty.equals(HARD_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_HARD.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.GOLD_HARD.getKey()));
          hBoxHealth.getChildren().remove(textFieldHealth);
          hBoxGold.getChildren().remove(textFieldGold);
          hBoxGold.setAlignment(Pos.CENTER);
          hBoxHealth.setVisible(true);
          hBoxGold.setVisible(true);
          hBoxInventory.setVisible(false);
        } else if (difficulty.equals(STANDARD_STRING)) {
          labelHealth.setText(languageController.getTranslation(Dictionary.HEALTH_STANDARD.getKey()));
          labelGold.setText(languageController.getTranslation(Dictionary.GOLD_STANDARD.getKey()));
          labelInventory.setText(languageController.getTranslation(Dictionary.INVENTORY_STANDARD.getKey()));
          hBoxHealth.getChildren().remove(textFieldHealth);
          hBoxGold.getChildren().remove(textFieldGold);
          hBoxInventory.getChildren().remove(textFieldInventory);
          hBoxGold.setAlignment(Pos.CENTER);
          hBoxHealth.setVisible(true);
          hBoxGold.setVisible(true);
          hBoxInventory.setVisible(true);
        } else {
          hBoxHealth.setVisible(false);
          hBoxGold.setVisible(false);
          hBoxInventory.setVisible(false);
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

    newGameController.swapCharacter(right, left, characterMale, characterFemale);

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

      String activeCharacter = characterMale.isVisible() ? "m.png" : "f.png";
      playerController.setActiveCharacter(activeCharacter);

      Toggle selectedToggle = toggleGroupDifficulty.getSelectedToggle();
      newGameController.updatePlayer(textFieldName.getText(), selectedToggle, activeCharacter, textFieldHealth, textFieldGold);

      screenController.activate("ChooseGoals");
      resetPane();
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(imageView, hBoxName, hBoxDifficulty, hBoxHealth, hBoxGold, hBoxInventory, hBoxCharacter, button);
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
}
