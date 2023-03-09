package com.tropaeo.roleplayplus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommandUtil {
    public CommandUtil() {
    }

    public boolean isOnlineByUuid(String uuid){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getUniqueId().toString().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public boolean isOnlineByName(String username){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getPlayerListName().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }

    public boolean isNumber(String number){
        try {
            Double.parseDouble(number);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public int getIntegerFromString(String number){
        double convertedNumber = 0;
        if(isNumber(number)){
            try {
                convertedNumber = Double.parseDouble(number);
                return (int) convertedNumber;
            } catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
        return -1;
    }

    public Player getOnlinePlayerByName(String username){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getPlayerListName().equalsIgnoreCase(username)){
                return player;
            }
        }
        return null;
    }

    public Player getOnlinePlayerByUuid(String uuid){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getUniqueId().toString().equals(uuid)){
                return player;
            }
        }
        return null;
    }

    public String getOfflinePlayerUuidByName(String username){
        return Bukkit.getOfflinePlayer(username).getUniqueId().toString();
    }

    public String getOfflinePlayerNameByUuid(String uuid){
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
    }


    private void placeholder(){

    }

    public int getTimeDifferenceInSeconds(long firstTime, long currentTime){
        return (int) ((currentTime - firstTime) / 1000);
    }

    public String getTimeDaysRoundedDown(int seconds, String formattedUnit){
        int days = (seconds / 86400);
        if (days != 0) {
            return days + formattedUnit;
        }
        return "";
    }

    public String getTimeHoursRoundedDown(int seconds, String formattedUnit){
        int hours = (seconds / 3600) % 24;
        if (hours != 0) {
            return hours + formattedUnit;
        }
        return "";
    }

    public String getTimeMinutesRoundedDown(int seconds, String formattedUnit){
        int minutes = (seconds / 60) % 60;
        if (minutes != 0) {
            return minutes + formattedUnit;
        }
        return "";
    }

    public String getTimeSecondsRoundedDown(int seconds, String formattedUnit){
        int thisSeconds = seconds % 60;
        if (thisSeconds != 0) {
            return thisSeconds + formattedUnit;
        }
        return "";
    }







}
