package me.pao.smp.Functions;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class Disenchant implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent e){
        try {
            if (e.getClickedInventory().getType().equals(InventoryType.ANVIL)){
                AnvilInventory anvil = (AnvilInventory) e.getClickedInventory();
                if (e.getInventory().getItem(0) != null && e.getInventory().getItem(1) != null){
                    ItemStack left = anvil.getItem(0);
                    ItemStack right = anvil.getItem(1);
                    if (left.getType().equals(Material.BOOK) && e.getSlot() == 2){
                        if (anvil.getItem(0).getType().equals(Material.BOOK) && anvil.getItem(0).getAmount() == 1 && anvil.getItem(2).getType().equals(Material.ENCHANTED_BOOK)){
                            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                            EnchantmentStorageMeta esm = (EnchantmentStorageMeta) book.getItemMeta();
                            for (Map.Entry<Enchantment, Integer> ench : right.getEnchantments().entrySet())
                            {
                                esm.addStoredEnchant(ench.getKey(), ench.getValue(), true);
                            }
                            book.setItemMeta(esm);
                            e.getWhoClicked().getInventory().addItem(book);
                            e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
                            anvil.setItem(0,null);
                            anvil.setItem(1,null);
                            anvil.setItem(2,null);
                        }
                    }
                }
            }
        } catch (NullPointerException ignored){}
    }
    @EventHandler
    public void anvil(PrepareAnvilEvent e){
        if (e.getInventory().getItem(0) != null && e.getInventory().getItem(1) != null){
            ItemStack left = e.getInventory().getItem(0);
            ItemStack right = e.getInventory().getItem(1);
            if (left.getType().equals(Material.BOOK)){
                if (left.getAmount() == 1 && !right.getEnchantments().isEmpty()){
                    ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
                    result.addUnsafeEnchantments(right.getEnchantments());
                    e.setResult(result);
                }
            }
        }
    }
}
