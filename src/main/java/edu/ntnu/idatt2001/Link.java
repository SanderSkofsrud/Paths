package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a link between two passages.
 */

public class Link {
  private final String text;
  private final String reference;
  private List<Action> actions;

  /**
   * Constructor for Link.
   *
   * @param text The text that will be displayed to the user.
   * @param reference The reference to the passage that the link will lead to.
   * @throws NullPointerException if text or reference is null.
   */

  public Link(String text, String reference) throws NullPointerException {
    if (text == null || text.isBlank()) {
      throw new NullPointerException("The text can´t be empty");
    }
    if (reference == null || reference.isBlank()) {
      throw new NullPointerException("The reference can´t be empty");
    }
    Objects.requireNonNull(text, "The text can´t be null");
    Objects.requireNonNull(reference, "The reference can´t be null");

    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  /**
   * Copy constructor for Link.
   *
   * @param link The link to be copied.
   * @throws NullPointerException if link is null.
   */
  public Link(Link link) {
    this.text = link.text;
    this.reference = link.reference;
    this.actions = link.actions;
  }

  /**
   * Returns the text of the link.
   *
   * @return The text of the link.
   */

  public String getText() {
    return text;
  }

  /**
   * Returns the reference of the link.
   *
   * @return The reference of the link.
   */

  public String getReference() {
    return reference;
  }

  /**
   * Adds an action to the link.
   *
   * @param action The action to be added.
   * @throws IllegalArgumentException if action is null.
   */

  public void addAction(Action action) throws IllegalArgumentException {
    if (action == null) {
      throw new IllegalArgumentException("Link Action can not be null!");
    }
    actions.add(action);
  }

  /**
   * Returns the actions of the link.
   *
   * @return The actions of the link.
   */

  public List<Action> getActions() {
    return actions;
  }

  /**
   * Returns a string representation of the link.
   *
   * @return A string representation of the link.
   */

  @Override
  public String toString() {
    return "Link{"
        + "text='"
        + getText() + '\''
        + ", refrence='" + getReference()
        + '}';
  }

  /**
   * Returns true if the link is equal to the object passed as parameter.
   *
   * @param o The object to be compared with the link.
   * @return True if the link is equal to the object passed as parameter.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Link link = (Link) o;

    if (!text.equals(link.text)) return false;
    if (!reference.equals(link.reference)) return false;
    return actions.equals(link.actions);
  }

  /**
   * Returns the hashcode of the link.
   *
   * @return The hashcode of the link.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getText(), getReference(), getActions());
  }

}
