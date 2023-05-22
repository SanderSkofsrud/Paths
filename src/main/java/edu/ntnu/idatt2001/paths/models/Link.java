package edu.ntnu.idatt2001.paths.models;

import edu.ntnu.idatt2001.paths.models.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a link. The link makes it possible to navigate
 * from one passage to another.
 * The links connect the different parts of the story.
 * A link has a text, a reference to the passage that the link will lead to and a list of actions
 *
 * @author Helle R. & Sander S.
 * @version 0.5 - 11.04.2023
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
   * @throws IllegalArgumentException if text or reference is empty.
   */

  public Link(String text, String reference) throws NullPointerException, IllegalArgumentException {
    Objects.requireNonNull(text, "The text can´t be null");
    Objects.requireNonNull(reference, "The reference can´t be null");
    if (text.isBlank()) {
      throw new IllegalArgumentException("The text can´t be empty");
    }
    if (reference.isBlank()) {
      throw new IllegalArgumentException("The reference can´t be empty");
    }


    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
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
   * Uses the text, reference and actions to the link with a given format.
   * The format is [text](reference)[action1][action2]...[actionN]
   * Uses string builder to create the string.
   *
   * @return A string representation of the link.
   */

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (!actions.isEmpty()) {
      sb.append("[");
      for (Action action : actions) {
        sb.append(action.toString());
      }
      sb.append("]");
    }
    return "["
        + getText()
        + "]("
        + getReference()
        + ")"
        + sb
        + "\n";

  }

  /**
   * Returns true if the link is equal to the object passed as parameter.
   * Two links are equal if they have the same reference.
   *
   * @param o The object to be compared with the link.
   * @return True if the link is equal to the object passed as parameter.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return reference != null ? reference.equals(link.reference) : link.reference == null;
  }

  /**
   * Returns the hashcode of the link based on the text and reference.
   *
   * @return The hashcode of the link.
   */
  @Override
  public int hashCode() {
    return reference != null ? reference.hashCode() : 0;
  }

}
