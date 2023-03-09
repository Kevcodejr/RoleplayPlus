package com.tropaeo.roleplayplus.Commands.MarryCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MarryMessages {
    private final Player player;

    public MarryMessages(Player player) {
        this.player = player;
    }

    private void sendMessage(String message) {
        String prefix = "";
        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
    }

    public void notOnlineError() {
        sendMessage("&cThat player isn't online!");
    }

    public void marryRequest(boolean isSender, String username) {
        if (isSender) {
            sendMessage("&bMarriage request sent to &f" + username + "&b, will expire in 60 seconds.");
        } else {
            sendMessage("&bYou've recieved a marriage request from &f" + username + "&b, will expire in 60 seconds. Do &f/marry &4deny &bor &f/marry &2accept " +
                    "&bto deny or accept the marriage request!");
        }
    }

    private final String helpMenuString = ChatColor.AQUA + "- - - - - - - - - " + ChatColor.LIGHT_PURPLE + "Marriage Help" + ChatColor.AQUA + " - - - - - - - - -\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "<player> " + ChatColor.AQUA + "- send a marriage request to another player.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "divorce " + ChatColor.AQUA + "- divorce your partner.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "seen " + ChatColor.AQUA + "- check when your partner was last online.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "slap " + ChatColor.AQUA + "- quickest way to a divorce.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "list " + ChatColor.AQUA + "- list all online couples.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "tp|teleport " + ChatColor.AQUA + "- teleport to your partner.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "prefix " + ChatColor.AQUA + "- change your marriage prefix.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "prefixes " + ChatColor.AQUA + "- show a prefix gui to select prefixes from.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "sethome " + ChatColor.AQUA + "- set your shared marriage home.\n" +
            ChatColor.AQUA + "/marry " + ChatColor.WHITE + "home " + ChatColor.AQUA + "- teleport to your shared marriage home.\n";

    public void helpMenu(){
        sendMessage(helpMenuString);
    }

    public void successMessage(String str){
        sendMessage("&b" + str);
    }

    public void errorMessage(String str){
        sendMessage("&c" + str);
    }

    public void message(String str){
        sendMessage(str);
    }

    public void marryRequestDenied(){
        sendMessage("&bRequest denied.");
    }

    public void alreadyHavePendingRequest() {
        sendMessage("&cThis player already has a pending request, please wait for it to be denied, accepted or expired.");
    }

    public void noPendingRequests(){
        sendMessage("&cNo pending requests.");
    }

    public void alreadyIsMarried() {
        sendMessage("&cYou're already married! To unmarry do &7/marry divorce");
    }

    public void notMarried() {
        sendMessage("&cYou're not married! :(");
    }

    public void notLoaded() {
        sendMessage("&cPlayer information was not loaded correctly, please relog. If this error persists please contact an administrator.");
    }

    public void cantYourself() {
        sendMessage("&cYou can't do that with yourself!");
    }

    public void broadcastMarry(String user1, String user2) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a" + user1 + " and " + user2 + " just married" +
                " each other!"));
    }

    public void broadcastDivorce(String user1, String user2) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a" + user1 + " and " + user2 + " just divorce" +
                " each other!"));
    }


}
