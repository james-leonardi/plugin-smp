package me.pao.smp.UsableItems;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SmokeBomb implements Listener {

    private final Main main;

    public SmokeBomb(){
        main = Main.getInstance();
    }
    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        ItemStack i = e.getItemDrop().getItemStack();
        if (i.getType().equals(Material.FIREWORK_STAR)) {
            if (i.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Smoke Bomb")) {
                i.setAmount(i.getAmount() - 1);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.hidePlayer(main, e.getPlayer());
                }
                e.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getPlayer().getLocation(), 2000, 0.5, 1.5, 0.5, 0.5);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 2);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, C.smokeBombDuration, 0, false, false));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, C.smokeBombDuration, 1, false, false));
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(main, e.getPlayer());
                    }
                }, C.smokeBombDuration);
            }

        }
    }
}