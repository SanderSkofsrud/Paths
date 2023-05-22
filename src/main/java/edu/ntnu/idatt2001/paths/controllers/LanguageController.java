package edu.ntnu.idatt2001.paths.controllers;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.Language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The type Language controller.
 * Singleton class for the language controller
 * Can be accessed from anywhere in the program.
 * This class is responsible for handling the language of the game.
 *
 * @author Helle R. and Sander S.
 * @version 1.1 15.05.2023
 */
public class LanguageController {

  /**
   * The constant instance.
   */
  public static LanguageController instance;
  /**
   * The Current language.
   * English is default
   */
  private Language currentLanguage = Language.EN;
  /**
   * The Google translate API
   */
  private Translate translate;
  /**
   * The Dictionary.
   * Contains all the words in the game
   * The key is the English word, and the value is the translated word
   */
  private Map<String, String> translations;

  /**
   * The constructor of the class.
   * Sets up the google translate API
   * Loads the config.properties file and uses the google.cloud.api.key to set up the API
   */
  private LanguageController() {
    translations = new HashMap<>();
    loadTranslations();

    Properties properties = new Properties();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (inputStream == null) {
        throw new FileNotFoundException("Property file 'config.properties' not found in the classpath");
      }
      properties.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }

    String apiKey = properties.getProperty("google.cloud.api.key");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalArgumentException("google.cloud.api.key is not set in config.properties");
    }

    TranslateOptions options = TranslateOptions.newBuilder().setProjectId("iron-lodge-386513").setApiKey(apiKey).build();
    this.translate = options.getService();
  }


  /**
   * Returns the instance of the class.
   *
   * @return the instance of the class
   */
  public static LanguageController getInstance() {
    if (instance == null) {
      instance = new LanguageController();
    }
    return instance;
  }

  /**
   * Gets current language enum.
   *
   * @return the current language enum
   */
  public Language getCurrentLanguageEnum() {
    return currentLanguage;
  }

  /**
   * Sets language.
   * Sets the current language
   * Loads the translations for the new language
   *
   * @param language the language
   */
  public void setLanguage(String language) {
    this.currentLanguage = Language.valueOf(language.toUpperCase());
    loadTranslations();
  }

  /**
   * Is internet available boolean.
   * Checks if the internet is available
   * Used to check if the google translate API can be used
   *
   * @return returns true if the internet is available, false if not
   */
  public boolean isInternetAvailable() {
    try {
      final URL url = new URL("http://www.google.com");
      final URLConnection conn = url.openConnection();
      conn.connect();
      conn.getInputStream().close();
      return true;
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      return false;
    }
  }


  /**
   * Translate string.
   * Translates the source text to the current language
   * If the internet is not available or the target language is the same as the source language, return the source text
   *
   * @param sourceText the text to be translated
   * @return the translated text
   */
  public String translate(String sourceText) {
    String language = this.currentLanguage.toString().toUpperCase();

    // If the internet is not available or the target language is the same as the source language, return the source text
    if (!isInternetAvailable() || this.currentLanguage.toString().equals(language)) {
      return sourceText;
    }

    Translation translation = translate.translate(
            sourceText,
            Translate.TranslateOption.targetLanguage(this.currentLanguage.toString().toLowerCase())
    );
    return translation.getTranslatedText();
  }

  /**
   * Translate to english string.
   * Translates the source text to english
   *
   * @param sourceText the text to be translated
   * @return the translated text
   */
  public String translateToEnglish(String sourceText) {
    String language = this.currentLanguage.toString().toUpperCase();

    // If the internet is not available or the target language is the same as the source language, return the source text
    if (!isInternetAvailable() || this.currentLanguage.toString().equals(language)) {
      return sourceText;
    }

    Translation translation = translate.translate(
            sourceText,
            Translate.TranslateOption.targetLanguage("en")
    );
    return translation.getTranslatedText();
  }

  /**
   * Load translations.
   * Loads the translations for the current language
   * Stores the translations in the dictionary
   */
  private void loadTranslations() {
    translations.clear();

    // Translate and store the strings
    for (Dictionary key : Dictionary.values()) {
      String sourceText = key.getKey();
      String translatedText = translateToLanguage(sourceText, currentLanguage);
      translations.put(sourceText, translatedText);
    }
  }

  /**
   * Translate to language string.
   * Translates the source text to the target language
   *
   * @param sourceText the text to be translated
   * @param language   the target language
   * @return the translated text
   */
  private String translateToLanguage(String sourceText, Language language) {
    String languageCode = language.toString().toLowerCase();
    if (languageCode.equals("en")) {
      return sourceText;
    }

    Translation translation = translate.translate(
            sourceText,
            Translate.TranslateOption.targetLanguage(languageCode)
    );
    return translation.getTranslatedText();
  }

  /**
   * Gets translation.
   * Returns the translation for the source text
   *
   * @param sourceText the text to get the translation for
   * @return the translation for the source text
   */
  public String getTranslation(String sourceText) {
    return translations.getOrDefault(sourceText, sourceText);
  }
}
