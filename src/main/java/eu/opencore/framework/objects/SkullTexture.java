package eu.opencore.framework.objects;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullTexture {

    @Getter
    private ItemStack item;

    public SkullTexture(String textureAsString) {
        this.item = new ItemStack(Material.PLAYER_HEAD, 1);

        if (textureAsString == null) return;

        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", textureAsString));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        this.item.setItemMeta(skullMeta);
    }
}
