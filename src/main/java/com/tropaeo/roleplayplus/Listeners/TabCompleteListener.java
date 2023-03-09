package com.tropaeo.roleplayplus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event){
        String message = event.getBuffer();

        String marryCmd = "/marry ";
        if(message.length() >= marryCmd.length() && message.substring(0, marryCmd.length()).equalsIgnoreCase(marryCmd)){
            String[] strings = {"list", "divorce", "tp", "teleport", "seen", "accept", "deny", "sethome", "home", "prefix", "prefixes", "slap"};
            for(String str : strings){
                subCommandComplete(marryCmd, str, event);
            }
        }

        String marryPrefixCmd = "/marry prefix ";
        if(message.length() >= marryPrefixCmd.length() && message.substring(0, marryPrefixCmd.length()).equalsIgnoreCase(marryPrefixCmd)){
            String[] strings = {"blue", "green", "red", "rainbow", "default", "redheart", "darkredheart", "pinkheart", "crown"};
            List<String> newList = new ArrayList<>();
            event.setCompletions(newList);
            for(String str : strings){
                subCommandComplete(marryPrefixCmd, str, event);
            }
        }
    }

    private void subCommandComplete(String command, String autoComplete, TabCompleteEvent event){
        String userString = event.getBuffer();
        String longMessage = command + autoComplete;
        if(userString.length() <= longMessage.length() && userString.equalsIgnoreCase(longMessage.substring(0, userString.length()))){
            List<String> tempList = event.getCompletions();
            tempList.add(autoComplete);
            event.setCompletions(tempList);
        }
    }

}
