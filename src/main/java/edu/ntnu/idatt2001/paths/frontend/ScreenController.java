package edu.ntnu.idatt2001.paths.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * The type Screen controller.
 */
public class ScreenController {
  private HashMap<String, Pane> screenMap = new HashMap<>();
  private HashMap<String, View> viewMap = new HashMap<>();
  private Scene main;

  /**
   * Instantiates a new Screen controller.
   *
   * @param main the main
   */
  public ScreenController(Scene main) {
    this.main = main;
  }

  /**
   * Add screen.
   *
   * @param name the name
   * @param view the view
   */
  public void addScreen(String name, View view) {
    screenMap.put(name, view.getPane());
    viewMap.put(name, view);
  }

  /**
   * Activate.
   *
   * @param name the name
   */
  protected void activate(String name) {
    viewMap.get(name).setup();
    main.setRoot(screenMap.get(name));
  }
}
