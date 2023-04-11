package edu.ntnu.idatt2001.paths.frontend;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ChooseGoalsView extends View {
  protected BorderPane borderPane;
  protected StackPane stackPane;
  private ScreenController screenController;
  public ChooseGoalsView(ScreenController screenController) {
    borderPane = new BorderPane();
    stackPane = new StackPane();
    borderPane.setCenter(stackPane);
  }
  public Pane getPane() {
    return this.borderPane;
  }
  public void setup() {
    Text text = new Text("Choose goals");
    stackPane.getChildren().add(text);
  }

  public void resetPane() {
    stackPane.getChildren().clear();
  }
}
