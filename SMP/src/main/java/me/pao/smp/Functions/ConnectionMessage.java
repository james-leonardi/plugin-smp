package me.pao.smp.Functions;

import me.pao.smp.C;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionMessage implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e){}
    @EventHandler
    public void leave(PlayerQuitEvent e){
        e.setQuitMessage(C.quitMessage.replace("@PLAYER@",e.getPlayer().getName()));
    }
}