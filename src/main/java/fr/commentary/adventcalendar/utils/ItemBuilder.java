package fr.commentary.adventcalendar.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.beans.ConstructorProperties;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder implements Cloneable {
    private ItemStack is;

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param material The material to create the ItemBuilder with.
     */
    @ConstructorProperties({"material"})
    public ItemBuilder(Material material)
    {
        this(material, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     *
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is)
    {
        this.is = is;
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material m, int amount)
    {
        is = new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from scratch with data.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     * @param data data like color, alternative.
     */
    public ItemBuilder(Material m, int amount, short data)
    {
        is = new ItemStack(m, amount, data);
    }

    /**
     * Create a new ItemBuilder for only skull head custom
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     * @param data data like color, alternative.
     * @param owner owner of the head.
     */
    public ItemBuilder(Material m, int amount, short data, String owner)
    {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(owner);
        skull.setItemMeta(skullMeta);
        is = skull;
    }

    /**
     * Clone the ItemBuilder into a new one.
     *
     * @return The cloned instance.
     */
    @Override
    public ItemBuilder clone()
    {
        return new ItemBuilder(is);
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     * @return
     */
    public ItemBuilder setName(String name)
    {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
    public ItemBuilder addLoreLine(String line)
    {
        ItemMeta im   = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add mutiple lore lines.
     *
     * @param lines The lore line to add.
     */
    public ItemBuilder addLoreLines(List<String> lines)
    {
        if(lines.size() == 0) return this;

        ItemMeta im   = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());

        lore.addAll(lines);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @param pos  The index of where to put it.
     */
    public ItemBuilder addLoreLine(String line, int pos)
    {
        ItemMeta     im   = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullTextures(String textures) {
        SkullMeta headMeta = (SkullMeta)this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", textures));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
            this.is.setItemMeta((ItemMeta)headMeta);
        } catch (NoSuchFieldException|IllegalAccessException ignored) {}
        return this;
    }

    /**
     * Retrieves the itemstack from the ItemBuilder.
     *
     * @return The itemstack created/modified by the ItemBuilder instance.
     */
    public ItemStack build()
    {
        return is;
    }
}