package me.pao.smp.PluginUtil;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;

public class StartupUtility {

    //Pass with true if finished reloading, false otherwise.
    public static void displayReloadMessage(boolean b){
        if (b){
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Bukkit.broadcastMessage(C.reloadMessageEnd),1L);
        }
        else Bukkit.broadcastMessage(C.reloadMessageStart);
    }

    public static void establishScoreboardHP(boolean enabled, long delay){
        if (enabled){
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), C.scoreboardRegisterHP);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), C.scoreboardHPBelowName);
            },delay);
        }
    }

    public static void initializeCustomRecipes(){
        ItemInitializer items = new ItemInitializer();
        items.initializeCustomRecipes();
    }
}