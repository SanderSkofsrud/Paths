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
    Passage passage = new Passage("Title", "Content");
    Story story = new Story("Title", passage);


    assertEquals(passage, story.getPassage(new Link("Title", "Title")));
  }

  @Test
  void testThatPassageValuesAreFetched(){
    Passage openingPassage = new Passage("Title", "Content");
    Story story = new Story("Title", openingPassage);
    Passage passage = new Passage("Another title", "More content");
    story.addPassage(passage);

    assertEquals(2, story.getPassages().size());
  }

  @Test
  void testThatGetStoryReturnsCorrectly(){
    Passage openingPassage = new Passage("Title", "Content");
    Story story = new Story("Title", openingPassage);

    assertEquals("Title", story.getTitle());
  }
}
