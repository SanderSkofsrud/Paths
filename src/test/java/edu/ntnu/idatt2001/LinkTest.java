package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import edu.ntnu.idatt2001.actions.GoldAction;
import edu.ntnu.idatt2001.actions.HealthAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

  Link link = new Link("Test", "Test");
  GoldAction goldAction = new GoldAction(10);
  HealthAction healthAction = new HealthAction(5);

  List<Action> actions = new ArrayList<>();

  @Test
  void testThatConstructorConstructsObject() {
    assertEquals(Link.class, link.getClass());
  }

  @Test
  void testThatConstructorThrowsNullException() {
    String text = null;
    String refrence = "Test";
    assertThrows(IllegalArgumentException.class, () -> Link link = new Link(text, refrence));

  }
  @Test
  void testThatConstructorThrowsIsBlankException() {
    String text = "Test";
    String refrence = null;
    assertThrows(IllegalArgumentException.class, () -> Link link = new Link(text, refrence));

  }

  @Test
  void testThatExceptionIsThrownWhenActionIsNull(){
    Action action = null;
    assertThrows(IllegalArgumentException.class, () -> link.addAction(action));
  }


  @Test
  void getText() {
    assertEquals(link.getText(), "Test");
  }

  @Test
  void getRefrence() {
    assertEquals(link.getRefrence(), "Test");
  }

  @Test
  void addAction() {
    link.addAction(goldAction);
    assertNotNull(link.getActions());
  }
}