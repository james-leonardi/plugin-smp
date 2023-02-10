package me.pao.smp.ServerUtil;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureDeclutter implements Listener {

    private final Main instance;

    public CreatureDeclutter() {
        instance = Main.getInstance();

        for (LivingEntity e : Bukkit.getWorld("world").getLivingEntities()){
            timeCreature(e);
        }
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) timeCreature(e.getEntity());
    }

    private void timeCreature(LivingEntity e) {
        if (e.getLocation().getY() < 62 && e instanceof Monster && !(e instanceof Drowned) && e.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                if (e.getHealth() == e.getMaxHealth() && ((Monster) e).getTarget() == null) {
                    e.remove();
                }
            }, C.creatureDespawnTimer);
        }
    }
}
