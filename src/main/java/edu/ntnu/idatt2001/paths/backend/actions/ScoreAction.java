package edu.ntnu.idatt2001.paths.backend.actions;

import edu.ntnu.idatt2001.paths.backend.Player;

/**
 * The interface Action.
 */
public class ScoreAction implements Action {
  private final int points;

  /**
   * Instantiates a new Score action.
   *
   * @param points the points
   */
  public ScoreAction(int points) {
    this.points = points;
  }

  /**
   * Executes a score action.
   *
   * @param player the player
   */
  @Override
  public void execute(Player player) {
    if (player.getScore() + this.points < 0) {
      throw new IllegalArgumentException("Score cannot be less than 0");
    }
    player.addScore(points);
  }

  @Override
  public String toString() {
    return "{ScoreAction}(" + this.points + ")";
  }
}