package edu.ntnu.idatt2001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Story {

  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  public Story(String title, Passage openingPassage) {

    if (title.isEmpty()) {
      throw new IllegalArgumentException("Title must not be empty");
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
  }

  public Passage getPassage(Link link){
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
