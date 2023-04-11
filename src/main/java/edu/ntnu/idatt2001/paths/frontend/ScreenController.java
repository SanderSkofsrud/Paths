package edu.ntnu.idatt2001.paths.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
  private HashMap<String, Pane> screenMap = new HashMap<>();
  private HashMap<String, View> viewMap = new HashMap<>();
  private Scene main;

  public ScreenController(Scene main) {
    this.main = main;
  }

  public void addScreen(String name, View view) {
    screenMap.put(name, view.getPane());
    viewMap.put(name, view);
  }

  protected void activate(String name) {
    viewMap.get(name).setup();
    main.setRoot(screenMap.get(name));
  }
}
