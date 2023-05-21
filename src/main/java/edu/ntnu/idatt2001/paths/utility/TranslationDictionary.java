package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.utility.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a translation dictionary.
 * The class is used to store translations of text for different languages.
 * It implements the singleton pattern, ensuring that only one instance of the dictionary
 * is created throughout the application. It provides methods to add translations for specific languages
 * and retrieve translations based on the language and source text.
 */

public class TranslationDictionary {
  private static TranslationDictionary instance;
  private Map<Language, Map<String, String>> translations;

  /**
   * Constructor for TranslationDictionary.
   * Private constructor to enforce singleton pattern and initialize the translations map.
   */
  private TranslationDictionary() {
    translations = new HashMap<>();
  }

  /**
   * Returns the instance of the TranslationDictionary.
   * If the instance does not exist, a new instance is created.
   *
   * @return the instance of the TranslationDictionary
   */
  public static TranslationDictionary getInstance() {
    if (instance == null) {
      instance = new TranslationDictionary();
    }
    return instance;
  }
}

