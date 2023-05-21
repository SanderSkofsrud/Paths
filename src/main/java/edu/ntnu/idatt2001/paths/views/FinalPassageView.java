package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.*;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

/**
 * The type Final passage view.
 * The class is used to show the credits of the game.
 * It is also used to show the goals achieved and failed.
 *
 * @author Helle R. and Sander S.
 * @version 0.2 21.05.2023
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
   * The Credits duration.
   * The credits duration is the duration of the credits in seconds.
   */
  private final double CREDITS_DURATION = 40;
  /**
   * The Screen controller.
   * The screenController is used to switch between the different views of the GUI.
   */
  private ScreenController screenController;
  /**
   * The Game controller.
   */
  GameController gameController = GameController.getInstance();
  /**
   * The Language controller.
   */
  LanguageController languageController = LanguageController.getInstance();
  /**
   * The Final passage controller.
   */
  FinalPassageController finalPassageController = new FinalPassageController();

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
   * The method is used to setup the GUI.
   * It creates the different Texts and VBoxes of the GUI.
   * It also creates the animation of the credits.
   * The method is called when the GUI is created.
   */
  @Override
  public void setup() {
    VBox vbox = new VBox();
    vbox.setAlignment(Pos.CENTER); // Center the VBox content
    vbox.setSpacing(25);

    Text congratsText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_1.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_1_1.getKey()));
    congratsText.setId("creditsHeader");

    Text goalsTitle = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_2.getKey()));
    goalsTitle.setId("creditsHeader");

    Text goalsAchieved = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_2_1.getKey()));
    goalsAchieved.setId("creditsText");

    VBox goalsVBox = finalPassageController.goalsAchieved();

    Text goalsFailed = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_2_2.getKey()));
    goalsFailed.setId("creditsText");

    VBox goalsFailedVBox = finalPassageController.goalsFailed();

    TextFlow goalPercentage = finalPassageController.percentageOfGoalsCompleted();

    Text createdByText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_3.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDIT_TEXT_3_1.getKey()));
    createdByText.setId("creditsText");

    Text specialThanksText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_4.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_4_1.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_4_2.getKey()));
    specialThanksText.setId("creditsText");

    Text sourcesText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_5.getKey())
    + "\n\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_5_1.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_5_2.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_5_3.getKey()));
    sourcesText.setId("creditsText");

    Text imagesText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_6.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_6_1.getKey())
    + "\n" + languageController.getTranslation(Dictionary.CREDITS_TEXT_6_2.getKey()));
    imagesText.setId("creditsText");

    Text thankYouText = new Text(languageController.getTranslation(Dictionary.CREDITS_TEXT_7.getKey()));
    thankYouText.setId("creditsText");

    vbox.getChildren().addAll(
            congratsText,
            goalsTitle,
            goalsAchieved,
            goalsVBox,
            goalsFailed,
            goalsFailedVBox,
            goalPercentage,
            createdByText,
            specialThanksText,
            sourcesText,
            imagesText,
            thankYouText
    );

    stackPane.getChildren().add(vbox);

    vbox.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
      TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(CREDITS_DURATION), vbox);
      translateTransition.setFromY(stackPane.getHeight());
      translateTransition.setToY(-newValue.getHeight());
      translateTransition.setInterpolator(Interpolator.LINEAR);
      translateTransition.setCycleCount(1);

      translateTransition.setOnFinished(event -> screenController.activate("MainMenu"));

      Platform.runLater(translateTransition::play);
    });

    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, true));
    stackPane.setBackground(new Background(backgroundImage));
    stackPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * The method is used to reset the pane of the GUI.
   */
  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
