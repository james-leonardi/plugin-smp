package me.pao.smp.PluginUtil;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class AntiFishFarming implements Listener {

    private final Main main;
    private static ArrayList<Player> fishing;

    public AntiFishFarming(){
        main = Main.getInstance();
        fishing = new ArrayList<>();
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)){
            if (C.fishFarmDelay == 0){ //Instant kick
                kickPlayer(e.getPlayer());
            } else { //Kick if clicked twice
                if (fishing.contains(e.getPlayer())){
                    fishing.remove(e.getPlayer());
                    kickPlayer(e.getPlayer());
                } else {
                    fishing.add(e.getPlayer());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> fishing.remove(e.getPlayer()), C.fishFarmDelay);
                }
            }
        }
    }

    private void kickPlayer(Player p){
        Bukkit.broadcastMessage(C.fishfarmBroadcast.replace("@PLAYER@",p.getName()));
        p.kickPlayer(C.fishfarmKickMessage);
    }

    @EventHandler
    public void onDisable(){
        fishing.clear();
    }
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        fishing.remove(e.getPlayer());
    }

    public static void clearLists(){
        fishing.clear();
    }
}
