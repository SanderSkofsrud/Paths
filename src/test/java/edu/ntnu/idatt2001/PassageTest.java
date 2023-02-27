package edu.ntnu.idatt2001;

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
  Passage p = new Passage("Test", "Test");
  Link link = new Link("Test1", "Test1");
  List<Link> links = new ArrayList<>();

  /**
   * Test class for constructor
   */
  @Nested
  public class Constructor {
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      assertEquals(Passage.class, p.getClass());
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
      assertThrows(NullPointerException.class, () -> p.addLink(link));
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
      assertEquals("Test", p.getContent());
    }

    /**
     * Test that getTitle returns correct
     */
    @Test
    @DisplayName("Test that getTitle returns correct")
    void testThatGetTitleReturnsCorrect() {
      assertEquals("Test", p.getTitle());
    }

    /**
     * Test that getLinks returns correct
     */
    @Test
    @DisplayName("Test that getLinks returns correct")
    void testThatGetLinksReturnsCorrect() {
      assertEquals(links, p.getLinks());
    }

    /**
     * Test that addLink adds link
     */
    @Test
    @DisplayName("Test that addLink adds link")
    void testThatAddLinkAddsLink() {
      p.addLink(link);
      links.add(link);
      assertEquals(links, p.getLinks());
    }

    /**
     * Test that hasLinks returns correct
     */
    @Test
    @DisplayName("Test that hasLinks returns correct")
    void testThatHasLinksReturnsCorrect() {
      p.addLink(link);
      assertTrue(p.hasLinks());
    }
  }
}
