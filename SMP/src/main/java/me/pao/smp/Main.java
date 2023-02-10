package me.pao.smp;

import me.pao.smp.PluginUtil.Register;
import me.pao.smp.PluginUtil.StartupUtility;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();

        Register.registerListeners(instance);
        Register.registerCommands(instance);

        StartupUtility.establishScoreboardHP(true, C.firstCommandDelay);
        StartupUtility.initializeCustomRecipes();
        StartupUtility.displayReloadMessage(true);
    }
    @Override
    public void onDisable(){
        StartupUtility.displayReloadMessage(false);
        Register.clearAllLists();
    }
}