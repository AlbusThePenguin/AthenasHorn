package me.albusthepenguin.athenashorn;

import me.albusthepenguin.athenashorn.events.HornEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class AthenasHorn extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        if (getConfig().getBoolean("Metrics")) {
            int pluginId = 16384; // <-- Replace with the id of your plugin!
            Metrics metrics = new Metrics(this, pluginId);
        }
        // Optional: Add custom charts
        //metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        getServer().getPluginManager().registerEvents(new HornEvent(this), this);
        Bukkit.getConsoleSender().sendMessage(ColorUtils.translateColorCodes("&e&l[&b&lAthenas &8&lHorn&e&l] &a&lEnabled &a&lVersion &6&l" + getDescription().getVersion()));

        if(!paperMC()) {
            Bukkit.getConsoleSender().sendMessage("&e&l[&b&lAthenas &8&lHorn&e&l] &4&lRequires &6&lPaperMC &4&lto work");
            Bukkit.getLogger().info("[Athenas Horn] This plugin requires PaperMC.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.translateColorCodes("&e&l[&b&lAthenas &8&lHorn&e&l] &4&lDisabled &a&lVersion &6&l" + getDescription().getVersion()));
    }

    public boolean paperMC() {
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

}
