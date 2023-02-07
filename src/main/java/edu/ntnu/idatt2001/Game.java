package edu.ntnu.idatt2001;

import java.util.List;

public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;

  public Game(Player player, Story story, List<Goal> goals) {
    this.player = player;
    this.story = story;
    this.goals = goals;
  }


}
