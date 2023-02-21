package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.goals.Goal;

import java.util.List;

public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;

  public Game(Player player, Story story, List<Goal> goals) throws NullPointerException {

    if (player == null){
      throw new NullPointerException("The player can´t be null");
    }
    if(story == null){
      throw new NullPointerException("The story can´t be null");
    }
    if (goals == null){
      throw new NullPointerException("The goals can´t be null");
    }

    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  public Game(Game game) {
    this.player = game.player;
    this.story = game.story;
    this.goals = game.goals;
  }

  public Player getPlayer() {
    return player;
  }

  public Story getStory() {
    return story;
  }

  public List<Goal> getGoals() {
    return goals;
  }

  public Passage begin(){
    return story.getOpeningPassage();
  }

  public Passage go(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Game-Link can not be null");
    }
    return story.getPassage(link);
  }
}
