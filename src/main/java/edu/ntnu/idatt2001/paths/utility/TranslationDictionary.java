package edu.ntnu.idatt2001.paths.utility;

import edu.ntnu.idatt2001.paths.utility.Language;

import java.util.HashMap;
import java.util.Map;

public class TranslationDictionary {
  private static TranslationDictionary instance;
  private Map<Language, Map<String, String>> translations;

  private TranslationDictionary() {
    translations = new HashMap<>();
  }

  public static TranslationDictionary getInstance() {
    if (instance == null) {
      instance = new TranslationDictionary();
    }
    return instance;
  }

  public void addTranslation(Language language, String sourceText, String translatedText) {
    Map<String, String> languageTranslations = translations.getOrDefault(language, new HashMap<>());
    languageTranslations.put(sourceText, translatedText);
    translations.put(language, languageTranslations);
  }

  public String getTranslation(Language language, String sourceText) {
    Map<String, String> languageTranslations = translations.get(language);
    if (languageTranslations != null) {
      return languageTranslations.getOrDefault(sourceText, sourceText);
    }
    return sourceText;
  }
}

