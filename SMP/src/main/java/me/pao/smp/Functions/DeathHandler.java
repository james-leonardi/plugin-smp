package me.pao.smp.Functions;

import me.pao.smp.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathHandler implements Listener {

    private Main instance;
    public DeathHandler(){
        instance = Main.getInstance();
    }
    @EventHandler
    public void f(PlayerDeathEvent e){
        e.setDeathMessage(ChatColor.RED + e.getDeathMessage());
        String msg = ChatColor.DARK_RED + "Death Coordinates: " + Math.round(e.getEntity().getLocation().getX()) + " " + Math.round(e.getEntity().getLocation().getY()) +
                " " + Math.round(e.getEntity().getLocation().getZ()) + " " + e.getEntity().getWorld().getName();
        e.getEntity().sendMessage(msg);
        instance.getLogger().info(e.getEntity().getName() + " | " + msg);

        /*for (Player p : Bukkit.getOnlinePlayers()){
            if (!p.getName().equals(e.getEntity().getName())){
                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        p.chat("f");
                    }
                },1L);
            }
        }*/
    }
}
