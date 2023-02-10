package me.pao.smp.Functions;

import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Timber implements Listener {

    private final Main instance;

    public Timber(){
        instance = Main.getInstance();
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        if (e.getPlayer().isSneaking()){
            List<Material> axe = Arrays.asList(Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,Material.GOLDEN_AXE,Material.DIAMOND_AXE);
            if (axe.contains(e.getPlayer().getInventory().getItemInMainHand().getType())){
                List<Material> wood = Arrays.asList(Material.ACACIA_LOG,Material.BIRCH_LOG,Material.DARK_OAK_LOG,Material.JUNGLE_LOG,Material.OAK_LOG,Material.SPRUCE_LOG);
                if (wood.contains(e.getBlock().getType())){
                    breakTree(e.getPlayer().getInventory().getItemInMainHand(),e.getBlock());
                }
            }
        }
    }

    private void breakTree(ItemStack axe, Block tree){
        List<Material> wood = Arrays.asList(Material.ACACIA_LOG,Material.BIRCH_LOG,Material.DARK_OAK_LOG,Material.JUNGLE_LOG,Material.OAK_LOG,Material.SPRUCE_LOG);
        if(!wood.contains(tree.getType())) return;
        tree.breakNaturally();
        Random r = new Random();
        tree.getWorld().playSound(tree.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.2F, r.nextInt(2));
        ((ExperienceOrb)tree.getWorld().spawnEntity(tree.getLocation(), EntityType.EXPERIENCE_ORB)).setExperience(1);
        try {
            if (axe.getDurability() == axe.getType().getMaxDurability()){
                tree.getWorld().playSound(tree.getLocation(),Sound.ENTITY_ITEM_BREAK,1,1);
                axe.setAmount(0);
            } else {
                axe.setDurability((short)(axe.getDurability()+1));
            }
        } catch (NullPointerException ignored){}
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            for(BlockFace face : BlockFace.values())
                breakTree(axe,tree.getRelative(face));
        },5L);
    }
}
