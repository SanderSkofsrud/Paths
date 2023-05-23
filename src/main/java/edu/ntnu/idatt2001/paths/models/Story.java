package edu.ntnu.idatt2001.paths.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class that represents a story.
 * A story is interactive, and non-linear and is of
 * a collection of passages and the links that connect them.
 * The story also has a title and an opening passage.
 *
 * @author Helle R. and Sander S.
 * @version 0.5 - 11.04.2023
 */

public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  private Passage endingPassage;

  /**
   * Constructor for Story.
   * The title of the story must not be null or empty.
   * The opening passage of the story must not be null.
   *
   * @param title The title of the story.
   * @param openingPassage The opening passage of the story.
   * @throws NullPointerException if title or openingPassage is null.
   * @throws IllegalArgumentException if title is empty.
   */
  public Story(String title, Passage openingPassage) throws NullPointerException {
    Objects.requireNonNull(title, "The title can´t be null");
    Objects.requireNonNull(openingPassage, "The opening passage can´t be null");

    if (title.isBlank()) {
      throw new IllegalArgumentException("Title must not be empty");
    }

    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
    this.addPassage(openingPassage);
    this.endingPassage = new Passage("The End", "Congratulations! You won the game");
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
   * The passage can not be added if its is null or already exist in the Story.
   * A link will be created from the title of the passage as the reference and title
   *
   * @param passage The passage to be added.
   * @throws NullPointerException if passage is null.
   * @throws IllegalArgumentException if passage is already in the story.
   */

  public void addPassage(Passage passage) {
    Objects.requireNonNull(passage, "Passage can not be null");
    if (passages.containsValue(passage)) {
      throw new IllegalArgumentException("Passage already exist in the story");
    }
    passages.put(new Link(passage.getTitle(), passage.getTitle()), passage);
  }

  /**
   * Returns the passage that the link leads to.
   *
   * @param link The link to the passage.
   * @return Passage that the link leads to.
   * @throws NullPointerException if link is null.
   */

  public Passage getPassage(Link link) throws NullPointerException {
    Objects.requireNonNull(link, "Link can not be null");
    Link mapLink = new Link(link.getReference(), link.getReference());
    return passages.get(mapLink);
  }

  /**
   * Removes a passage from the story.
   * The passage can not be removed if it is null or if
   * other passages has links to this passage.
   *
   * @param link The link to the passage.
   * @throws IllegalArgumentException if link is null or if other
   *        passages has links to this passage.
   */
  public void removePassage(Link link) throws IllegalArgumentException {
    boolean otherPassagesLinkToPassage = passages.values().stream()
        .flatMap(passage -> passage.getLinks().stream())
        .anyMatch(l -> l.getReference().equals(link.getReference()));
    if (getPassage(link) == null) {
      throw new IllegalArgumentException("Can not find passage");
    }
    if (otherPassagesLinkToPassage) {
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
   * Returns the ending passage of the story.
   * A player will reach the ending passage when it has fulfilled all goals.
   *
   * @return The ending passage of the story.
   */
  public Passage getEndingPassage() {
    return endingPassage;
  }

  /**
   * Return a String representation of the story.
   * The String representation of the story is a concatenation of the title,
   * the opening passage and the passages.
   * Uses string builder to concatenate the passages and will therefore also
   * display links from the passage toString.
   *
   * @return A String representation of the story.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Passage passage : passages.values()) {
      if (passage.equals(openingPassage)) {
        continue;
      }
      sb.append(passage.toString());
    }
    return getTitle()
            + "\n"
            + getOpeningPassage()
            + sb;
  }
}
