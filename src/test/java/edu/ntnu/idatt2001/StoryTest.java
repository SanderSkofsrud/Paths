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
  Passage openingPassage;
  Story story2;
  Link link;

  Link link2;

  @BeforeEach
  void setUp() {
    passage = new Passage("passage", "content");
    passage2 = new Passage("passage2", "content2");
    openingPassage = new Passage("openingPassage", "content");
    story = new Story("story", openingPassage);
    story2 = new Story("story2",openingPassage);
    link = new Link("passage", "passage2");
    link2 = new Link("testhg", "test");
    passage.addLink(link);
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
      assertEquals(openingPassage, story.getOpeningPassage());
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
      assertThrows(IllegalArgumentException.class, () ->
          story.removePassage(link));
    }

    /**
     * Test that removePassage throws IllegalArgumentException if other passage has links to the passage
     */
    @Test
    void testThatRemovePassageThrowsIllegalArgumentExceptionIfPassageHasLinks() {
      story.addPassage(passage2);
      story.addPassage(passage);
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
     * Test that passage is added to story
     */
    @Test
    @DisplayName("Test that passage is added to Story")
    void testThatPassageIsAdded() {
      story.addPassage(passage);
      assertEquals(1, story.getPassages().size());
    }

    /**
     * Test that removePassage removes passage from story
     */
    @Test
    @DisplayName("Test that removePassage removes passage")
    void testThatRemovePassageRemovesPassage() {
      assertTrue(story.getPassages().size() == 0);
      story.addPassage(passage2);
      story.removePassage(link);
      assertEquals(1, story.getPassages().size());
    }

    /**
     * Test that the Arraylist BrokenLinks returns the correct size
     */
    @Test
    @DisplayName("Test that getBrokenLinks returns correctly")
    void testThatGetBrokenLinksReturnsCorrectly() {
      assertEquals(0, story.getBrokenLinks().size());
      story.addPassage(passage);
      assertEquals(1, story.getBrokenLinks().size());
    }
  }

  /**
   * Test class for file handling
   */
  @Nested
  class fileHandling {

    /**
     * Test that toString for Story returns correctly and ensure that the format is correct.
     */
    @Test
    @DisplayName("Test toString")
    void testToString() {
      passage.addLink(link);
      passage.addLink(link2);
      story.addPassage(passage);
      assertEquals(story.getTitle()
              + "\n"
              + story.getOpeningPassage()
              + passage.toString(), story.toString());
    }
  }
}
