package edu.ntnu.idatt2001;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Passage
 */
public class PassageTest {
  Passage passage;
  Link link;
  List<Link> links;

  @BeforeEach
  void setUp() {
    passage = new Passage("title", "content");
    link = new Link("link", "link");
    links = new ArrayList<>();
  }

  /**
   * Test class for constructor
   */
  @Nested
  public class Constructor {
    /**
     * Test that constructor constructs object
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      assertEquals(Passage.class, passage.getClass());
    }
    /**
     * Test that constructor throws NullPointerException when title is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when title is null")
    void testThatConstructorThrowsNullPointerException() {
      String title = null;
      String content = "Test";
      assertThrows(NullPointerException.class, () -> new Passage(title, content));
    }

    /**
     * Test that constructor throws NullPointerException when content is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when content is null")
    void testThatConstructorThrowsNullPointerExceptionWhenContentIsNull() {
      String title = "Test";
      String content = null;
      assertThrows(NullPointerException.class, () -> new Passage(title, content));
    }
  }

  /**
   * Test class for exception handling
   */
  @Nested
  public class ExceptionHandling {

    /**
     * Test that addLink throws IllegalArgumentException when link is null
     */
    @Test
    @DisplayName("Test that addLink throws IllegalArgumentException when link is null")
    void testThatExceptionIsThrownWhenLinkIsNull() {
      Link link = null;
      assertThrows(NullPointerException.class, () -> passage.addLink(link));
    }

  }
  /**
   * Test class for return values
   */
  @Nested
  public class ReturnValues {

    /**
     * Test that getContent returns correct
     */
    @Test
    @DisplayName("Test that getContent returns correct")
    void testThatGetContentReturnsCorrect() {
      assertEquals("content", passage.getContent());
    }

    /**
     * Test that getTitle returns correct
     */
    @Test
    @DisplayName("Test that getTitle returns correct")
    void testThatGetTitleReturnsCorrect() {
      assertEquals("title", passage.getTitle());
    }

    /**
     * Test that getLinks returns correct
     */
    @Test
    @DisplayName("Test that getLinks returns correct")
    void testThatGetLinksReturnsCorrect() {
      assertEquals(links, passage.getLinks());
    }

    /**
     * Test that addLink adds link
     */
    @Test
    @DisplayName("Test that addLink adds link")
    void testThatAddLinkAddsLink() {
      passage.addLink(link);
      links.add(link);
      assertEquals(links, passage.getLinks());
    }

    /**
     * Test that hasLinks returns correct
     */
    @Test
    @DisplayName("Test that hasLinks returns correct")
    void testThatHasLinksReturnsCorrect() {
      passage.addLink(link);
      assertTrue(passage.hasLinks());
    }
  }
}
