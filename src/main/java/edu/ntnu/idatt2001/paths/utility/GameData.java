package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;

import java.util.List;

public class GameData {
  private Story story;
  private Player player;
  private List<Goal> goals;

  public GameData(Story story, Player player, List<Goal> goals) {
    this.story = story;
    this.player = player;
    this.goals = goals;
  }

  public Story getStory() {
    return story;
  }

  public Player getPlayer() {
    return player;
  }

  public List<Goal> getGoals() {
    return goals;
  }
}

