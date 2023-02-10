package me.pao.smp.Functions;

import me.pao.smp.C;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class OffhandTorch implements Listener, CommandExecutor {

    private static ArrayList<Player> enable;
    public OffhandTorch(){
        enable = new ArrayList<>();
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a){
        if (s instanceof Player){
            Player p = (Player)s;
            if (l.equalsIgnoreCase("torch")){
                if (enable.contains(p)){
                    enable.remove(p);
                    p.sendMessage(C.torchDisable);
                } else {
                    enable.add(p);
                    p.sendMessage(C.torchEnable);
                }
            }
        }
        return true;
    }

    @EventHandler
    public void disableTorches(BlockPlaceEvent e){
        Material placed = e.getBlockPlaced().getType();
        Material inHand = e.getPlayer().getInventory().getItemInOffHand().getType();
        if (!enable.contains(e.getPlayer()) &&
                ((placed.equals(Material.WALL_TORCH) || placed.equals(Material.TORCH)) && inHand.equals(Material.TORCH)) ||
                ((placed.equals(Material.REDSTONE_WALL_TORCH) || placed.equals(Material.REDSTONE_TORCH)) && inHand.equals(Material.REDSTONE_TORCH)))
            e.setCancelled(true);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        enable.remove(e.getPlayer());
    }

    public static void clearLists(){
        enable.clear();
    }
}
