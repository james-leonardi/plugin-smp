package me.pao.smp.Functions;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class ExplosionRollback implements Listener {

    private final Main instance;
    public ExplosionRollback(){
        instance = Main.getInstance();
    }
    @EventHandler
    public void explosion(EntityExplodeEvent e){
        if (e.getEntityType().equals(EntityType.PRIMED_TNT) || (C.rollbackMobExplosions && e.getEntity() instanceof Creeper)){
            Random r = new Random();
            int min = 300;
            int max = -1;
            for (Block b : e.blockList()){
                int height = (int)b.getLocation().getY();
                if (min > height) min = height;
                if (max < height) max = height;
            }
            //List<Material> containers = Arrays.asList(Material.CHEST,Material.TRAPPED_CHEST,Material.FURNACE,Material.HOPPER,Material.DROPPER,Material.DISPENSER,Material.BREWING_STAND,Material.SHULKER_BOX);
            for (Block b : e.blockList()){
                if (b.getType().equals(Material.TNT)) continue;
                final BlockState bs = b.getState();
                Location l = b.getLocation();
                int fromBottom = (int)l.getY() - min;
                int delay = 70 + (30*fromBottom+1) + r.nextInt(30);
                if (bs instanceof InventoryHolder) getServer().dispatchCommand(Bukkit.getConsoleSender(),"setblock " + (int)l.getX() + " " + (int)l.getY() + " " + (int)l.getZ() + " air");
                else b.setType(Material.AIR);
                getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> bs.update(true,true),delay);
            }
        }
    }
}