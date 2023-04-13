package edu.ntnu.idatt2001.paths.models;

import edu.ntnu.idatt2001.paths.models.goals.Goal;

import java.util.List;

/**
 * A class that represents a game. A game connects a player to a story and have methods to start and manage the game.
 */

public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;

  /**
   * Constructor for Game.
   *
   * @param player The player of the game.
   * @param story The story of the game.
   * @param goals The goals of the game.
   * @throws NullPointerException if player, story or goals is null.
   */
  public Game(Player player, Story story, List<Goal> goals) throws NullPointerException {

    if (player == null) {
      throw new NullPointerException("The player can´t be null");
    }
    if (story == null) {
      throw new NullPointerException("The story can´t be null");
    }
    if (goals == null) {
      throw new NullPointerException("The goals can´t be null");
    }

    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  /**
   * Copy constructor for Game.
   *
   * @param game The game to be copied.
   * @throws NullPointerException if game is null.
   */

  public Game(Game game) {
    this.player = game.player;
    this.story = game.story;
    this.goals = game.goals;
  }

  /**
   * Returns the player of the game.
   *
   * @return The player of the game.
   */

  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the story of the game.
   *
   * @return The story of the game.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Returns the goals of the game.
   *
   * @return The goals of the game.
   */

  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * Returns the opening passage of the story.
   *
   * @return The opening passage of the story.
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Returns the passage that is linked to the given link.
   *
   * @param link The link to the passage.
   * @return The passage that is linked to the given link.
   * @throws NullPointerException if link is null.
   */
  public Passage go(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Game-Link can not be null");
    }
    return story.getPassage(link);
  }
}
