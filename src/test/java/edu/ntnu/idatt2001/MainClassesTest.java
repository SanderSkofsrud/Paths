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

    @Nested
    class PlayerTest {

      Player player = new Player("Test", 10, 30, 50);
      List<String> inventory = new ArrayList<>();
      String test = "Item";

      @Test
      void getName() {
        assertEquals(player.getName(), "Test");
      }

      @Test
      void addHealth() {
        player.addHealth(10);
        assertEquals(player.getHealth(),20);

      }

      @Test
      void getHealth() {
        assertEquals(player.getHealth(), 10);
      }

      @Test
      void addScore() {
        player.addScore(10);
        assertEquals(player.getScore(),40);
      }

      @Test
      void getScore() {
        assertEquals(player.getScore(), 30);
      }

      @Test
      void addGold() {
        player.addGold(10);
        assertEquals(player.getGold(),60);
      }

      @Test
      void getGold() {
        assertEquals(player.getGold(), 50);
      }

      @Test
      void addToInventory() {
        player.addToInventory(test);
        assertNotNull(player.getInventory());
      }

      @Test
      void getInventory() {
        inventory.add(test);
        assertFalse(inventory.isEmpty());
      }
    }
    @Nested
    class GameTest {

      @org.junit.jupiter.api.Test
      void getPlayer() {
      }

      @org.junit.jupiter.api.Test
      void getStory() {
      }

      @org.junit.jupiter.api.Test
      void getGoals() {
      }

      @org.junit.jupiter.api.Test
      void begin() {
      }

      @org.junit.jupiter.api.Test
      void go() {
      }
    }


    @Nested
    class StoryTest {

      @Test
      void getTitle() {
      }

      @Test
      void getPassages() {
      }

      @Test
      void getOpeningPassage() {
      }

      @Test
      void addPassage() {
      }

      @Test
      void getPassage() {
      }
    }

    }
