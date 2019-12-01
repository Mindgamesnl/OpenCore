package eu.opencore.framework.language;

import eu.opencore.OpenCore;
import eu.opencore.framework.chat.ChatUtil;
import eu.opencore.framework.files.OpenCoreFile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Language {

    private static List<Language> languages = new ArrayList<>();

    public static List<Language> getLanguages() {
        return languages;
    }

    public static Language getLanguageByName(String name) {
        for (Language language : getLanguages()) {
            if (name.toLowerCase().equals(language.getLanguageName().toLowerCase())) return language;
            if (name.toLowerCase().equals(language.getComplexName().toLowerCase())) return language;
        }
        return null;
    }

    private String languageName;
    private String complexName;

    private ItemStack languageItem;
    private String displayName;

    public Language(OpenCore instance, String languageName, String complexName, ItemStack languageItem, String displayName) {
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

    public ItemStack getLanguageItemStack() {
        ItemStack itemStack = new ItemStack(languageItem);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.replaceColorCodes(displayName));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public String getDisplayName() {
        return displayName;
    }
}
