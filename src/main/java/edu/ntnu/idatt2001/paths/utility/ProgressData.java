package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.models.Passage;
import edu.ntnu.idatt2001.paths.models.player.Player;

public class ProgressData {
  private static Passage previousPassage;
  private static Passage currentPassage;
  private static Player player;

  public ProgressData(Passage previousPassage, Passage currentPassage, Player player) {
    this.previousPassage = previousPassage;
    this.currentPassage = currentPassage;
    this.player = player;
  }

  public static Passage getPreviousPassage() {
    return previousPassage;
  }

  public static Passage getCurrentPassage() {
    return currentPassage;
  }

  public static Player getPlayer() {
    return player;
  }
}

