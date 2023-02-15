package edu.ntnu.idatt2001;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PassageTest {
  Passage p = new Passage("Test", "Test");
  Link link = new Link("Test1", "Test1");
  Link link2 = new Link("Test2", "Test2");
  List<Link> links = new ArrayList<>();


  @Test
  void getTitle() {
    assertEquals("Test", p.getTitle());
  }

  @Test
  void getContent() {
    assertEquals("Test", p.getContent());
  }

  @Test
  void addLink() {
    p.addLink(link);
    assertNotNull(p.getLinks());
  }
  @Test
  void getLinks() {
    links.add(link);
    links.add(link2);
    assertFalse(links.isEmpty());
  }
}
