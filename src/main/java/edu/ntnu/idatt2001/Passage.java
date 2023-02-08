package edu.ntnu.idatt2001;

import java.util.List;
import java.util.*;

public class Passage {
  private final String title;
  private final String content;
  private List<Link> links;

  public Passage(String title, String content) {

    if (title.isBlank()) {
      throw new IllegalArgumentException("Title must not be empty");
    }
    if (content.isBlank()) {
      throw new IllegalArgumentException("Content must not be empty");
    }

    this.title = title;
    this.content = content;
  }

public Passage(Passage passage) {
    this.title = passage.title;
    this.content = passage.content;
}

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public List<Link> getLinks() {
    return links;
  }

  public void addLink(Link link) {
    links.add(link);
  }

  public boolean hasLinks() {
    return links != null;
  }
  @java.lang.Override
  public java.lang.String toString() {
    return "Passage{" +
            "title='" + getTitle() + '\'' +
            ", content='" + getContent() + '\'' +
            ", links=" + getLinks() +
            '}';
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof Passage)) return false;
    if (!super.equals(object)) return false;

    Passage passage = (Passage) object;

    if (!getTitle().equals(passage.getTitle())) return false;

    return true;
  }

  public int hashCode() {
    return Objects.hash(getTitle(), getContent(), getLinks());  }
}

