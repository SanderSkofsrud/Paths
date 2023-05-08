package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.views.View;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * The type Screen controller.
 * This class is responsible for switching between the different screens in the game.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class ScreenController {
  /*
   * A hashmap that maps the name of the screen to the pane of the screen.
   * This is used to switch between the different screens.
   */
  private HashMap<String, Pane> screenMap = new HashMap<>();
  /*
   * A hashmap that maps the name of the screen to the view of the screen.
   * This is used to set up the screen before it is shown.
   */
  private HashMap<String, View> viewMap = new HashMap<>();
  /**
   * The main scene of the game.
   */
  private Scene main;

  /**
   * Instantiates a new Screen controller.
   *
   * @param main the main scene of the game
   */
  public ScreenController(Scene main) {
    this.main = main;
  }

  /**
   * Add screen to the hashmaps.
   *
   * @param name the name of the view
   * @param view the view that is added
   */
  public void addScreen(String name, View view) {
    screenMap.put(name, view.getPane());
    viewMap.put(name, view);
  }

  /**
   * Activates a view and sets it up.
   * Shows the view on the main scene.
   *
   * @param name the name of the view
   */
  public void activate(String name) {
    viewMap.get(name).setup();
    main.setRoot(screenMap.get(name));
  }
}
