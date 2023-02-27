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

  Link link = new Link("Test", "reference");
  GoldAction goldAction = new GoldAction(10);
  HealthAction healthAction = new HealthAction(5);

  List<Action> actions = new ArrayList<>();

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

  }

  /**
   * Test class for exception handling
   */
  @Nested
  public class ExceptionHandling {
    /**
     * Test that addAction throws IllegalArgumentException when action is null
     */
    @Test
    @DisplayName("Test that addAction throws IllegalArgumentException when action is null")
    void testThatExceptionIsThrownWhenActionIsNull() {
      Action action = null;
      assertThrows(IllegalArgumentException.class, () -> link.addAction(action));
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
   * Test class for return values
   */
  @Nested
  public class ReturnValues {
    /**
     * Test that getText returns correct
     */
    @Test
    @DisplayName("Test that getText returns correct")
    void testThatGetTextReturnsCorrect() {
      assertEquals(link.getText(), "Test");
    }

    /**
     * Test that getReference returns correct
     */
    @Test
    @DisplayName("Test that getReference returns correct")
    void testThatGetReferenceReturnsCorrect() {
      assertEquals(link.getReference(), "reference");
    }

    /**
     * Test that getActions returns correct
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
  }

}