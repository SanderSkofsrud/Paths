package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Link {
  private final String text;
  private final String reference;
  private List<Action> actions;

  public Link(String text, String reference) throws NullPointerException {
    if (text == null || text.isBlank()){
      throw new NullPointerException("The text can´t be empty");
    }
    if(reference == null || reference.isBlank()){
      throw new NullPointerException("The reference can´t be empty");
    }
    Objects.requireNonNull(text,"The text can´t be null");
    Objects.requireNonNull(reference, "The reference can´t be null");

    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  public Link(Link link) {
    this.text = link.text;
    this.reference = link.reference;
    this.actions = link.actions;
  }


  public String getText() {
    return text;
  }

  public String getReference() {
    return reference;
  }

  public void addAction(Action action) throws IllegalArgumentException {
    if (action == null) {
      throw new IllegalArgumentException("Link Action can not be null!");
    }
    actions.add(action);
  }

  public List<Action> getActions() {
    return actions;
  }

    @Override
    public String toString() {
        return "Link{" +
            "text='" + getText() + '\'' +
            ", refrence='" + getReference() +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!text.equals(link.text)) return false;
        if (!reference.equals(link.reference)) return false;
        return actions.equals(link.actions);
    }

    @Override
    public int hashCode() {
    return Objects.hash(getText(), getReference(),getActions());  }

}
