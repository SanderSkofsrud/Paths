package edu.ntnu.idatt2001.paths.frontend;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class NewGameView extends View {
  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  public NewGameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }
  public Pane getPane() {
    return this.borderPane;
  }

  public void setup() {
    ImageView imageView = new ImageView(new Image("create.png"));

    Label labelName = new Label("Select name");
    TextField textFieldName = new TextField();
    textFieldName.setPromptText("Enter name");
    HBox hBoxName = new HBox();
    hBoxName.getChildren().addAll(labelName, textFieldName);
    hBoxName.setSpacing(10);
    hBoxName.setAlignment(Pos.CENTER);
    hBoxName.setPadding(new Insets(10, 10, 10, 10));

    Label labelDifficulty = new Label("Select difficulty");
    ToggleGroup toggleGroupDifficulty = new ToggleGroup();
    ToggleButton easy = new ToggleButton("Easy");
    ToggleButton medium = new ToggleButton("Medium");
    ToggleButton hard = new ToggleButton("Hard");
    ToggleButton custom = new ToggleButton("Custom");
    easy.setToggleGroup(toggleGroupDifficulty);
    medium.setToggleGroup(toggleGroupDifficulty);
    hard.setToggleGroup(toggleGroupDifficulty);
    custom.setToggleGroup(toggleGroupDifficulty);
    HBox hBoxDifficulty = new HBox();
    hBoxDifficulty.getChildren().addAll(labelDifficulty, easy, medium, hard, custom);
    hBoxDifficulty.setSpacing(10);
    hBoxDifficulty.setAlignment(Pos.CENTER);
    hBoxDifficulty.setPadding(new Insets(10, 10, 10, 10));

    Label labelHealth = new Label("Select health (Normal difficulty = 100)");
    TextField textFieldHealth = new TextField();
    textFieldHealth.setPromptText("Enter health");
    HBox hBoxHealth = new HBox();
    hBoxHealth.getChildren().addAll(labelHealth, textFieldHealth);
    hBoxHealth.setSpacing(10);
    hBoxHealth.setAlignment(Pos.CENTER);
    hBoxHealth.setPadding(new Insets(10, 10, 10, 10));
    hBoxHealth.setVisible(false);

    Label labelGold = new Label("Select gold (Normal difficulty = 100)");
    TextField textFieldGold = new TextField();
    textFieldGold.setPromptText("Enter gold");
    HBox hBoxGold = new HBox();
    hBoxGold.getChildren().addAll(labelGold, textFieldGold);
    hBoxGold.setSpacing(10);
    hBoxGold.setAlignment(Pos.CENTER);
    hBoxGold.setPadding(new Insets(10, 10, 10, 10));
    hBoxGold.setVisible(false);

    toggleGroupDifficulty.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (toggleGroupDifficulty.getSelectedToggle() != null) {
        if (toggleGroupDifficulty.getSelectedToggle().equals(custom)) {
          hBoxHealth.setVisible(true);
          hBoxGold.setVisible(true);
        } else {
          hBoxHealth.setVisible(false);
          hBoxGold.setVisible(false);
        }
      }
    });

    Label labelCharacter = new Label("Select character model");
    Image male = new Image("male.png");
    Image female = new Image("female.png");
    ImageView characterMale = new ImageView(male);
    ImageView characterFemale = new ImageView(female);
    characterFemale.setVisible(false);
    characterMale.setFitHeight(100);
    characterMale.setFitWidth(100);
    characterFemale.setFitHeight(100);
    characterFemale.setFitWidth(100);

    Button right = new Button(">");
    Button left = new Button("<");

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

    Button button = new Button("Create Character");
    button.setOnAction(e -> {
      if (textFieldName.getText().isEmpty()) {
        textFieldName.setPromptText("Enter name");
        return;
      } else if (toggleGroupDifficulty.getSelectedToggle() == null) {
        labelDifficulty.setText("Select difficulty");
        return;
      } else if (toggleGroupDifficulty.getSelectedToggle().equals(custom) && (textFieldHealth.getText().isEmpty() || textFieldGold.getText().isEmpty())) {
        labelHealth.setText("Select health (Normal difficulty = 100)");
        labelGold.setText("Select gold (Normal difficulty = 100)");
        return;
      }
      screenController.activate("ChooseGoals");
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(imageView, hBoxName, hBoxDifficulty, hBoxHealth, hBoxGold, hBoxCharacter, button);
    vBox.setAlignment(Pos.CENTER);
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(10, 10, 10, 10));

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(vBox);

    Image background = new Image("background.png");
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    stackPane.setBackground(new Background(backgroundImage));

    stackPane.getChildren().add(borderPane);
  }

  public void resetPane() {
    stackPane.getChildren().clear();
  }

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
