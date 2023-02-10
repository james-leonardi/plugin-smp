package me.pao.smp.Functions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HorseStats implements Listener {

    @EventHandler
    public void horseStats(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (damager instanceof Player && (entity instanceof Horse || entity instanceof ZombieHorse || entity instanceof SkeletonHorse || entity instanceof Mule || entity instanceof Donkey)){
            Player p = (Player)damager;
            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                AbstractHorse horse = (AbstractHorse)entity;
                p.sendMessage("");
                p.sendMessage(ChatColor.BLUE + "Horse ID " + ChatColor.AQUA + horse.getEntityId());

                int currentHp = (int)horse.getHealth();
                int maxHp = (int)horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                p.sendMessage(ChatColor.BLUE + "Health " + ChatColor.AQUA + currentHp + "/" + maxHp);

                double moveSpeed = (10000*horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
                double percent = round(100.0D * horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() / 0.1D - 100.0D,2);
                p.sendMessage(ChatColor.BLUE + "Movement Speed " + ChatColor.AQUA + round(43.09624579*(moveSpeed/10000)+0.0043613446,1) + " blocks/second");

                double jumpHeight = (100*horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue());
                p.sendMessage(ChatColor.BLUE + "Jump Height " + ChatColor.AQUA + round(7.015*(jumpHeight/100)-1.8865,2) + " blocks");

                e.setCancelled(true);
            }
        }
    }

    private double round(double value, int decimal) {
        double magicNumber = decimal > 0 ? Math.pow(10.0D, decimal) : 1.0D;
        return Math.round(value * magicNumber) / magicNumber;
    }
}
