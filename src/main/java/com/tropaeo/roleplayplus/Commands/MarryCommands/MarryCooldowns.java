package com.tropaeo.roleplayplus.Commands.MarryCommands;


import com.tropaeo.roleplayplus.Objects.DoubleString;
import com.tropaeo.roleplayplus.Objects.MarriageRequest;
import com.tropaeo.roleplayplus.RoleplayPlus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MarryCooldowns {
    private final List<MarriageRequest> requests = new ArrayList<>();

    private final List<Player> reqSuitors = new ArrayList<>();
    private final List<Player> reqTargets = new ArrayList<>();

    public MarryCooldowns() {
    }

    public void newPendingRequest(Player suitor, Player target) {
        MarriageRequest marriageRequest = new MarriageRequest(suitor, target);
        requests.add(marriageRequest);

        new BukkitRunnable() {
            public void run() {
                requests.removeIf(request -> request.getId().equals(marriageRequest.getId()));
            }
        }.runTaskLaterAsynchronously(RoleplayPlus.instance, 20 * 60);
    }

    public boolean hasPendingRequest(String username){
        for(MarriageRequest marriageRequest : requests){
            if(marriageRequest.getTargetPlayer().getPlayerListName().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public Player getSuitor(Player target){
        Player suitor = null;
        for(MarriageRequest marriageRequest : requests){
            if(marriageRequest.getTargetPlayer().getUniqueId().toString().equals(target.getUniqueId().toString())){
                suitor = marriageRequest.getSuitorPlayer();
                break;
            }
        }
        return suitor;
    }

    public void removeAllPendingRequests(String uuid){
        requests.removeIf(marriageRequest -> marriageRequest.getTargetPlayer().getUniqueId().toString().equals(uuid));
        requests.removeIf(marriageRequest -> marriageRequest.getSuitorPlayer().getUniqueId().toString().equals(uuid));
    }


}
