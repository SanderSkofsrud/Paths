package edu.ntnu.idatt2001;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/**
 * Test class for Story
 */

public class StoryTest {

  Story story;
  Passage passage;
  Passage passage2;
  Story story2;
  Link link;

  Link link2;

  @BeforeEach
  void setUp() {
    passage = new Passage("passage", "content");
    passage2 = new Passage("passage2", "content2");
    story = new Story("story", passage);
    story2 = new Story("story2",new Passage("title", "content"));
    link = new Link("passage2", "passage2");
    link2 = new Link("test", "test");
  }

  /**
   * Test class for constructor
   */
  @Nested
  public class Constructor {
    /**
     * Test that constructor constructs correctly
     */
    @Test
    @DisplayName("Test that constructor constructs correctly")
    void testThatConstructorConstructsCorrectly() {
      assertEquals("story", story.getTitle());
      assertEquals(passage, story.getOpeningPassage());
    }

    /**
     * Test that constructor throws NullPointerExceptions when passage null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerExceptions when passage null")
    void testThatConstructorThrowsNullPointerExceptionsWhenPassageNull() {
      Passage passage = null;
      assertThrows(NullPointerException.class, () ->
          new Story("title", passage));
    }

    /**
     * Test that constructor throws NullPointerException when title is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when title is null")
    void testThatConstructorThrowsNullPointerExceptionWhenTitleIsNull(){
      assertThrows(NullPointerException.class, () ->
          new Story(null, passage));
    }
  }

  /**
   * Test class for exception handling
   */
  @Nested
  public class ExceptionHandling {
    /**
     * Test that constructor throws NullPointerException when openingPassage is null
     */
    @Test
    @DisplayName("Test that addPassage throws IllegalArgumentException")
    void testThatAddPassageThrowsIllegalArgumentException(){
      Passage passage = null;
      assertThrows(IllegalArgumentException.class, () ->
          story2.addPassage(passage));
    }

    /**
     * Test that getPassage throws NullPointerException when link is null
     */
    @Test
    @DisplayName("Test that getPassage throws NullPointerException")
    void testThatGetPassageThrowsNullPointerException(){
      Link link = null;
      assertThrows(NullPointerException.class, () ->
          story2.getPassage(link));
    }

    /**
     * Test that removePassage throws IllegalArgumentException if passage does not exist in Story
     */
    @Test
    @DisplayName("Test that removePassage throws IllegalArgumentException if passage does not exist in Story")
    void testThatRemovePassageThrowsIllegalArgumentExceptionIfPassageDocentExist(){
      passage2.addLink(link);
      assertThrows(IllegalArgumentException.class, () ->
          story.removePassage(link));
    }

    @Test
    void testThatRemovePassageThrowsIllegalArgumentExceptionIfPassageHasLinks() {
      passage.addLink(link);
      story.addPassage(passage2);
      assertThrows(IllegalArgumentException.class, () ->
          story.removePassage(link));
    }
  }

  /**
   * Test class for return values
   */

  @Nested
  public class ReturnValues {

    /**
     * Test that passage is added and fetched
     */
    @Test
    @DisplayName("Test that passage is added and fetched")
    void testThatPassageIsAddedAndFetched() {
      assertEquals(passage, story.getOpeningPassage());
    }

    /**
     * Test that getOpeningPassage returns correctly
     */
    @Test
    @DisplayName("Test that getOpeningPassage returns correctly")
    void testThatGetOpeningPassageReturnsCorrectly() {
      assertEquals(passage, story.getOpeningPassage());
    }

    /**
     * Test that getPassage returns correctly
     */
    @Test
    @DisplayName("Test that getPassages returns correctly")
    void testThatGetPassagesReturnsCorrectly() {
      story.addPassage(passage2);
      assertEquals(2, story.getPassages().size());
    }

    /**
     * Test that getTitle returns correctly
     */
    @Test
    @DisplayName("Test that getTitle returns correctly")
    void testThatGetTitleReturnsCorrectly() {
      assertEquals("story", story.getTitle());
    }

    /**
     * Test that removePassage returns correctly
     */
    @Test
    @DisplayName("Test that removePassage returns correctly")
    void testThatRemovePassageRemovesPassage() {
      assertTrue(story.getPassages().size() == 1);
      story.addPassage(passage2);
      story.removePassage(link);
      assertEquals(1, story.getPassages().size());
    }

    /**
     * Test that getBrokenLinks returns correctly
     */
    @Test
    @DisplayName("Test that getBrokenLinks returns correctly")
    void testThatGetBrokenLinksReturnsCorrectly() {
      assertEquals(0, story.getBrokenLinks().size());
      passage2.addLink(link2);
      story.addPassage(passage2);
      assertEquals(1, story.getBrokenLinks().size());
    }
  }

  @Nested
  class fileHandling {
    @Test
    @DisplayName("Test toString")
    void testToString() {
      story.addPassage(passage2);
      passage2.addLink(link);
      passage2.addLink(link2);
      assertEquals("story"
              + "\n"
              + passage.toString()
              + passage.toString()
              + passage2.toString(), story.toString());
    }
  }
}
