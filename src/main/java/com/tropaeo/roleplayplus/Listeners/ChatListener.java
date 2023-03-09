package com.tropaeo.roleplayplus.Listeners;

import com.tropaeo.roleplayplus.Commands.RoleplayCommandUtil;
import com.tropaeo.roleplayplus.Objects.User;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final RoleplayCommandUtil roleplayCommandUtil = new RoleplayCommandUtil();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event){
        User user = roleplayCommandUtil.getUser(event.getPlayer().getUniqueId().toString());
        if(roleplayCommandUtil.isMarried(user)){
            event.setFormat(ChatColor.translateAlternateColorCodes('&', user.getPrefix()) + ChatColor.RESET + " " + event.getFormat());
        }


    }

}
