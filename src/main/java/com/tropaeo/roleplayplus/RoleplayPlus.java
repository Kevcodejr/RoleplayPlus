package com.tropaeo.roleplayplus;

import com.tropaeo.roleplayplus.Commands.MarryCommands.MarryMain;
import com.tropaeo.roleplayplus.DataStructure.UserDataHandler;
import com.tropaeo.roleplayplus.DataStructure.UserList;
import com.tropaeo.roleplayplus.Listeners.ChatListener;
import com.tropaeo.roleplayplus.Listeners.SessionListener;
import com.tropaeo.roleplayplus.Listeners.TabCompleteListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class RoleplayPlus extends JavaPlugin {
    public static RoleplayPlus instance;
    private static final UserDataHandler userDataHandler = new UserDataHandler();
    private static final UserList userDataList = new UserList(userDataHandler);
    public static UserList getUserDataList(){
        return userDataList;
    }


    @Override
    public void onEnable() {
        instance = this;
        userDataHandler.initialize(this);
        //// Config ////
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //// Register Listeners ////
        getServer().getPluginManager().registerEvents(new SessionListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);

        //// Register Commands ////
        Objects.requireNonNull(getCommand("marry")).setExecutor(new MarryMain());


        new BukkitRunnable() {
            public void run() {
                System.out.println("Saving ... [RoleplayPlus]");
                userDataList.runSaveScheduler();
                System.out.println("Saved [RoleplayPlus]");
            }
        }.runTaskTimerAsynchronously(this, 20 * 60 * 5, 20 * 60 * 10);

        for(Player player : Bukkit.getOnlinePlayers()){
            userDataList.add(player.getPlayer().getUniqueId().toString(), player.getPlayer().getPlayerListName());
        }
    }

    @Override
    public void onDisable() {

        userDataList.runSaveScheduler();
    }
}
