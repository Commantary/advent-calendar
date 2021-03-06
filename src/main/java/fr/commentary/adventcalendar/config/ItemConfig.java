package fr.commentary.adventcalendar.config;

import fr.commentary.adventcalendar.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ItemConfig {

    public String id;

    @Getter
    public Material material;

    @Getter
    public int amount;

    @Getter
    public int data;

    private String name;

    private List<String> lore;

    private boolean loreActive;

    public ItemConfig(String id) {
        this.id = id;

        reload();
    }

    public void reload() {
        Main main = Main.getInstance();

        this.material = Material.getMaterial(Objects.requireNonNull(main.getConfig().getString("gui.items." + this.id + ".material")));
        this.amount = main.getConfig().getInt("gui.items." + this.id + ".amount");
        this.data = main.getConfig().getInt("gui.items." + this.id + ".data");
        this.name = main.getConfig().getString("gui.items." + this.id + ".name");
        this.lore = main.getConfig().getStringList("gui.items." + this.id + ".lore");
        this.loreActive = main.getConfig().getBoolean("gui.items." + this.id + ".lore-active");
    }

    public String getName() {
        return name.replaceAll("&", "§");
    }

    public List<String> getLore() {
        List<String> loreS = new ArrayList<>();

        if (loreActive) {
            lore.forEach(s -> loreS.add(s.replaceAll("&", "§")));
        }

        return loreS;
    }
}
