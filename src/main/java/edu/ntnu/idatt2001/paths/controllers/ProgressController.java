package edu.ntnu.idatt2001.paths.controllers;

import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.utility.ProgressData;
import edu.ntnu.idatt2001.paths.utility.paths.PathsReader;
import edu.ntnu.idatt2001.paths.utility.paths.PathsWriter;

import java.io.FileNotFoundException;

public class ProgressController {
  private static ProgressController instance;
  private Player player;
  private Passage previousPassage;
  private Passage currentPassage;
  private ProgressController() {
  }
  public static ProgressController getInstance() {
    if (instance == null) {
      instance = new ProgressController();
    }
    return instance;
  }
  public void saveProgress(Player player, Passage previousPassage, Passage currentPassage) {
    PathsWriter.saveProgress(player, previousPassage, currentPassage);
  }

  public void loadProgress(String playerName) {
    try {
      PathsReader.loadProgress(playerName);
      player = ProgressData.getPlayer();
      previousPassage = ProgressData.getPreviousPassage();
      currentPassage = ProgressData.getCurrentPassage();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not load progress");
    }
  }

  public Player getPlayer() {
    return player;
  }
  public Passage getPreviousPassage() {
    return previousPassage;
  }
  public Passage getCurrentPassage() {
    return currentPassage;
  }
}
