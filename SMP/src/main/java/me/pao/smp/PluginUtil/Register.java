package me.pao.smp.PluginUtil;

import me.pao.smp.Functions.*;
import me.pao.smp.Main;
import me.pao.smp.ServerUtil.CreatureDeclutter;
import me.pao.smp.UsableItems.ProjectileShield;
import me.pao.smp.UsableItems.SmokeBomb;
import org.bukkit.Bukkit;

public class Register {
    public static void registerListeners(Main instance){
        Bukkit.getPluginManager().registerEvents(new ProjectileShield(),instance);
        Bukkit.getPluginManager().registerEvents(new TogglePvP(),instance);
        Bukkit.getPluginManager().registerEvents(new OffhandTorch(),instance);
        Bukkit.getPluginManager().registerEvents(new SmokeBomb(),instance);
        Bukkit.getPluginManager().registerEvents(new ItemInitializer(),instance);
        Bukkit.getPluginManager().registerEvents(new ChainHelmetSwapper(),instance);
        Bukkit.getPluginManager().registerEvents(new ConnectionMessage(),instance);
        Bukkit.getPluginManager().registerEvents(new Timber(),instance);
        Bukkit.getPluginManager().registerEvents(new CreatureDeclutter(),instance);
        Bukkit.getPluginManager().registerEvents(new Disenchant(),instance);
        Bukkit.getPluginManager().registerEvents(new ImprovedEnchantments(),instance);
        Bukkit.getPluginManager().registerEvents(new HorseStats(),instance);
        Bukkit.getPluginManager().registerEvents(new DeathHandler(),instance);
        Bukkit.getPluginManager().registerEvents(new NightSkip(),instance);
        Bukkit.getPluginManager().registerEvents(new HomingBow(),instance);
        Bukkit.getPluginManager().registerEvents(new ExplosionRollback(),instance);
    }

    public static void registerCommands(Main instance){
        instance.getCommand("light").setExecutor(new LightLevelVisualizer());
        instance.getCommand("pvp").setExecutor(new TogglePvP());
        instance.getCommand("smprl").setExecutor(new MiscCommands());
        instance.getCommand("ib").setExecutor(new MiscCommands());
        instance.getCommand("brew").setExecutor(new MiscCommands());
        instance.getCommand("torch").setExecutor(new OffhandTorch());
    }

    public static void clearAllLists(){
        LightLevelVisualizer.clearLists();
        OffhandTorch.clearLists();
        TogglePvP.clearLists();
        //AntiFishFarming.clearLists();
        ProjectileShield.clearLists();
    }
}
