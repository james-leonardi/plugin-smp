package me.pao.smp.Functions;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;


public class TogglePvP implements Listener, CommandExecutor {

    private final Main instance;
    private static ArrayList<Player> pvp = new ArrayList<>();
    public TogglePvP(){
        instance = Main.getInstance();
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a){
        if (s instanceof Player){
            Player p = (Player)s;
            if (l.equalsIgnoreCase("pvp")){
                if (C.pvpEnabled){
                    p.sendMessage(C.pvpAlwaysOn);
                    return true;
                }
                if (pvp.contains(p)){
                    pvp.remove(p);
                    p.sendMessage(C.pvpDisable);
                } else {
                    pvp.add(p);
                    p.sendMessage(C.pvpEnable);
                }
            }
        }
        return true;
    }

    @EventHandler
    public void damagePlayer(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (damager instanceof Player && entity instanceof Player){
            Player one = (Player) damager;
            Player two = (Player) entity;
            if (!two.canSee(one)) two.showPlayer(instance,one);
            if (!pvp.contains(one) || !pvp.contains(two)) e.setCancelled(true);
            if (C.pvpEnabled) e.setCancelled(false);
            return;
        }
        if (damager instanceof Arrow && entity instanceof Player) {
            Arrow arrow = (Arrow) damager;
            Player p = (Player) entity;
            if (arrow.getShooter() instanceof Player){
                Player one = (Player) arrow.getShooter();
                if (!p.canSee(one)) p.showPlayer(instance,one);
                if (!pvp.contains(p) || !pvp.contains(one)) e.setCancelled(true);
                if (C.pvpEnabled) e.setCancelled(false);
            }
        }
    }
    @EventHandler
    public void leave(PlayerQuitEvent e){
        pvp.remove(e.getPlayer());
    }
    public static ArrayList<Player> getPvp(){
        return pvp;
    }

    public static void clearLists(){
        pvp.clear();
    }
}