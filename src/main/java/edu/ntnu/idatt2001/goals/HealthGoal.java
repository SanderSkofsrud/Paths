package edu.ntnu.idatt2001.goals;

import edu.ntnu.idatt2001.Player;

public class HealthGoal implements Goal {
  private final int minimumHealth;

  public HealthGoal(int minimumHealth) {
    if (minimumHealth < 0) {
      throw new IllegalArgumentException("Health cannot be less than 0");
    }
    this.minimumHealth = minimumHealth;
  }

  @Override
  public boolean isFulfilled(Player player) { return player.getHealth() >= minimumHealth; }
}
