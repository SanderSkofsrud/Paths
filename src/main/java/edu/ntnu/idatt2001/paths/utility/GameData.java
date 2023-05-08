package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.models.player.Player;
import edu.ntnu.idatt2001.paths.models.Story;
import edu.ntnu.idatt2001.paths.models.goals.Goal;

import java.util.List;

/**
 * The type Game data.
 * The GameData class is used to store the data of the game.
 * The class is used to store the story, player and goals of the game.
 * The class is used so that a game can be loaded without having to contain all the data in the Game class.
 *
 * @author Helle R. and Sander S.
 * @version 0.1 08.05.2023
 */
public class GameData {
  /**
   * The Story.
   */
  private Story story;
  /**
   * The Player.
   */
  private Player player;
  /**
   * The Goals.
   */
  private List<Goal> goals;

  /**
   * Instantiates new Game data.
   * The constructor is used to create a GameData object.
   * The constructor is used to store the story, player and goals of the game.
   *
   * @param story  the story
   * @param player the player
   * @param goals  the goals
   */
  public GameData(Story story, Player player, List<Goal> goals) {
    this.story = story;
    this.player = player;
    this.goals = goals;
  }

  /**
   * Gets story.
   *
   * @return the story
   */
  public Story getStory() {
    return story;
  }

  /**
   * Gets player.
   *
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Gets goals.
   *
   * @return the goals
   */
  public List<Goal> getGoals() {
    return goals;
  }
}

