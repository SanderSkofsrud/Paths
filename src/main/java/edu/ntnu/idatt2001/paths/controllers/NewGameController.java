package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.animation.TranslateTransition;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static edu.ntnu.idatt2001.paths.models.player.Difficulty.*;

/**
 * The type New game controller.
 * The class is used to control the behavior of the NewGameView.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class NewGameController {
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
  private String EASY_STRING;
  private String STANDARD_STRING;
  private String HARD_STRING;
  private String CUSTOM_STRING;

  public void setupStrings() {
    EASY_STRING = languageController.getTranslation(Dictionary.EASY.getKey());
    STANDARD_STRING = languageController.getTranslation(Dictionary.STANDARD.getKey());
    HARD_STRING = languageController.getTranslation(Dictionary.HARD.getKey());
    CUSTOM_STRING = languageController.getTranslation(Dictionary.CUSTOM.getKey());
  }

  public void updatePlayer(String name, Toggle selectedToggle, String activeCharacter, TextField textFieldHealth, TextField textFieldGold) {
    String toggleText = ((ToggleButton) selectedToggle).getText();
    if (toggleText.equals(CUSTOM_STRING)) {
      player = playerController.addCustomPlayer(name, Integer.parseInt(textFieldHealth.getText()), Integer.parseInt(textFieldGold.getText()), activeCharacter);
    } else if (toggleText.equals(EASY_STRING)) {
      player = playerController.addDefaultPlayer(name, EASY, activeCharacter);
    } else if (toggleText.equals(HARD_STRING)) {
      player = playerController.addDefaultPlayer(name, HARD, activeCharacter);
    } else {
      player = playerController.addDefaultPlayer(name, STANDARD, activeCharacter);
    }
  }


  public void swapCharacter(Button right, Button left, ImageView characterMale, ImageView characterFemale) {
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