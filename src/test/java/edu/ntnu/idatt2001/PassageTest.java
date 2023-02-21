package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PassageTest {
  Passage p = new Passage("Test", "Test");
  Link link = new Link("Test1", "Test1");
  List<Link> links = new ArrayList<>();

  @Nested
  public class Constructor {
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      assertEquals(Passage.class, p.getClass());
    }

    @Test
    @DisplayName("Test that constructor throws NullPointerException when title is null")
    void testThatConstructorThrowsNullPointerException() {
      String title = null;
      String content = "Test";
      assertThrows(NullPointerException.class, () -> new Passage(title, content));
    }
  }

  @Nested
  public class ExceptionHandling {
    @Test
    @DisplayName("Test that addLink throws IllegalArgumentException when link is null")
    void testThatExceptionIsThrownWhenLinkIsNull() {
      Link link = null;
      assertThrows(IllegalArgumentException.class, () -> p.addLink(link));
    }

  }
  @Nested
  public class ReturnValues {

    @Test
    @DisplayName("Test that getContent returns correct")
    void testThatGetContentReturnsCorrect() {
      assertEquals("Test", p.getContent());
    }

    @Test
    @DisplayName("Test that getLinks returns correct")
    void testThatLinksAreAddedToPassage() {
      p.addLink(link);
      assertTrue(p.getLinks().contains(link));
    }
  }
}
