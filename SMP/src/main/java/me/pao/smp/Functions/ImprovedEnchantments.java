package me.pao.smp.Functions;

import me.pao.smp.Main;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class ImprovedEnchantments implements Listener {

    private final Main instance;
    private final ArrayList<Player> pvpList;
    public ImprovedEnchantments(){
        instance = Main.getInstance();
        pvpList = TogglePvP.getPvp();
    }
    @EventHandler
    public void damage(EntityDamageByEntityEvent e){
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (instance.getConfig().getBoolean("enchants") && damager instanceof Player && entity instanceof LivingEntity){
            Player p = (Player)damager;
            LivingEntity en = (LivingEntity)entity;
            boolean pass = true;
            if (en instanceof Player){
                Player damaged = (Player)en;
                if (!(pvpList.contains(damaged) && pvpList.contains(damager))){
                    pass = false;
                }
            }
            ItemStack is = p.getInventory().getItemInMainHand();
            Random r = new Random();

            if (pass){
                //Smite
                if (is.containsEnchantment(Enchantment.DAMAGE_UNDEAD)){
                    int level = is.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                    if (level * 15 >= r.nextInt(99)+1){
                        p.getWorld().strikeLightningEffect(en.getLocation());
                        try {
                            en.setHealth(en.getHealth()-e.getDamage());
                        } catch (IllegalArgumentException ignored){}
                    }
                }

                //Bane of Arthropods
                if (is.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
                    int level = is.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
                    en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (level * 20) + 20, 2, false, false),true);
                }
            }
        }
    }
}
