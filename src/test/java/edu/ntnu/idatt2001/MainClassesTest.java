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


}
