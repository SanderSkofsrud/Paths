package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a story.
 */

public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  /**
   * Constructor for Story.
   *
   * @param title The title of the story.
   * @param openingPassage The opening passage of the story.
   * @throws NullPointerException if title or openingPassage is null.
   */
  public Story(String title, Passage openingPassage) throws NullPointerException {

    if (title == null || title.isEmpty()) {
      throw new NullPointerException("Title must not be empty");
    }
    if (openingPassage == null) {
      throw new NullPointerException("Opening passage must not be empty");
    }

    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<Link, Passage>();
    addPassage(openingPassage);
  }

  /**
   * Copy constructor for Story.
   *
   * @param story The story to be copied.
   * @throws NullPointerException if story is null.
   */
  public Story(Story story) {
    this.title = story.title;
    this.openingPassage = story.openingPassage;
    this.passages = story.passages;
  }

  /**
   * Returns the title of the story.
   *
   * @return The title of the story.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the passages of the story.
   *
   * @return The passages of the story.
   */
  public Map<Link, Passage> getPassages() {
    return passages;
  }

  /**
   * Returns the opening passage of the story.
   *
   * @return The opening passage of the story.
   */

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  /**
   * Adds a passage to the story.
   *
   * @param passage The passage to be added.
   * @throws IllegalArgumentException if passage is null.
   */

  public void addPassage(Passage passage) {
    if (passage == null) {
      throw new IllegalArgumentException("Passage can not be null");
    }
    passages.put(new Link(passage.getTitle(), passage.getTitle()), passage);
  }

  /**
   * Returns the passage that the link leads to.
   *
   * @param link The link to the passage.
   * @return Passage
   * @throws NullPointerException if link is null.
   */

  public Passage getPassage(Link link) throws NullPointerException {
    if (link == null) {
      throw new NullPointerException("Story-Link cannot be null");
    }
    return passages.get(link);
  }

  /**
   * Return a String representation of the story.
   *
   * @return A String representation of the story.
   */
  @Override
  public String toString() {
    return "Story{"
        + "title='"
        + getTitle() + '\''
        + ", passages="
        + getPassages()
        + ", openingPassage="
        + getOpeningPassage()
        + '}';
  }
}
