package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static edu.ntnu.idatt2001.paths.models.player.Difficulty.*;


/**
 * The type New game controller.
 * The class is used to control the behavior of the NewGameView.
 *
 * @author Helle R. and Sander S.
 * @version 1.0 20.05.2023
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
   * The language controller.
   * The languageController is used to get the translation of the strings.
   */
  LanguageController languageController = LanguageController.getInstance();
  /**
   * The easy string.
   * The EASY_STRING is used to store the translation of the easy difficulty.
   */
  private String EASY_STRING;

  /**
   * The hard string.
   * The HARD_STRING is used to store the translation of the hard difficulty.
   */
  private String HARD_STRING;
  /**
   * The custom string.
   * The CUSTOM_STRING is used to store the translation of the custom difficulty.
   */
  private String CUSTOM_STRING;

  private static final String RIGHT = "RIGHT";

  /**
   * Setup strings.
   * The method is used to get the translation of the strings.
   */
  public void setupStrings() {
    EASY_STRING = languageController.getTranslation(Dictionary.EASY.getKey());
    HARD_STRING = languageController.getTranslation(Dictionary.HARD.getKey());
    CUSTOM_STRING = languageController.getTranslation(Dictionary.CUSTOM.getKey());
  }

  /**
   * Update player.
   * The method is used to update the player.
   * The method is used to define the stats of the player.
   *
   * @param name            the name of the player
   * @param selectedToggle  the selected toggle button for the difficulty
   * @param activeCharacter the active character of the player
   * @param textFieldHealth the text field health of the player
   * @param textFieldGold   the text field gold of the player
   */
  public void updatePlayer(String name, Toggle selectedToggle, String activeCharacter,
                           TextField textFieldHealth, TextField textFieldGold) {
    String toggleText = ((ToggleButton) selectedToggle).getText();
    if (toggleText.equals(CUSTOM_STRING)) {
      player = playerController.addCustomPlayer(name, Integer.parseInt(textFieldHealth.getText()),
          Integer.parseInt(textFieldGold.getText()), activeCharacter);
    } else if (toggleText.equals(EASY_STRING)) {
      player = playerController.addDefaultPlayer(name, EASY, activeCharacter);
    } else if (toggleText.equals(HARD_STRING)) {
      player = playerController.addDefaultPlayer(name, HARD, activeCharacter);
    } else {
      player = playerController.addDefaultPlayer(name, STANDARD, activeCharacter);
    }
  }

  /**
   * Swap character.
   * The method is used to swap the character.
   * The method is used to change the character of the player.
   *
   * @param right the right button
   * @param left the left button
   * @param characterMale the imageview for the male character
   * @param characterFemale the imageview for the female character
   */
  public void swapCharacter(Button right, Button left, ImageView characterMale,
                            ImageView characterFemale) {
    right.setOnAction(e -> {
      ImageView current = characterMale.isVisible() ? characterMale : characterFemale;
      ImageView next = characterMale.isVisible() ? characterFemale : characterMale;
      swapImages(current, next, RIGHT);
    });

    left.setOnAction(e -> {
      ImageView current = characterMale.isVisible() ? characterMale : characterFemale;
      ImageView next = characterMale.isVisible() ? characterFemale : characterMale;
      swapImages(current, next, "LEFT");
    });
  }

  /**
   * Swap images.
   * The method is used to swap the images.
   * The method is used to change the image of the character.
   *
   * @param current the current imageview
   * @param next the next imageview
   * @param direction the direction of the swap
   */
  private void swapImages(ImageView current, ImageView next, String direction) {
    double currentWidth = current.getFitWidth();

    TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), current);
    slideOut.setFromX(0);
    slideOut.setToX(direction.equals(RIGHT) ? -currentWidth : currentWidth);

    TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), next);
    slideIn.setFromX(direction.equals(RIGHT) ? currentWidth : -currentWidth);
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