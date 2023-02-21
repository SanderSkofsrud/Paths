package edu.ntnu.idatt2001;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

  Player player = new Player("Test", 10, 30, 50);
  List<String> inventory = new ArrayList<>();
  String test = "Item";

  @Nested
  public class ConstructorTest {
    @Test
    @DisplayName("Test that constructor throws NullPointerException")
    void testThatConstructorThrowsNullPointerException() {
      int health = -1;
      assertThrows(IllegalArgumentException.class, () -> new Player("name", health, 10,20));
    }

    @Test
    @DisplayName("Test that constructor throws IllegalArgumentException when health is less than zero")
    void testThatConstructorThrowsIllegalArgumentException() {
      String name = null;
      assertThrows(NullPointerException.class, () -> new Player(name, 10,10,10));
    }
  }

  @Nested
  public class ExceptionHandling {

    @Test
    @DisplayName("Test that addHealth can not set negative value")
    void testThatAddHealthCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addHealth(-20));
    }

    @Test
    @DisplayName("Test that addScore can not set negative value")
    void testThatAddScoreCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addScore(-200));
    }

    @Test
    @DisplayName("Test that addGold can not set negative value")
    void testThatAddGoldCanNotSetNegativeValue() {
      assertThrows(IllegalArgumentException.class, () -> player.addGold(-200));
    }
  }

  @Nested
  public class addMethods {

    @Test
    @DisplayName("Test that Items will be added to inventory")
    void testThatItemsAddToInventory() {
      player.addToInventory("item");
      assertEquals(1, player.getInventory().size());
    }

    @Test
    @DisplayName("Test that addGold works")
    void testThatAddGoldWorks() {
      player.addGold(10);
      assertEquals(player.getGold(), 60);
    }

    @Test
    @DisplayName("Test that addScore works")
    void testThatAddScoreWorks() {
      player.addScore(10);
      assertEquals(player.getScore(), 40);
    }
  }


  @Nested
  public class ReturnValues {
    @Test
    @DisplayName("Test that getName returns correct")
    void testThatGetNameReturnsCorrect() {
      assertEquals(player.getName(), "Test");
    }

    @Test
    @DisplayName("Test that getHealth returns correct")
    void testThatGetHealthReturnsCorrect() {
      assertEquals(player.getHealth(), 10);
    }


    @Test
    @DisplayName("Test that getScore returns correct")
    void testThatGetScoreReturnsCorrect() {
      assertEquals(player.getScore(), 30);
    }


    @Test
    @DisplayName("Test that getGold returns correct")
    void testThatGetGoldReturnsCorrect() {
      assertEquals(player.getGold(), 50);
    }
  }
}

