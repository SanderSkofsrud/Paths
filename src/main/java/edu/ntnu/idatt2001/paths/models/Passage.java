package edu.ntnu.idatt2001.paths.models;

import java.util.*;
import java.util.List;


/**
 * A class that represents a passage.
 * A passage is a part of a story and it´s possible to navigate from one passage
 * to another by using a link. A passage can have multiple links leading to other passages.
 * A passage has a title, content of the passage and a list of links.
 * The title describes the passage and the content is the text that will be displayed to the user.
 *
 * @author Helle R. & Sander S.
 * @version 0.3 - 11.04.2023
 */

public class Passage {
  private final String title;
  private final String content;
  private List<Link> links;

  /**
   * Constructor for Passage.
   * The title and content of the passage can´t be null or empty.
   * The list of links will be initialized as an empty list.
   *
   * @param title The title of the passage.
   * @param content The content of the passage.
   * @throws NullPointerException if title or content is null.
   * @throws IllegalArgumentException if title or content is empty.
   */

  public Passage(String title, String content) throws NullPointerException {
    Objects.requireNonNull(title, "The title can´t be null");
    Objects.requireNonNull(content, "The content can´t be null");

    if (title.isBlank()) {
      throw new IllegalArgumentException("Title must not be empty");
    }
    if (content.isBlank()) {
      throw new IllegalArgumentException("Content must not be empty");
    }

    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  /**
   * Copy constructor for Passage.
   *
   * @param passage The passage to be copied.
   * @throws NullPointerException if passage is null.
   */
  public Passage(Passage passage) {
    this.title = passage.title;
    this.content = passage.content;
    this.links = passage.links;
  }

  /**
   * Returns the title of the passage.
   *
   * @return The title of the passage.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the content of the passage.
   *
   * @return The content of the passage.
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the links of the passage.
   *
   * @return The links of the passage.
   */
  public List<Link> getLinks() {
    return links;
  }

  /**
   * Adds a link to the passage.
   * Checks if the link already exists in the passage.
   * The link can´t be null.
   *
   * @param link The link to be added.
   * @throws NullPointerException if link is null.
   */
  public void addLink(Link link) {
    Objects.requireNonNull(link, "Link can not be null");
    if (getLinks().contains(link)) {
      throw new IllegalArgumentException("Link already exists in passage!");
    }
    links.add(link);
  }

  /**
   * Checks if the passage has links.
   *
   * @return True if the passage has at least one link.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Returns a string representation of the passage with the title, content and links.
   * Uses the toString method from the Link class and string builder to create the string.
   * The string will be formatted as follows:
   * <p>
   *    ::title <br>
   *    content <br>
   *    link1 <br>
   *    link2 <br>
   *    linkN
   * </p>
   *
   *
   * @return A string representation of the passage.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Link link : links) {
      sb.append(link.toString());
    }
    return "\n::"
            + getTitle()
            + "\n"
            + getContent()
            + "\n"
            + sb;
  }

  /**
   * Returns true if the passage is equal to the object passed as parameter.
   * The passage is equal to another passage if the titles are equal.
   *
   * @param object The object to be compared.
   * @return True if the passage is equal to the object.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Passage)) return false;
    if (!super.equals(object)) return false;

    Passage passage = (Passage) object;

    return getTitle().equals(passage.getTitle());
  }

  /**
   * Returns the hash code of the passage.
   *
   * @return The hash code of the passage.
   */

  @Override
  public int hashCode() {
    return Objects.hash(getTitle(), getContent(), getLinks());
  }
}

