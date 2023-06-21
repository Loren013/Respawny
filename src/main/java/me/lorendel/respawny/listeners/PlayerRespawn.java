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
        int i = 0;

        boolean suitable = false;
        while (!suitable && i < plugin.getConfig().getInt("max-times-check-spawn-location")){

        int x = p.getLocation().getBlockX() + ThreadLocalRandom.current().nextInt(-realRadius, realRadius + 1);
        int z = p.getLocation().getBlockZ() + ThreadLocalRandom.current().nextInt(-realRadius, realRadius + 1);

        Block block = p.getWorld().getHighestBlockAt(x, z);

        int y = block.getY() + 1;

        Material material = block.getType();
        int distance_x = p.getLocation().getBlockX() - x;
        int distance_z = p.getLocation().getBlockZ() - z;
        int half = (int)Math.pow(distance_x, 2) + (int)Math.pow(distance_z, 2);
        int real_distance = (int)Math.sqrt(half);

        if(!(block.isEmpty() || block.isLiquid()) && checkLeaves(material) && real_distance >= plugin.getConfig().getInt("minimum-distance-from-death")){

            Location newLocation = new Location(p.getWorld(), x, y, z);
            suitable = true;

            e.setRespawnLocation(newLocation);
            if(plugin.getConfig().getBoolean("show-message-on-respawn")){
            p.sendMessage(ChatColor.GREEN + "You have been spawned nearby to your death location! " + real_distance + " blocks away.");
            }
            }
        i++;
        }

        if(!suitable){
            e.setRespawnLocation(p.getWorld().getSpawnLocation());
            p.sendMessage("You have been spawned on default world spawn location!");
        }

    }
    public boolean checkLeaves(Material m) {
        if (m.toString().equals("BIRCH_LEAVES") || m.toString().equals("OAK_LEAVES") ||
                m.toString().equals("DARK_OAK_LEAVES") || m.toString().equals("JUNGLE_LEAVES") ||
                m.toString().equals("AZALEA_LEAVES") || m.toString().equals("SPRUCE_LEAVES")
                || m.toString().equals("FLOWERING_AZALEA") || m.toString().equals("MANGROVE_LEAVES")) {
            return false;
        } else {
            return true;
        }
    }
}