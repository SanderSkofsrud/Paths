package edu.ntnu.idatt2001;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

  Player player = new Player("Test", 10, 30, 50);
  List<String> inventory = new ArrayList<>();
  String test = "Item";

  @Test
  void testThatConstructorThrowsNullPointerException() {
    int health = -1;
    assertThrows(IllegalArgumentException.class, () -> new Player("name", health, 10,20));
  }

  @Test
  void testThatConstructorThrowsIllegalArgumentException() {
    String name = null;
    assertThrows(NullPointerException.class, () -> new Player(name, 10,10,10));
  }

  @Test
  void testThatAddHealthCanNotSetNegativeValue() {
    assertThrows(IllegalArgumentException.class, () -> player.addHealth(-20));
  }

  @Test
  void testThatItemsAddToInventory() {
    player.addToInventory("item");
    assertEquals(1, player.getInventory().size());
  }

  @Test
  void testThatGetNameReturnsCorrect() {
    assertEquals(player.getName(), "Test");
  }

  @Test
  void testThatAddHealthReturnsCorrect() {
    player.addHealth(10);
    assertEquals(player.getHealth(),20);

  }

  @Test
  void testThatGetHealthReturnsCorrect() {
    assertEquals(player.getHealth(), 10);
  }

  @Test
  void testThatAddScoreReturnsCorrect() {
    player.addScore(10);
    assertEquals(player.getScore(),40);
  }

  @Test
  void testThatGetScoreReturnsCorrect() {
    assertEquals(player.getScore(), 30);
  }

  @Test
  void testThatAddGoldReturnsCorrect() {
    player.addGold(10);
    assertEquals(player.getGold(),60);
  }

  @Test
  void testThatGetGoldReturnsCorrect() {
    assertEquals(player.getGold(), 50);
  }

}

