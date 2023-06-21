package me.lorendel.respawny.listeners;

import me.lorendel.respawny.Respawny;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerRespawn implements Listener {

    Respawny plugin = Respawny.getPlugin(Respawny.class);

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){

        int radius = plugin.getConfig().getInt("radius-of-respawn");
        int realRadius = Math.abs(radius);

        Player p = e.getPlayer();
        Location currentLocation = p.getLocation();

        boolean safe = false;
        while (!safe){

        int x = p.getLocation().getBlockX() + ThreadLocalRandom.current().nextInt(-realRadius, realRadius + 1);
        int z = p.getLocation().getBlockZ() + ThreadLocalRandom.current().nextInt(-realRadius, realRadius + 1);

        Block block = p.getWorld().getHighestBlockAt(x, z);

        int y = block.getY() + 1;

        Material material = block.getType();

        if(material != Material.WATER && material != Material.LAVA && material != Material.ACACIA_LEAVES
                && material != Material.BIRCH_LEAVES && material != Material.OAK_LEAVES && material != Material.DARK_OAK_LEAVES
                && material != Material.JUNGLE_LEAVES && material != Material.AZALEA_LEAVES && material != Material.SPRUCE_LEAVES
                && material != Material.FLOWERING_AZALEA && material != Material.MANGROVE_LEAVES){

            safe = true;
            Location newLocation = new Location(p.getWorld(), x, y, z);
            e.setRespawnLocation(newLocation);
            p.sendMessage(ChatColor.GREEN + "You were spawned nearby to your death location!");

            }

        }
    }
}

