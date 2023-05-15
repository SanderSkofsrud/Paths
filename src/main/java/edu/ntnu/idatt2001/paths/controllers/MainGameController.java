package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Link;
import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.Story;

import java.util.HashMap;

public class MainGameController {

  private static MainGameController instance;

  private Story story;

  private HashMap<Link, Passage> brokenLinks = new HashMap<>();

  private MainGameController() {}

  public static MainGameController getInstance() {
    if (instance == null) {
      instance = new MainGameController();
    }
    return instance;
  }

}
