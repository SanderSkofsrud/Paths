package edu.ntnu.idatt2001;

import javax.swing.*;
import edu.ntnu.idatt2001.actions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Link {
  private final String text;
  private final String refrence;
  private List<Action> actions;

  public Link(String text, String refrence) throws IllegalArgumentException, NullPointerException {
    if (text.isBlank()){
      throw new IllegalArgumentException("The text can´t be empty");
    }
    if(refrence.isBlank()){
      throw new IllegalArgumentException("The reference can´t be empty");
    }
    Objects.requireNonNull(text,"The text can´t be null");
    Objects.requireNonNull(refrence, "The reference can´t be null");

    this.text = text;
    this.refrence = refrence;
    this.actions = new ArrayList<>();
  }

  public Link(Link link) {
    this.text = link.text;
    this.refrence = link.refrence;
    this.actions = link.actions;
  }


  public String getText() {
    return text;
  }

  public String getRefrence() {
    return refrence;
  }

  public void addAction(Action action) {
    actions.add(action);
  }

  public List<Action> getActions() {
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
    return Objects.hash(getText(),getRefrence(),getActions());  }

}
