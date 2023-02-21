package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import edu.ntnu.idatt2001.actions.GoldAction;
import edu.ntnu.idatt2001.actions.HealthAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

  Link link = new Link("Test", "reference");
  GoldAction goldAction = new GoldAction(10);
  HealthAction healthAction = new HealthAction(5);

  List<Action> actions = new ArrayList<>();

  @Test
  void testThatConstructorConstructsObject() {
    assertEquals(Link.class, link.getClass());
  }

  @Test
  void testThatConstructorThrowsNullPointerException() {
    String text = null;
    String refrence = "Test";
    assertThrows(NullPointerException.class, () -> new Link(text, refrence));
  }

  @Test
  void testThatExceptionIsThrownWhenActionIsNull(){
    Action action = null;
    assertThrows(IllegalArgumentException.class, () -> link.addAction(action));
  }


  @Test
  void testThatGetTextReturnsCorrect() {
    assertEquals(link.getText(), "Test");
  }

  @Test
  void testThatGetReferenceReturnsCorrect() {
    assertEquals(link.getReference(), "reference");
  }

  @Test
  void testThatGetActionsReturnsCorrect() {
    actions.add(goldAction);
    actions.add(healthAction);
    link.addAction(goldAction);
    link.addAction(healthAction);
    assertEquals(link.getActions(), actions);
  }
}