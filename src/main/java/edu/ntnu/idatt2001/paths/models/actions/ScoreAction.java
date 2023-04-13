package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.Player;

/**
 * Inventory action.
 * An action that adds or removes score to the player when executed.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class ScoreAction implements Action {
  private final int points;

  /**
   * Instantiates a new Score action which adds or removes score to the player.
   *
   * @param points the amount of points to add or remove
   */
  public ScoreAction(int points) {
    this.points = points;
  }

  /**
   * Executes a score action on a player.
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

  /**
   * Returns a string representation of the ScoreAction object.
   *
   * @return a string representation of the ScoreAction object.
   */
  @Override
  public String toString() {
    return "{ScoreAction}(" + this.points + ")";
  }
}