package edu.ntnu.idatt2001.paths.views;

import edu.ntnu.idatt2001.paths.controllers.ScreenController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * The type Choose goals view.
 */
public class ChooseGoalsView extends View {
  /**
   * The Border pane.
   */
  protected BorderPane borderPane;
  /**
   * The Stack pane.
   */
  protected StackPane stackPane;
  private ScreenController screenController;

  /**
   * Instantiates a new Choose goals view.
   *
   * @param screenController the screen controller
   */
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
