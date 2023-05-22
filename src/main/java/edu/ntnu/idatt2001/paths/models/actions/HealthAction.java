package edu.ntnu.idatt2001.paths.models.actions;

import edu.ntnu.idatt2001.paths.models.player.Player;

/**
 * Health action.
 * An action that adds or removes health to the player when executed.
 *
 * @author Helle R. & Sander S.
 * @version 0.1 - 11.04.2023
 */
public class HealthAction implements Action {

  private final int health;

  /**
   * Instantiates a new Health action which adds or removes health to the player.
   *
   * @param health the amount of health to add or remove
   */
  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * Executes a health action on a player.
   * Uses the addHealth method in the Player class.
   *
   * @param player the player
   * @throws IllegalArgumentException if the player's health become less than 0
   */
  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player.getHealth() + this.health <= 0) {
      throw new IllegalArgumentException("You died!");
    }
    player.addHealth(health);
  }

  /**
   * Returns a string representation of the HealthAction object.
   *
   * @return a string representation of the HealthAction object.
   */
  @Override
  public String toString() {
    return "{HealthAction}(" + this.health + ")";
  }
}
