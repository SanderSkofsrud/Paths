package edu.ntnu.idatt2001.paths.models;

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
  class Constructor {
    /**
     * Test that constructor constructs object with valid values and indirectly test that getters work
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      assertEquals("title", passage.getTitle());
      assertEquals("content", passage.getContent());
      assertEquals(links, passage.getLinks());
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
  class addLinks {

    /**
     * Test that addLinks adds link to passage without exceptions when link is not null
     */
    @Test
    @DisplayName("Test that addLinks works without exceptions when link is not null")
    void testThatAddLinksWorks() {
      assertTrue(!passage.hasLinks());
      passage.addLink(link);
      assertTrue(passage.hasLinks());
    }

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
  class hasLinks {

    @Test
    @DisplayName("Test that hasLinks returns false when no links")
    void testThatHasLinksReturnsFalseWhenNoLinks() {
      assertFalse(passage.hasLinks());
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

  /**
   * Test class for file handling
   */
  @Nested
  class fileHandling {

    /**
     * Test that toString for Passage returns correct and ensure that the format is correct.
     */
    @Test
    @DisplayName("Test that toString returns correct")
    void testThatToStringReturnsCorrect() {
      passage.addLink(link);
      assertEquals("\n::"
              + "title"
              + "\n"
              + "content"
              + "\n"
              + "["
              + "link"
              + "]("
              + "link"
              + ")\n", passage.toString());
    }
  }
}
