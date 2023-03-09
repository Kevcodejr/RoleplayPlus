package com.tropaeo.roleplayplus.Listeners;

import com.tropaeo.roleplayplus.DataStructure.UserList;
import com.tropaeo.roleplayplus.RoleplayPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SessionListener implements Listener {
    private final UserList userDataList = RoleplayPlus.getUserDataList();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        userDataList.add(event.getPlayer().getUniqueId().toString(), event.getPlayer().getPlayerListName());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        userDataList.setOnline(event.getPlayer().getUniqueId().toString(), false);
    }

}
