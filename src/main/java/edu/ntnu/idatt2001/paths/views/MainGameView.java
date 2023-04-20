package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class MainGameView extends View{

  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;

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
    Text game = new Text();
    Text text = new Text("Main game view");
    stackPane.getChildren().add(text);
  }

  @Override
  void resetPane() {
    stackPane.getChildren().clear();
  }
}
