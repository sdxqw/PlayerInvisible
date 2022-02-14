package org.xnotro.playerinvisible;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    // Config Statements
    public static String invisible;
    public static String visible;
    public static String prefix;
    public static String perms;

    // Plugin Prefix
    private static final String pluginPrefix = "[PlayerInvisible]";

    // ItemStack
    ItemStack itemStack = new ItemStack(Material.FEATHER);

    // Enable Plugin
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Loading" + getDescription().getName());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Loading Events");
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Loading Config");
        initialize();
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Config Initialized");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Unloading" + getDescription().getName());
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Saving Config");
    }

    // Player Join Event
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje) {
        Player p = pje.getPlayer();
        PlayerInventory pi = pje.getPlayer().getInventory();
        pi.addItem(itemStack);
    }

    // Player Interact Event
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent pie) {

        // Get Player
        Player p = pie.getPlayer();

        // Get The Item In Hand
        if (pie.getPlayer().getItemInHand().getType() == Material.FEATHER) {

            // Get Action RIGHT_CLICK + AIR and BLOCK
            if (pie.getAction().equals(Action.RIGHT_CLICK_AIR) || pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                if (!p.hasPermission("plrinvs")) {
                    // Make You Invisible
                    if (!p.hasMetadata("invisible")) {
                        for (Player ps : Bukkit.getOnlinePlayers()) {
                            ps.hidePlayer(p);
                        }
                        p.setMetadata("invisible", new FixedMetadataValue(this, true));
                        p.sendMessage(prefix + invisible);

                        // Make You Visible
                    } else {
                        for (Player ps : Bukkit.getOnlinePlayers()) {
                            ps.showPlayer(p);
                        }
                        p.removeMetadata("visible", this);
                        p.sendMessage(prefix + visible);
                    }
                } else {
                    p.sendMessage(prefix + perms);
                }
            }
        }
    }

    // Config Initialize
    public void initialize() {
        invisible = getConfig().getString("messages.invisible");
        visible = getConfig().getString("messages.visible");
        prefix = getConfig().getString("messages.prefix");
        perms = getConfig().getString("messages.perms");
    }
}

