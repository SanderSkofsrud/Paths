package edu.ntnu.idatt2001;

import javax.swing.*;

public class Link {
  private final String text;
  private final String refrence;
  private Link<Action> actions;

  public Link(String text, String refrence) {
    if (text.isBlank()){
      throw new IllegalArgumentException("The text can´t be empty");
    }
    if(refrence.isBlank()){
      throw new IllegalArgumentException("The reference can´t be empty");
    }

    this.text = text;
    this.refrence = refrence;
  }

  public Link(Link link) {
    this.text = link.text;
    this.refrence = link.refrence;
  }


  public String getText() {
    return text;
  }

  public String getRefrence() {
    return refrence;
  }

  public boolean addAction(Action action) {
    return action != null;
  }

  public Link<Action> getActions() {
    return actions;
  }

    @Override
    public String toString() {
        return "Link{" +
            "text='" + getText() + '\'' +
            ", refrence='" + getRefrence() + '\'' +
            ", actions=" + getActions() +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!text.equals(link.text)) return false;
        if (!refrence.equals(link.refrence)) return false;
        return actions.equals(link.actions);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + refrence.hashCode();
        result = 31 * result + actions.hashCode();
        return result;
    }
}
