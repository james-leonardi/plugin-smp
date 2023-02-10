package me.pao.smp.Functions;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class NightSkip implements Listener {

    private int sleeping;

    private final Main instance;
    public NightSkip(){
        instance = Main.getInstance();
        sleeping = 0;
    }

    @EventHandler
    public void sleep(PlayerBedEnterEvent e){
        if (C.nightskipEnabled){
            if (e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)){
                sleeping++;
                if (sleeping == 1) countdown();
            }
        } else {
            if (e.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK))
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + e.getPlayer().getName() + " is trying to sleep.");
        }
    }

    @EventHandler
    public void leaveSleep(PlayerBedLeaveEvent e){
        if (C.nightskipEnabled) sleeping--;
    }

    /**
     * I know this function has a small bug, and I could fix it, but I choose not to.
     * It's tradition at this point to make the countdown malfunction.
     */
    private void countdown() {
        for (int timeLeft = C.nightskipTimer; timeLeft >= 0; timeLeft--) {
            final int copy = timeLeft;
            instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, () -> {
                if (sleeping == 0) return;
                if (copy > 0){
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Skipping night in " + copy + " seconds.");
                }
                else {
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Night skipped.");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!p.isSleeping() || p.getSleepTicks() < 100) p.setSleepingIgnored(true);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> p.setSleepingIgnored(false),10L);
                    }
                }
            }, (10 - timeLeft) * 20);
        }
    }
}
