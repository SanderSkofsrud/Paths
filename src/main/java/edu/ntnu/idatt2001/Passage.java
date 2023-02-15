package edu.ntnu.idatt2001;

import java.util.List;
import java.util.*;

public class Passage {
  private final String title;
  private final String content;
  private List<Link> links;

  public Passage(String title, String content) throws NullPointerException {

    if (title == null ||title.isBlank()) {
      throw new NullPointerException("Title must not be empty");
    }
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("Content must not be empty");
    }
    Objects.requireNonNull(title,"The title can´t be null");
    Objects.requireNonNull(content, "The content can´t be null");

    this.title = title;
    this.content = content;
    this.links = new ArrayList<Link>();
  }

public Passage(Passage passage) {
    this.title = passage.title;
    this.content = passage.content;
    this.links = passage.links;
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
    if (link == null) {
      throw new IllegalArgumentException ("PassageLink can not be null");
    }
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

