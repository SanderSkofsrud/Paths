package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.LanguageController;
import edu.ntnu.idatt2001.paths.controllers.PlayerController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.player.Player;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


/**
 * The type Minigame view.
 * The class is used to create the GUI of the minigame.
 * The minigame is a tic-tac-toe game.
 * The player plays against the computer.
 * The player is X and the computer is O.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class MinigameView extends View {
  /**
   * The Border pane.
   * The borderPane is the main pane of the GUI.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   * The stackPane is the pane where the gridPane is placed.
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
  /**
   * The button array.
   * The buttons array is used to store the buttons of the gridPane.
   * The buttons are used to play the game.
   */
  private Button[][] buttons = new Button[3][3];
  /**
   * The isFinished boolean.
   * The isFinished boolean is used to check if the game is finished.
   */
  private boolean isFinished = false;
  /**
   * The hasPlayed boolean.
   * The hasPlayed boolean is used to check if the player has played.
   */
  private static boolean hasPlayed = false;

  /**
   * Instantiates a new Minigame view.
   *
   * @param screenController the screen controller
   */
  public MinigameView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
    this.screenController = screenController;
  }

  /**
   * Gets pane.
   *
   * @return the pane
   */
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Setup.
   * The setup method is used to setup the GUI of the minigame.
   * The method is used to create the gridPane and the buttons.
   * The method is used to add the gridPane to the stackPane.
   * The method is used to add the stackPane to the borderPane.
   * The method is used to add the stylesheet to the borderPane.
   */
  @Override
  public void setup() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    setupInterface(gridPane);

    stackPane.getChildren().add(gridPane);
    borderPane.setCenter(stackPane);
    Image background = new Image(getClass().getResourceAsStream("/images/background.png"));
    BackgroundImage backgroundImage = new BackgroundImage(background,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1.0, 1.0, true, true, false, true));
    borderPane.setBackground(new Background(backgroundImage));
    borderPane.getStylesheets().add("stylesheet.css");
  }

  /**
   * Sets up the interface.
   * The method is used to setup the interface of the minigame.
   * The method is used to create the buttons of the gridPane.
   *
   * @param gridPane the grid pane
   */
  private void setupInterface(GridPane gridPane) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Button button = new Button(" ");
        button.setMinSize(100, 100);
        button.setOnAction(e -> playerMove(button));
        buttons[i][j] = button;
        gridPane.add(button, j, i);
      }
    }
  }

  /**
   * A method for the player to make a move.
   *
   * @param button the button the player clicks
   */
  private void playerMove(Button button) {
    if (!isFinished && button.getText().equals(" ")) {
      button.setText("X");
      button.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 30px;");
      button.setDisable(true);
      checkWinCondition("X");
      if (!isFinished) {
        computerMove();
      }
    }
  }

  /**
   * A method for the computer to make a move.
   * The method is used to make the computer make a move.
   * The method is used to check if the computer has won.
   */
  private void computerMove() {
    Random random = new Random();
    int i;
    int j;
    do {
      i = random.nextInt(3);
      j = random.nextInt(3);
    } while (!buttons[i][j].getText().equals(" "));
    buttons[i][j].setText("O");
    buttons[i][j].setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 30px;");
    buttons[i][j].setDisable(true);
    checkWinCondition("O");
  }

  /**
   * A method to check if the game is finished.
   * The method is used to check if the game is finished.
   *
   * @param player the player
   */
  private void checkWinCondition(String player) {
    // Check rows
    for (int i = 0; i < 3; i++) {
      if (buttons[i][0].getText().equals(player)
          && buttons[i][1].getText().equals(player)
          && buttons[i][2].getText().equals(player)) {
        isFinished = true;
        announceWinner(player);
        return;
      }
    }

    // Check columns
    for (int i = 0; i < 3; i++) {
      if (buttons[0][i].getText().equals(player)
          && buttons[1][i].getText().equals(player)
          && buttons[2][i].getText().equals(player)) {
        isFinished = true;
        announceWinner(player);
        return;
      }
    }

    // Check diagonal (top-left to bottom-right)
    if (buttons[0][0].getText().equals(player)
        && buttons[1][1].getText().equals(player)
        && buttons[2][2].getText().equals(player)) {
      isFinished = true;
      announceWinner(player);
      return;
    }

    // Check diagonal (top-right to bottom-left)
    if (buttons[0][2].getText().equals(player)
        && buttons[1][1].getText().equals(player)
        && buttons[2][0].getText().equals(player)) {
      isFinished = true;
      announceWinner(player);
      return;
    }

    // Check for a draw
    boolean isDraw = true;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (buttons[i][j].getText().equals(" ")) {
          isDraw = false;
          break;
        }
      }
      if (!isDraw) break;
    }

    if (isDraw) {
      announceDraw();
    }
  }

  /**
   * A method to announce the winner.
   * The method is used to announce the winner of the minigame.
   *
   * @param playerString the player string
   */
  private void announceWinner(String playerString) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(languageController.translate("Game Over"));
    alert.setHeaderText(null);
    Player player = playerController.getPlayer();
    if (playerString.equals("X")) {
      player.addGold(10);
      alert.setContentText(languageController.translate("You win! You gained 10 gold."));
    } else {
      player.addGold(-10);
      alert.setContentText(languageController.translate("You lost! You lost 10 gold."));
    }
    alert.showAndWait();
    hasPlayed = true;
    playerController.setPlayer(player);
    screenController.activate("MainGame");
  }

  /**
   * A method to announce a draw.
   * The method is used to announce a draw in the minigame.
   */
  private void announceDraw() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(languageController.translate("Game Over"));
    alert.setHeaderText(null);
    alert.setContentText(languageController.translate("It's a draw!"));
    alert.showAndWait();
    hasPlayed = true;
    screenController.activate("MainGame");
  }

  /**
   * A method to check if the minigame has been played.
   * If the minigame has been played, it cannot be played again.
   *
   * @return true, if successful
   */
  public static boolean hasPlayed() {
    return hasPlayed;
  }

  /**
   * A method to reset the pane.
   * The method is used to reset the pane.
   */
  @Override
  void resetPane() {
    stackPane.getChildren().clear();
    setup();
  }
}
