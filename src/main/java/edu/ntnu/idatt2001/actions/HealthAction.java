package edu.ntnu.idatt2001.actions;

import edu.ntnu.idatt2001.Player;

public class HealthAction implements Action {

  private final int health;

  public HealthAction(int health) {this.health = health;}

  @Override
  public void execute(Player player) {
    if (player.getHealth() + this.health < 0) {
      throw new IllegalArgumentException("Health cannot be less than 0");
    }
    player.addHealth(health);}
}
