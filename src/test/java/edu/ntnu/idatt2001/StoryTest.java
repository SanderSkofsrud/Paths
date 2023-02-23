package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StoryTest {

  @Nested
  public class Constructor {
    @Test
    @DisplayName("Test that constructor constructs correctly")
    void testThatConstructorConstructsCorrectly() {
      Passage passage = new Passage("title", "content");
      Story story = new Story("title", passage);
      assertEquals("title", story.getTitle());
      assertEquals(passage, story.getOpeningPassage());
    }
  }

  @Nested
  public class ExceptionHandling {
    @Test
    @DisplayName("Test that constructor throws NullPointerExceptions when passage null")
    void testThatConstructorThrowsNullPointerExceptionsWhenPassageNull() {
      Passage passage = null;
      assertThrows(NullPointerException.class, () ->
          new Story("title", passage));
    }

    @Test
    @DisplayName("Test that constructor throws NullPointerException when title is null")
    void testThatConstructorThrowsNullPointerExceptionWhenTitleIsNull(){
      Passage passage = new Passage("title", "content");
      assertThrows(NullPointerException.class, () ->
          new Story(null, passage));
    }

    @Test
    @DisplayName("Test that addPassage throws IllegalArgumentException")
    void testThatAddPassageThrowsIllegalArgumentException(){
      Passage passage = null;
      Story story = new Story("title", new Passage("title", "content"));
      assertThrows(IllegalArgumentException.class, () ->
          story.addPassage(passage));
    }

    @Test
    @DisplayName("Test that getPassage throws NullPointerException")
    void testThatGetPassageThrowsNullPointerException(){
      Link link = null;
      Story story = new Story("title", new Passage("title", "content"));
      assertThrows(NullPointerException.class, () ->
          story.getPassage(link));
    }
  }

  @Nested
  public class ReturnValues {

    @Test
    @DisplayName("Test that passage is added and fetched")
    void testThatPassageIsAddedAndFetched() {
      Passage passage = new Passage("title", "content");
      Story story = new Story("title", passage);
      assertEquals(passage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test that getStory returns correctly")
    void testThatGetStoryReturnsCorrectly() {
      Passage openingPassage = new Passage("title", "content");
      Story story = new Story("title", openingPassage);

      assertEquals("title", story.getTitle());
    }

    @Test
    @DisplayName("Test that getOpeningPassage returns correctly")
    void testThatGetOpeningPassageReturnsCorrectly() {
      Passage openingPassage = new Passage("title", "content");
      Story story = new Story("title", openingPassage);

      assertEquals(openingPassage, story.getOpeningPassage());
    }

    @Test
    @DisplayName("Test that getPassages returns correctly")
    void testThatGetPassagesReturnsCorrectly() {
      Passage openingPassage = new Passage("title", "content");
      Story story = new Story("title", openingPassage);
      Passage passage = new Passage("title2", "content2");
      story.addPassage(passage);
      assertEquals(2, story.getPassages().size());
    }

    @Test
    @DisplayName("Test that getTitle returns correctly")
    void testThatGetTitleReturnsCorrectly() {
      Passage openingPassage = new Passage("title", "content");
      Story story = new Story("title", openingPassage);

      assertEquals("title", story.getTitle());
    }
  }
}
