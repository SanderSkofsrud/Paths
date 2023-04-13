package edu.ntnu.idatt2001.paths.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a story. A story is interactive, and non-linear and exist of a collection of passages.
 * The story also has a title and an opening passage.
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

    if (title == null || title.isBlank()) {
      throw new NullPointerException("Title must not be empty");
    }
    if (openingPassage == null) {
      throw new NullPointerException("Opening passage must not be empty");
    }

    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<Link, Passage>();
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
    Link mapLink = new Link(link.getReference(), link.getReference());
    return passages.get(mapLink);
  }

  /**
   * Removes a passage from the story.
   * @param link The link to the passage.
   * @throws IllegalArgumentException if link is null or if other passages has links to this passage.
   */
  public void removePassage(Link link) {
    List<Passage> passagesWithLinks = passages.values().stream().filter(passage -> passage.getLinks().size() > 0).toList();
    if (getPassage(link) == null) {
      throw new IllegalArgumentException("Can not find passage");
    }
    if (passagesWithLinks.size() > 0) {
      throw new IllegalArgumentException("Other passages has links to this passage");
    }
    passages.remove(link);
  }

  /**
   * Returns a list of broken links.
   * A broken link is a link that does not lead to a passage in the story.
   *
   * @return A List of broken links.
   */
  public List<Link> getBrokenLinks() {
    return passages.values().stream()
        .flatMap(passage -> passage.getLinks().stream())
        .filter(link -> getPassage(link) == null)
        .toList();
  }

  /**
   * Return a String representation of the story.
   *
   * @return A String representation of the story.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Passage passage : passages.values()) {
      sb.append(passage.toString());
    }
    return getTitle()
            + "\n"
            + getOpeningPassage()
            + sb;
  }
}
