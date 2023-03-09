package com.tropaeo.roleplayplus.Objects;

import org.bukkit.entity.Player;

import java.util.UUID;

public class MarriageRequest {
    private final String id;
    private Player suitorPlayer = null;
    private Player targetPlayer = null;

    public MarriageRequest(Player suitorPlayer, Player targetPlayer) {
        this.suitorPlayer = suitorPlayer;
        this.targetPlayer = targetPlayer;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Player getSuitorPlayer() {
        return suitorPlayer;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }
}
