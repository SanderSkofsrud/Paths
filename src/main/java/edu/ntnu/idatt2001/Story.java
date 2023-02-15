package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  public Story(String title, Passage openingPassage) throws NullPointerException {

    if (title == null ||title.isEmpty()) {
      throw new NullPointerException("Title must not be empty");
    }
    if (openingPassage == null) {
      throw new NullPointerException("Opening passage must not be empty");
    }

    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<Link, Passage>();
  }

  public Story(Story story) {
    this.title = story.title;
    this.openingPassage = story.openingPassage;
    this.passages = story.passages;
  }

  public String getTitle() {
    return title;
  }

  public Map<Link, Passage> getPassages() {
    return passages;
  }

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public void addPassage(Passage passage){
    if (passage == null){
      throw new IllegalArgumentException("Passage can not be null");
    }
    passages.put(new Link(passage.getTitle(), passage.getTitle()), passage);
  }

  public Passage getPassage(Link link) throws NullPointerException {
    if (link == null){
      throw new NullPointerException("Story-Link cannot be null");
    }
    return passages.get(link);
  }

  @Override
  public String toString() {
    return "Story{" +
        "title='" + getTitle() + '\'' +
        ", passages=" + getPassages() +
        ", openingPassage=" + getOpeningPassage() +
        '}';
  }
}
