package eu.opencore.framework.language;

import eu.opencore.OpenCore;
import eu.opencore.framework.files.OpenCoreFile;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Language {

    private static List<Language> languages = new ArrayList<>();

    public static List<Language> getLanguages() {
        return languages;
    }

    public static Language getLanguageByName(String name) {
        for (Language language : getLanguages()) {
            String languageName = language.getLanguageName();
            if (name.toLowerCase().equals(languageName.toLowerCase())) return language;
        }
        return null;
    }

    private String languageName;
    private String complexName;

    private Material languageItem;
    private String displayName;

    public Language(OpenCore instance, String languageName, String complexName, Material languageItem, String displayName) {
        this.languageName = languageName;
        this.complexName = complexName;
        this.languageItem = languageItem;
        this.displayName = displayName;

        OpenCoreFile language = new OpenCoreFile(instance, "languages/" + complexName.toLowerCase() + ".yml");
        language.load();

        languages.add(this);
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getComplexName() {
        return complexName;
    }

    public Material getLanguageItem() {
        return languageItem;
    }

    public String getDisplayName() {
        return displayName;
    }
}
