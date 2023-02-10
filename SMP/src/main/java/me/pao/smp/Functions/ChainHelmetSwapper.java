package me.pao.smp.Functions;

import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ChainHelmetSwapper implements Listener {

    private final Main instance;
    public ChainHelmetSwapper(){
        instance = Main.getInstance();
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e){
        ItemStack i = e.getItemDrop().getItemStack();
        if (i.getType().equals(Material.IRON_HELMET)) {
            if (i.getDurability() == 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    i.setType(Material.CHAINMAIL_HELMET);
                    e.getPlayer().getWorld().playSound(e.getItemDrop().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
                    e.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getItemDrop().getLocation(), 20, 0.1, 0.2, 0.1, 0.2);
                }, 20L);
            }
        }
        if (i.getType().equals(Material.CHAINMAIL_HELMET)) {
            if (i.getDurability() == 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                    i.setType(Material.IRON_HELMET);
                    e.getPlayer().getWorld().playSound(e.getItemDrop().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 2);
                    e.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getItemDrop().getLocation(), 20, 0.1, 0.2, 0.1, 0.2);
                }, 20L);
            }
        }
    }
}
