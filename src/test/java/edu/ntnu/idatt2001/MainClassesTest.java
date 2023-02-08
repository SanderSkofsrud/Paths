package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.actions.Action;
import edu.ntnu.idatt2001.actions.GoldAction;
import edu.ntnu.idatt2001.actions.HealthAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainClassesTest {

  @BeforeAll
    static void initAll() {
  }
    @Nested
    class LinkTest {
      Link link = new Link("Test", "Test");
      GoldAction goldAction = new GoldAction(10);
      HealthAction healthAction = new HealthAction(5);

      List<Action> actions = new ArrayList<>();

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

      @Test
      void getActions() {
        actions.add(0, healthAction);
        actions.add(1, goldAction);
        assertFalse(actions.isEmpty());
      }
    }

    @Nested
    class PassageTest {

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

}
