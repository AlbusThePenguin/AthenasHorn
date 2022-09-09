package me.albusthepenguin.athenashorn.events;

import me.albusthepenguin.athenashorn.ColorUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class HornEvent implements Listener {
    private Plugin plugin;

    private String titleMessage;
    private String subtitleMessage;
    private String chatMessage;
    private String prefix;
    private int zCords;
    private int xCords;
    private int yCords;

    private int fadeIn;
    private int fadeOut;
    private int stay;

    private boolean useMessages;
    private boolean useTitle;

    private String Permission;

    public HornEvent(Plugin plugin) {
        this.plugin = plugin;

        prefix = ColorUtils.translateColorCodes(plugin.getConfig().getString("Settings.prefix")) + " ";


        chatMessage = ColorUtils.translateColorCodes(plugin.getConfig().getString("Messages.default"));
        titleMessage = ColorUtils.translateColorCodes(plugin.getConfig().getString("Title.title"));
        subtitleMessage = ColorUtils.translateColorCodes(plugin.getConfig().getString("Title.subtitle"));

        Permission = plugin.getConfig().getString("Settings.permission");

        fadeIn = plugin.getConfig().getInt("Title.fadeIn");
        fadeOut = plugin.getConfig().getInt("Title.fadeOut");
        stay =  plugin.getConfig().getInt("Title.stay");

        zCords = plugin.getConfig().getInt("Settings.zCord");
        xCords = plugin.getConfig().getInt("Settings.xCord");
        yCords = plugin.getConfig().getInt("Settings.yCord");

        useMessages = plugin.getConfig().getBoolean("Messages.use");
        useTitle = plugin.getConfig().getBoolean("Title.use");


    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission(Permission)) {
            if(ClickType.RIGHT.isRightClick() && p.getInventory().getItemInMainHand().getType() == Material.GOAT_HORN) {
                Location location = p.getLocation();
                if(!p.hasCooldown(Material.GOAT_HORN)) {
                    for (Entity entity : e.getPlayer().getWorld().getNearbyEntities(location, xCords, yCords, zCords)) {
                        if (entity instanceof Goat && entity instanceof LivingEntity) {
                            entityMove(entity, location);
                            // LOL ((Goat) entity).setScreaming(true);
                        }
                    }

                    if(useMessages) {
                        p.sendMessage(prefix + chatMessage);
                    }

                    if(useTitle) {
                        p.sendTitle(titleMessage, subtitleMessage, fadeIn, stay, fadeOut);
                    }
                }
            }
        }
        return;
    }

    public void entityMove(Entity entity, Location location) {
        ((Mob)entity).getPathfinder().moveTo(location);
    }
}