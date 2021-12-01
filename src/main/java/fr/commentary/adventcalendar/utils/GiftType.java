package fr.commentary.adventcalendar.utils;

import fr.commentary.adventcalendar.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum GiftType {

    GET(
            "get",
            Material.PLAYER_HEAD,
            1,
            0,
            "§a§lGet",
            Arrays.asList("Line 1", "Line 2"),
            true,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0="
    ),
    MISS(
            "miss",
            Material.PLAYER_HEAD,
            1,
            0,
            "§a§lMiss",
            Arrays.asList("Line 1", "Line 2"),
            true,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY5ZDlkZTYyZWNhZTliNzk4NTU1ZmQyM2U4Y2EzNWUyNjA1MjkxOTM5YzE4NjJmZTc5MDY2Njk4Yzk1MDhhNyJ9fX0="
    ),
    WAITING(
            "waiting",
            Material.PLAYER_HEAD,
            1,
            0,
            "§a§lWaiting",
            Arrays.asList("Line 1", "Line 2"),
            true,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVhMzc1NWE2ZmUwMTlhMTczY2UzYTQzMDcwNDUyZTc2Nzc2OGQ1NzU1OWQwNGI3M2UyMWI5MDNlYWExYmQ4MiJ9fX0="
    ),
    SUCCESS(
            "success",
            Material.PLAYER_HEAD,
            1,
            0,
            "§a§lSuccess",
            Arrays.asList("Line 1", "Line 2"),
            true,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjVhM2I0OWJlZWMzYWIyM2FlMGI2MGRhYjU2ZTljYzhmYTE2NzY5YTI1ODMwYjVkOGQ2YzQ2Mzc4ZjU0NDMwIn19fQ=="
    );

    @Getter
    @Setter
    private String typeName;

    @Getter
    @Setter
    private Material material;

    @Getter
    @Setter
    private int amount;

    @Getter
    @Setter
    private int data;

    @Getter
    @Setter
    private String name;

    @Setter
    private List<String> lore;

    private boolean loreActive;

    @Getter
    @Setter
    private String texture;

    GiftType(String typeName, Material material, int amount, int data, String name, List<String> lore, boolean loreActive, String texture) {
        this.typeName = typeName;
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.name = name;
        this.lore = lore;
        this.loreActive = loreActive;
        this.texture = texture;

        reload();
    }

    public List<String> getLore() {
        List<String> loreS = new ArrayList<>();

        if (loreActive) {
            lore.forEach(s -> loreS.add(s.replaceAll("&", "§")));
        }

        return loreS;
    }

    public void reload() {
        this.material = Material.matchMaterial(Objects.requireNonNull(Main.getInstance().getConfig().getString("gui.items." + typeName + ".material")));
        this.amount = Main.getInstance().getConfig().getInt("gui.items." + typeName + ".amount");
        this.data = Main.getInstance().getConfig().getInt("gui.items." + typeName + ".data");
        this.name = Main.getInstance().getConfig().getString("gui.items." + typeName + ".name");
        this.lore = Main.getInstance().getConfig().getStringList("gui.items." + typeName + ".lore");
        this.loreActive = Main.getInstance().getConfig().getBoolean("gui.items." + typeName + ".lore-active");
        this.texture = Main.getInstance().getConfig().getString("gui.items." + typeName + ".texture");
    }

}
