package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.GameController;
import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import edu.ntnu.idatt2001.paths.models.Game;
import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Player;
import edu.ntnu.idatt2001.paths.models.actions.Action;
import edu.ntnu.idatt2001.paths.models.goals.Goal;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
    passageContent = new TextArea();
    passageContent.setWrapText(true);
    passageContent.setEditable(false);
    borderPane.setCenter(passageContent);

    HBox buttonsBox = new HBox();
    borderPane.setBottom(buttonsBox);

    updateUIWithPassage(game.begin());
  }

  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }

  private void updateUIWithPassage(Passage passage) {
    passageContent.setText(passage.getContent());
    HBox buttonsBox = (HBox) borderPane.getBottom();
    buttonsBox.getChildren().clear();

    for (Link link : passage.getLinks()) {
      System.out.println(link);
      Button button = new Button(link.getText());
      button.setOnAction(event -> {
        Passage nextPassage = game.go(link);
        System.out.println(nextPassage);
        updateUIWithPassage(nextPassage);
      });
      buttonsBox.getChildren().add(button);
    }
  }
}
