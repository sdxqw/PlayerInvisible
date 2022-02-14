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
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    private static final String pluginPrefix = "[PlayerInvisible";
    ItemStack itemStack = new ItemStack(Material.FEATHER);

    // Enable Plugin
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(pluginPrefix + " Loading" + getDescription().getName());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // Player Join Event
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje) {
        Player p = pje.getPlayer();
        PlayerInventory pi = pje.getPlayer().getInventory();
        pi.addItem(itemStack);
    }

    // I Dont Know Whats Wrong But This Shit Dont Work LEL
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent pie) {

        // Get Player
        Player p = pie.getPlayer();

        // Get The Item In Hand
        if (pie.getPlayer().getItemInHand().getType() == Material.FEATHER) {

            // Get Action RIGHT_CLICK + AIR and BLOCK
            if (pie.getAction().equals(Action.RIGHT_CLICK_AIR) || pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                // Show Player
                if(p.canSee(p) == true) {
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        p = plr;
                        p.showPlayer(p);
                        p.sendMessage("Player are now visible");
                    }
                }

                // Hide Player
                else if(p.canSee(p) == false) {
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        p = plr;
                        p.hidePlayer(p);
                        p.sendMessage("Player are now invisible");
                    }
                }
            }
        }
    }
}
