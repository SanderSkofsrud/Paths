package edu.ntnu.idatt2001.paths.controllers;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import edu.ntnu.idatt2001.paths.utility.Dictionary;
import edu.ntnu.idatt2001.paths.utility.Language;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LanguageController {

  public static LanguageController instance;
  private Language currentLanguage = Language.EN;  // Default language
  private Translate translate;
  private Map<String, String> translations;

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


  public static LanguageController getInstance() {
    if (instance == null) {
      instance = new LanguageController();
    }
    return instance;
  }

  public String getCurrentLanguage() {
    return currentLanguage.toString().toUpperCase();
  }
  public Language getCurrentLanguageEnum() {
    return currentLanguage;
  }

  public void setLanguage(String language) {
    this.currentLanguage = Language.valueOf(language.toUpperCase());
    loadTranslations();
  }

  public boolean isInternetAvailable() {
    try {
      InetAddress address = InetAddress.getByName("8.8.8.8");
      // Try to reach Google's DNS server with a 2 second timeout
      return address.isReachable(2000);
    } catch (Exception e) {
      // If any exception occurs (e.g., UnknownHostException or IOException),
      // then we're probably not connected to the internet
      return false;
    }
  }

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

  private void loadTranslations() {
    translations.clear();

    // Translate and store the strings
    for (Dictionary key : Dictionary.values()) {
      String sourceText = key.getKey();
      String translatedText = translateToLanguage(sourceText, currentLanguage);
      translations.put(sourceText, translatedText);
    }
  }

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

  public String getTranslation(String sourceText) {
    return translations.getOrDefault(sourceText, sourceText);
  }
}
