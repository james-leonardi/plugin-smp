package me.pao.smp;

import org.bukkit.ChatColor;

public final class C {
    public static final String reloadMessageStart = ChatColor.RED + "Reloading. Server will lag for a bit...";
    public static final String reloadMessageEnd = ChatColor.GREEN + "Reload complete.";
    public static final String scoreboardRegisterHP = "scoreboard objectives add hp health \"❤\"";
    public static final String scoreboardHPBelowName = "scoreboard objectives setdisplay belowName hp";
    public static final String fishfarmBroadcast = ChatColor.DARK_RED + "@PLAYER@ is a dirty AFK fisher."; // @PLAYER@ = playername
    public static final String fishfarmKickMessage = "AFK Fish Farming is disabled";
    public static final String lightDisable = ChatColor.GRAY + "You will no longer see dangerous light levels.";
    public static final String lightEnable = ChatColor.YELLOW + "You will now see dangerous light levels.";
    public static final String brewingGuideLink = "https://gamepedia.cursecdn.com/minecraft_gamepedia/7/7b/Minecraft_brewing_en.png?version=7451b1173274f7647253af50d1f1bb4d";
    public static final String quitMessage = ChatColor.YELLOW + "@PLAYER@ ragequit"; // @PLAYER@ = playername
    public static final String pvpAlwaysOn = ChatColor.RED + "PvP is always enabled.";
    public static final String pvpEnable = ChatColor.GREEN + "Enabled PvP";
    public static final String pvpDisable = ChatColor.RED + "Disabled PvP";
    public static final String torchDisable = ChatColor.RED + "Off-hand torch placement disabled.";
    public static final String torchEnable = ChatColor.GREEN + "Off-hand torch placement enabled.";

    public static final long firstCommandDelay = 30L;
    public static final long lightCheckStartingDelay = 5L;
    public static final long lightCheckRepeatingDelay = 18L;
    public static final long creatureDespawnTimer = 1200L;

    public static final int smokeBombDuration = 100;
    public static final int projectileShieldCD = 6;
    public static final int fishFarmDelay = 0;
    public static final int nightskipTimer = 10;
    public static int lightDistance = Main.getInstance().getConfig().getInt("lightDistance");

    public static double teleportCD = Main.getInstance().getConfig().getDouble("teleportCooldown");

    public static boolean pvpEnabled = Main.getInstance().getConfig().getBoolean("pvp");
    public static boolean nightskipEnabled = Main.getInstance().getConfig().getBoolean("nightSkipping");
    public static boolean customTP = Main.getInstance().getConfig().getBoolean("teleport");
    public static boolean rollbackMobExplosions = Main.getInstance().getConfig().getBoolean("mobExplosionRollback");

    private C(){
        throw new AssertionError();
    }
}
