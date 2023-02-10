package me.pao.smp.Functions;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LightLevelVisualizer implements CommandExecutor {

    private final Main instance;
    private static ArrayList<Player> lighting = new ArrayList<>();

    public LightLevelVisualizer(){
        instance = Main.getInstance();

        establishRepeatingLightCheck();
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a){
        if (s instanceof Player) {
            Player p = (Player)s;
            if (l.equalsIgnoreCase("light")) {
                if (lighting.contains(p)) {
                    lighting.remove(p);
                    p.sendMessage(C.lightDisable);
                } else {
                    lighting.add(p);
                    p.sendMessage(C.lightEnable);
                }
            }
        }
        return true;
    }

    private void establishRepeatingLightCheck(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!lighting.isEmpty()){
                ArrayList<Block> blockMap = new ArrayList<>();
                for (Player p : lighting){
                    blockMap.clear();
                    Location l = p.getLocation();
                    int dist = C.lightDistance;
                    for (int blockx = l.getBlockX() - dist; blockx < l.getBlockX() + dist; blockx++){
                        for (int blocky = l.getBlockY() - (dist / 2); blocky < l.getBlockY() + (dist / 2); blocky++){
                            for (int blockz = l.getBlockZ() - dist; blockz < l.getBlockZ() + dist; blockz++){
                                Block question = l.getWorld().getBlockAt(blockx,blocky,blockz);
                                if (question.getType().isSolid()) blockMap.add(question);
                            }
                        }
                    }
                    for (Block b : blockMap){
                        Block above = b.getWorld().getBlockAt(b.getLocation().add(0,1,0));
                        if (!above.getType().isSolid() && above.getLightFromBlocks() <= 7){
                            p.spawnParticle(Particle.VILLAGER_ANGRY,above.getX()+.5,above.getY(),above.getZ()+.5,1,0,0,0);
                        }
                    }
                }
            }
        }, C.lightCheckStartingDelay, C.lightCheckRepeatingDelay);
    }

    public static void clearLists(){
        lighting.clear();
    }
}
