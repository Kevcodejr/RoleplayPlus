package com.tropaeo.roleplayplus.Objects;

import org.bukkit.Location;

public class User {
    // Basic Info
    private String username;
    private String uuid;
    private String marriedUuid;
    private boolean modified = false;
    private boolean online = true;

    // Other
    private String prefix = "&c<3&b<3&d<3";
    private Location marriageHome;

    public User() {
    }

    public User(String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public String getMarriedUuid() {
        return marriedUuid;
    }

    public void setMarriedUuid(String marriedUuid) {
        this.marriedUuid = marriedUuid;
    }

    public Location getMarriageHome() {
        return marriageHome;
    }

    public void setMarriageHome(Location marriageHome) {
        this.marriageHome = marriageHome;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void saveChanges() {
        this.modified = true;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
