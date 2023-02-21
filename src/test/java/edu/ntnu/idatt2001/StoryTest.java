package edu.ntnu.idatt2001;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StoryTest {
  @Test
  void testThatConstructorThrowsNullPointerExceptions(){
    Passage passage = null;
    assertThrows(NullPointerException.class, () ->
        new Story("Title", passage));
  }

  @Test
  void testThatPassageIsAddedAndFetched(){
    Passage passage = new Passage("title", "content");
    Story story = new Story("title", passage);
    assertEquals(passage, story.getOpeningPassage());
  }

  @Test
  void testThatGetStoryReturnsCorrectly(){
    Passage openingPassage = new Passage("title", "content");
    Story story = new Story("title", openingPassage);

    assertEquals("title", story.getTitle());
  }

  @Test
  void testThatGetOpeningPassageReturnsCorrectly(){
    Passage openingPassage = new Passage("title", "content");
    Story story = new Story("title", openingPassage);

    assertEquals(openingPassage, story.getOpeningPassage());
  }

  @Test
  void testThatGetPassagesReturnsCorrectly(){
    Passage openingPassage = new Passage("title", "content");
    Story story = new Story("title", openingPassage);
    Passage passage = new Passage("title2", "content2");
    story.addPassage(passage);
    assertEquals(2, story.getPassages().size());
  }
}
