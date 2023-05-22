package edu.ntnu.idatt2001.paths.models;

import edu.ntnu.idatt2001.paths.models.goals.Goal;
import edu.ntnu.idatt2001.paths.models.player.Player;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a game.
 * A game connects a player to a story and have methods to start and manage the game.
 * It has a player, a story and a list of goals and represent what the user will use.
 * The game have goals that the player must fulfill to win the game.
 *
 * @author Helle R. & Sander S.
 * @version 0.2 - 11.04.2023
 */

public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;
  private Passage currentPassage;

  /**
   * Constructor for Game.
   * The player, story and goals can not be null.
   *
   * @param player The player of the game - represents who is playing the game
   *               and their possessions
   * @param story The story of the game - represents the structure of the game
   *              with links and passages
   * @param goals The goals of the game - the achievements that the player
   *              must fulfill to win
   * @throws NullPointerException if player, story or goals is null.
   */
  public Game(Player player, Story story, List<Goal> goals, Passage currentPassage)
      throws NullPointerException {
    Objects.requireNonNull(player, "The player can´t be null");
    Objects.requireNonNull(story, "The story can´t be null");
    Objects.requireNonNull(goals, "The goals can´t be null");

    if (goals.isEmpty()) {
      throw new IllegalArgumentException("The story needs at least one goal");
    }
    if (goals.stream().anyMatch(Objects::isNull)) {
      throw new NullPointerException("Goals can not contain null values");
    }

    this.player = player;
    this.story = story;
    this.goals = goals;
    this.currentPassage = currentPassage;
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

  public Passage getCurrentPassage() {
    return currentPassage;
  }

  public void setCurrentPassage(Passage currentPassage) {
    this.currentPassage = currentPassage;
  }


  /**
   * Starts the game and returns the opening passage of the story.
   *
   * @return The opening passage of the story.
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * Returns the passage that is linked to the given link and
   * moves the player to the new passage through the link.
   * If the player has fulfilled any of the goals, the ending passage is returned.
   *
   * @param link The link to the passage.
   * @return The passage that is linked to by the given link or the ending passage
   *        if the player has fulfilled any of the goals.
   * @throws NullPointerException if link is null.
   */
  public Passage go(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Game-Link can not be null");
    }
    if (checkGoals()) {
      return story.getEndingPassage();
    }
    return story.getPassage(link);
  }

  /**
   * Checks if the player has fulfilled all the goals.
   *
   * @return True if the player has fulfilled all the goals, false otherwise.
   */
  public boolean checkGoals() {
    return goals.stream().allMatch(goal -> goal.isFulfilled(player));
  }

  /**
   * Returns a string representation of the game.
   *
   * @return A string representation of the game.
   */

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Goal goal : goals) {
      sb.append(goal.toString());
      sb.append("\n");
    }
    return story
            + "\n"
            + sb
            + "\n"
            + player;
  }
}
