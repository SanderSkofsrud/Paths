package edu.ntnu.idatt2001;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PassageTest {
  Passage p = new Passage("Test", "Test");
  Link link = new Link("Test1", "Test1");
  List<Link> links = new ArrayList<>();


  @Test
  void testThatConstructorThrowsNullPointerException() {
    String title = null;
    String content = "Test";
    assertThrows(NullPointerException.class, () -> new Passage(title, content));
  }

  @Test
  void testThatGetTitleReturnsCorrect() {
    assertEquals("Test", p.getTitle());
  }

  @Test
  void testThatGetContentReturnsCorrect() {
    assertEquals("Test", p.getContent());
  }

  @Test
  void testThatLinksAreAddedToPassage() {
    p.addLink(link);
    assertTrue(p.getLinks().contains(link));
  }
}
