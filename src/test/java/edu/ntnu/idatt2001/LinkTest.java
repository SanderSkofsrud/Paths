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
     * Test that constructor constructs object and indirectly test that getters work
     */
    @Test
    @DisplayName("Test that constructor constructs object")
    void testThatConstructorConstructsObject() {
      assertEquals("Test", link.getText());
      assertEquals("reference", link.getReference());
      assertEquals(actions, link.getActions());
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
   * Test class addAction
   */
  @Nested
  public class addAction {

    /**
     * Test that addAction adds action without exceptions when a valid action is added
     */
    @Test
    @DisplayName("Test that addAction adds action without exceptions when a valid action is added")
    void testThatAddActionAddsAction() {
      link.addAction(goldAction);
      assertTrue(link.getActions().contains(goldAction));
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