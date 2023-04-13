package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.Player;

/**
 * The interface Action.
 */
public class HealthAction implements Action {

  private final int health;

  /**
   * Instantiates a new Health action.
   *
   * @param health the health
   */
  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * Executes a health action.
   *
   * @param player the player
   */
  @Override
  public void execute(Player player) {
    if (player.getHealth() + this.health < 0) {
      throw new IllegalArgumentException("Health cannot be less than 0");
    }
    player.addHealth(health);
  }

  @Override
  public String toString() {
    return "{HealthAction}(" + this.health + ")";
  }
}
