package com.tropaeo.roleplayplus.Commands.MarryCommands;

import com.tropaeo.roleplayplus.Commands.CommandUtil;
import com.tropaeo.roleplayplus.Commands.RoleplayCommandUtil;
import com.tropaeo.roleplayplus.DataStructure.UserList;
import com.tropaeo.roleplayplus.Objects.User;
import com.tropaeo.roleplayplus.Objects.UserHandler;
import com.tropaeo.roleplayplus.RoleplayPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MarryMain implements CommandExecutor {
    private final MarryCooldowns marryCooldowns = new MarryCooldowns();
    private final CommandUtil commandUtil = new CommandUtil();
    private final RoleplayCommandUtil roleplayCommandUtil = new RoleplayCommandUtil();
    private final UserList userDataList = RoleplayPlus.getUserDataList();
    private final List<String> slapList = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            runPlayerCommand((Player) sender, command, label, args);
            return true;
        }


        return true;
    }

    public void runPlayerCommand(Player player, Command command, String label, String[] args) {
        MarryMessages originalMessenger = new MarryMessages(player);
        if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
            originalMessenger.helpMenu();
            return;
        }

        if(args[0].equalsIgnoreCase("slap")){
            for(String str : slapList){
                if(str.equals(player.getUniqueId().toString())){
                    originalMessenger.successMessage("Please wait a bit before you slap your partner again, you don't want to get divorced yet do you??");
                    return;
                }
            }

            User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
            if (notMarried(originalMessenger, user)) {
                return;
            }
            Player partner = commandUtil.getOnlinePlayerByUuid(user.getMarriedUuid());
            if(partner == null){
                return;
            }
            partner.setVelocity(partner.getLocation().getDirection().setY(1));
            partner.setVelocity(partner.getVelocity().setX(0));
            partner.setVelocity(partner.getVelocity().setZ(0));
            partner.playSound(partner.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1f, 1f);
            originalMessenger.successMessage("You just &eb*tch&b slapped your partner! :o");
            MarryMessages partnerMessenger = new MarryMessages(partner);
            partnerMessenger.successMessage("You just got &eb*tch&b slapped by your partner! :o");
            slapList.add(player.getUniqueId().toString());
            new BukkitRunnable() {
                public void run() {
                    slapList.remove(player.getUniqueId().toString());
                }
            }.runTaskLaterAsynchronously(RoleplayPlus.instance, 20 * 30);
            return;
        }


        if(args[0].equalsIgnoreCase("sethome")){
            User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
            if (notMarried(originalMessenger, user)) {
                return;
            }
            User user2 = roleplayCommandUtil.getOfflineUser(user.getMarriedUuid(), userDataList, commandUtil);
            if (user2 == null) {
                originalMessenger.errorMessage("Unable to load your partner profile, please try again later.");
                return;
            }
            UserHandler main = new UserHandler(user);
            UserHandler partner = new UserHandler(user2);
            Location homeLocation = player.getLocation();

            main.setHome(homeLocation);
            partner.setHome(homeLocation);
            originalMessenger.successMessage("Successfully set your marriage home to your position.");
            return;
        }

        if(args[0].equalsIgnoreCase("home")){
            User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
            if (notMarried(originalMessenger, user)) {
                return;
            }
            Location location = user.getMarriageHome();
            if(location == null){
                originalMessenger.errorMessage("No home set, set a new home with &7/marry sethome");
                return;
            }
            player.teleport(location);
            originalMessenger.successMessage("Successfully teleported to your marriage home.");
            return;
        }


        if (args[0].equalsIgnoreCase("list")) {
            List<User> userList = userDataList.getUserList();
            List<String> marriedPlayers = new ArrayList<>();

            for (User user : userList) {
                if (user.getMarriedUuid() != null) {
                    int i = 0;
                    for (String p : marriedPlayers) {
                        if (p.equals(user.getUuid()) || p.equals(user.getMarriedUuid())) {
                            i = 1;
                            break;
                        }
                    }
                    if (i == 0) {
                        marriedPlayers.add(user.getUuid());
                        marriedPlayers.add(user.getMarriedUuid());
                    }
                }
            }

            int pageStart = 0;
            int pageEnd;

            if (args.length != 1 && commandUtil.isNumber(args[1])) {
                int page = commandUtil.getIntegerFromString(args[1]);
                pageStart = (page - 1) * 16;
            }
            if (pageStart < 0) {
                pageStart = 0;
            }
            pageEnd = pageStart + 16;
            if (pageEnd > marriedPlayers.size()) {
                pageEnd = marriedPlayers.size();
            }

            StringBuilder marriageList = new StringBuilder("--- Married &b&lONLINE &bCouples (" + ((pageStart / 16) + 1) + ") ---\n");
            for (int i = pageStart; i < pageEnd - 1; i++) {
                marriageList.append("&b» ");
                String username = commandUtil.getOfflinePlayerNameByUuid(marriedPlayers.get(i));
                if (commandUtil.isOnlineByUuid(marriedPlayers.get(i))) {
                    marriageList.append("&a").append(username);
                } else {
                    marriageList.append("&7").append(username);
                }
                marriageList.append(" &b&l&mv&b ");

                if (marriedPlayers.get(i + 1) == null) {
                    break;
                }
                username = commandUtil.getOfflinePlayerNameByUuid(marriedPlayers.get(i + 1));
                if (commandUtil.isOnlineByUuid(marriedPlayers.get(i + 1))) {
                    marriageList.append("&a").append(username);
                } else {
                    marriageList.append("&7").append(username);
                }
                marriageList.append("&b\n");
                i++;
            }
            marriageList.append("To go to another page do /marry list <number>");
            originalMessenger.successMessage(marriageList.toString());
            return;
        }

        if (args.length != 1 && args[0].equalsIgnoreCase("prefix")) {
            User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
            if (notMarried(originalMessenger, user)) {
                return;
            }
            User user2 = roleplayCommandUtil.getOfflineUser(user.getMarriedUuid(), userDataList, commandUtil);
            if (user2 == null) {
                originalMessenger.errorMessage("Unable to do this :(");
                return;
            }

            UserHandler userHandler1 = new UserHandler(user);
            UserHandler userHandler2 = new UserHandler(user2);
            if (args[1].equalsIgnoreCase("default")) {
                String prefix = "&c<3&b<3&d<3";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
                return;
            }


            if (args[1].equalsIgnoreCase("green")) {
                if (!player.hasPermission("roleplayplus.prefix.green")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&2<3&a<3&2<3";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("blue")) {
                if (!player.hasPermission("roleplayplus.prefix.blue")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&9<3&b<3&9<3";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("red")) {
                if (!player.hasPermission("roleplayplus.prefix.red")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&4<3&c<3&4<3";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("rainbow")) {
                if (!player.hasPermission("roleplayplus.prefix.rainbow")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&4<&c3&e<&a3&9<&53";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("darkredheart")) {
                if (!player.hasPermission("roleplayplus.prefix.darkredheart")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&4&l&mv";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("redheart")) {
                if (!player.hasPermission("roleplayplus.prefix.redheart")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&c&l&mv";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("pinkheart")) {
                if (!player.hasPermission("roleplayplus.prefix.pinkheart")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&d&l&mv";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else if (args[1].equalsIgnoreCase("crown")) {
                if (!player.hasPermission("roleplayplus.prefix.crown")) {
                    originalMessenger.errorMessage("No permission.");
                    return;
                }
                String prefix = "&e&l&mw";
                userHandler1.setPrefix(prefix, originalMessenger);
                userHandler2.setPrefix(prefix);
            } else {
                originalMessenger.errorMessage("Not a valid prefix selected.");
            }
            return;
        }


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("prefix")) {
                User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
                if (notMarried(originalMessenger, user)) {
                    return;
                }
                originalMessenger.successMessage("You currently have this prefix: " + user.getPrefix());
                return;
            }


            if (args[0].equalsIgnoreCase("seen")) {
                User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
                if (notMarried(originalMessenger, user)) {
                    return;
                }
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(user.getMarriedUuid()));
                Player partnerPlayer = commandUtil.getOnlinePlayerByUuid(user.getMarriedUuid());
                if (partnerPlayer != null) {
                    originalMessenger.successMessage("Your partner is currently online.");
                    return;
                }

                StringBuilder timeOutput = new StringBuilder("");
                int seconds = commandUtil.getTimeDifferenceInSeconds(offlinePlayer.getLastPlayed(), Calendar.getInstance().getTimeInMillis());
                timeOutput.append(commandUtil.getTimeDaysRoundedDown(seconds, " days "));
                timeOutput.append(commandUtil.getTimeHoursRoundedDown(seconds, " hours "));
                timeOutput.append(commandUtil.getTimeMinutesRoundedDown(seconds, " minutes "));
                timeOutput.append(commandUtil.getTimeSecondsRoundedDown(seconds, " seconds "));
                originalMessenger.successMessage("Last seen: " + timeOutput + "ago.");
                return;
            }


            if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("teleport")) {
                User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
                if (notMarried(originalMessenger, user)) {
                    return;
                }
                User partner = roleplayCommandUtil.getOfflineUser(user.getMarriedUuid(), userDataList, commandUtil);
                Player partnerPlayer = Bukkit.getPlayer(UUID.fromString(user.getMarriedUuid()));
                if (partner == null || !commandUtil.isOnlineByUuid(user.getMarriedUuid()) || partnerPlayer == null) {
                    originalMessenger.errorMessage("Your partner is not online.");
                    return;
                }
                player.teleport(partnerPlayer);
                MarryMessages partnerMessenger = new MarryMessages(partnerPlayer);
                originalMessenger.successMessage("You successfully teleported to your partner.");
                partnerMessenger.successMessage("Your partner teleported to you.");
                return;
            }


            if (args[0].equalsIgnoreCase("divorce")) {
                User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
                if (notMarried(originalMessenger, user)) {
                    return;
                }

                User user2 = roleplayCommandUtil.getOfflineUser(user.getMarriedUuid(), userDataList, commandUtil);
                if (user2 == null) {
                    originalMessenger.errorMessage("Could not load other user. Please contact the administrator.");
                    UserHandler userHandler = new UserHandler(user);
                    userHandler.resetMarriage();
                    return;
                }

                UserHandler userHandler = new UserHandler(user);
                UserHandler userHandler2 = new UserHandler(user2);
                userHandler.resetMarriage();
                userHandler2.resetMarriage();
                originalMessenger.broadcastDivorce(user.getUsername(), user2.getUsername());
                return;
            }


            if (args[0].equalsIgnoreCase("accept")) {
                Player suitorPlayer = marryCooldowns.getSuitor(player);
                if (!marryCooldowns.hasPendingRequest(player.getPlayerListName()) || suitorPlayer == null) {
                    originalMessenger.noPendingRequests();
                    return;
                }
                User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
                User suitorUser = roleplayCommandUtil.getUser(suitorPlayer.getUniqueId().toString());
                if (user == null || suitorUser == null) {
                    originalMessenger.notLoaded();
                    return;
                }
                if (roleplayCommandUtil.isMarried(user)) {
                    originalMessenger.alreadyIsMarried();
                    return;
                }
                user.setMarriedUuid(suitorPlayer.getUniqueId().toString());
                suitorUser.setMarriedUuid(player.getUniqueId().toString());
                user.setPrefix("&c<3&b<3&d<3");
                suitorUser.setPrefix("&c<3&b<3&d<3");
                user.saveChanges();
                suitorUser.saveChanges();
                marryCooldowns.removeAllPendingRequests(user.getUuid());
                marryCooldowns.removeAllPendingRequests(suitorUser.getUuid());
                originalMessenger.broadcastMarry(user.getUsername(), suitorUser.getUsername());
                return;
            }
            if (args[0].equalsIgnoreCase("deny")) {
                Player suitorPlayer = marryCooldowns.getSuitor(player);
                if (!marryCooldowns.hasPendingRequest(player.getPlayerListName()) || suitorPlayer == null) {
                    originalMessenger.noPendingRequests();
                    return;
                }
                marryCooldowns.removeAllPendingRequests(player.getUniqueId().toString());
                marryCooldowns.removeAllPendingRequests(suitorPlayer.getUniqueId().toString());
                originalMessenger.marryRequestDenied();
                return;
            }


            // Marriage request
            marriageRequest(player, originalMessenger, args);
        }
    }

    private boolean notMarried(MarryMessages marryMessages, User user) {
        if (user == null) {
            marryMessages.notLoaded();
            return true;
        }
        if (!roleplayCommandUtil.isMarried(user)) {
            marryMessages.notMarried();
            return true;
        }
        return false;
    }


    public void marriageRequest(Player player, MarryMessages originalMessenger, String[] args) {
        // Marriage request
        String targetName = args[0];
        Player targetPlayer = commandUtil.getOnlinePlayerByName(targetName);
        User user = roleplayCommandUtil.getUser(player.getUniqueId().toString());
        if (player.getPlayerListName().equalsIgnoreCase(targetName)) {
            originalMessenger.cantYourself();
            return;
        }
        if (marryCooldowns.hasPendingRequest(targetName)) {
            originalMessenger.alreadyHavePendingRequest();
            return;
        }
        if (user == null) {
            originalMessenger.notLoaded();
            return;
        }
        if (roleplayCommandUtil.isMarried(user)) {
            originalMessenger.alreadyIsMarried();
            return;
        }
        if (targetPlayer != null) {
            MarryMessages targetMessenger = new MarryMessages(targetPlayer);
            marryCooldowns.newPendingRequest(player, targetPlayer);
            targetMessenger.marryRequest(false, player.getPlayerListName());
            originalMessenger.marryRequest(true, targetPlayer.getPlayerListName());
        } else {
            originalMessenger.notOnlineError();
        }
    }


}
