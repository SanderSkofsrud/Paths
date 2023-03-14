package edu.ntnu.idatt2001;

import java.util.*;
import java.util.List;


/**
 * A class that represents a passage in a story.
 */

public class Passage {
  private final String title;
  private final String content;
  private List<Link> links;

  /**
   * Constructor for Passage.
   *
   * @param title The title of the passage.
   * @param content The content of the passage.
   * @throws NullPointerException if title or content is null.
   */

  public Passage(String title, String content) throws NullPointerException {

    if (title == null || title.isBlank()) {
      throw new NullPointerException("Title must not be empty");
    }
    if (content == null || content.isBlank()) {
      throw new NullPointerException("Content must not be empty");
    }
    Objects.requireNonNull(title, "The title can´t be null");
    Objects.requireNonNull(content, "The content can´t be null");

    this.title = title;
    this.content = content;
    this.links = new ArrayList<Link>();
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
   *
   * @param link The link to be added.
   * @throws NullPointerException if link is null.
   */
  public void addLink(Link link) {
    if (link == null) {
      throw new NullPointerException("PassageLink can not be null");
    }
    links.add(link);
  }

  /**
   * Returns true if the passage has links.
   *
   * @return True if the passage has links.
   */
  public boolean hasLinks() {
    return !links.isEmpty();
  }

  /**
   * Returns a string representation of the passage.
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
   * Returns true if the passage is equal to the object.
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

    if (!getTitle().equals(passage.getTitle())) return false;

    return true;
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

