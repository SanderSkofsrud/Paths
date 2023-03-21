package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import edu.ntnu.idatt2001.actions.GoldAction;
import edu.ntnu.idatt2001.actions.HealthAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Link
 */
class LinkTest {
  Link link;
  GoldAction goldAction;
  HealthAction healthAction;
  List<Action> actions;

  @BeforeEach
  void setUp() {
    link = new Link("Test", "reference");
    goldAction = new GoldAction(10);
    healthAction = new HealthAction(5);
    actions = new ArrayList<>();
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
      assertEquals(Link.class, link.getClass());
    }

    /**
     * Test that constructor throws NullPointerException when text is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when text is null")
    void testThatConstructorThrowsNullPointerException() {
      String text = null;
      String refrence = "Test";
      assertThrows(NullPointerException.class, () -> new Link(text, refrence));
    }

    /**
     * Test that constructor throws NullPointerException when reference is null
     */
    @Test
    @DisplayName("Test that constructor throws NullPointerException when reference is null")
    void testThatConstructorThrowsNullPointerExceptionWhenReferenceIsNull() {
      String text = "Test";
      String refrence = null;
      assertThrows(NullPointerException.class, () -> new Link(text, refrence));
    }
  }

  /**
   * Test class for exception handling
   */
  @Nested
  public class addAction {

    /**
     * Test that actions are added to link
     */
    @Test
    @DisplayName("Test that getActions returns correct")
    void testThatGetActionsReturnsCorrect() {
      actions.add(goldAction);
      actions.add(healthAction);
      link.addAction(goldAction);
      link.addAction(healthAction);
      assertEquals(link.getActions(), actions);
    }
    /**
     * Test that addAction throws IllegalArgumentException when action is null
     */
    @Test
    @DisplayName("Test that addAction throws IllegalArgumentException when action is null")
    void testThatExceptionIsThrownWhenActionIsNull() {
      Action action = null;
      assertThrows(IllegalArgumentException.class, () -> link.addAction(action));
    }
  }

  /**
   * Test class for file handling
   */
  @Nested
  class fileHandling {
    /**
     * Test that toString returns correct string and ensure ensure that the format is correct.
     */
    @Test
    @DisplayName("Test that toString returns correct string")
    void testThatToStringReturnsCorrectString() {
      assertEquals("["
              + "Test"
              + "]("
              + "reference"
              + ") \n", link.toString());
    }
  }
}