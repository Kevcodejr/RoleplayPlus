package com.tropaeo.roleplayplus.DataStructure;

import com.tropaeo.roleplayplus.Objects.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private final List<User> userList = new ArrayList<>();
    private final UserDataHandler userDataHandler;

    public UserList(UserDataHandler userDataHandler) {
        this.userDataHandler = userDataHandler;
    }

    public void add(String uuid, String username) {
        setOnline(uuid, true);
        for (User user : userList) {
            if (user.getUuid().equals(uuid)) {
                return;
            }
        }
        FileConfiguration config = userDataHandler.load(uuid);
        // Run this if failed to load from file
        if (config == null) {
            User user = new User(username, uuid);
            userList.add(user);
            return;
        }
        User user = getConfigUser(config, username, uuid);
        userList.add(user);
    }

    public void runSaveScheduler() {
        for(int i = 0; i < userList.size(); i++){
            User user = userList.get(i);
            if (user.isModified()) {
                FileConfiguration config = setConfigUser(user);
                userDataHandler.save(user.getUuid(), config);
                user.setModified(false);
            }
            if (!user.isOnline()) {
                userList.remove(user);
            }
        }
    }

    private FileConfiguration setConfigUser(User user){
        FileConfiguration config = new YamlConfiguration();
        /////////////////////////////
        config.set("username", user.getUsername());
        config.set("uuid", user.getUuid());
        config.set("marriedUuid", user.getMarriedUuid());
        config.set("prefix", user.getPrefix());
        config.set("marriageHome", user.getMarriageHome());
        /////////////////////////////
        return config;
    }

    private User getConfigUser(FileConfiguration config, String username, String uuid){
        User user = new User();
        // Set to modified if username has changed
        if(!username.equalsIgnoreCase(config.getString("username"))){
            user.setModified(true);
        }
        /////////////////////////////
        user.setUsername(username);
        user.setUuid(uuid);
        user.setMarriedUuid(config.getString("marriedUuid"));
        if(config.getString("prefix") != null){
            user.setPrefix(config.getString("prefix"));
        }
        user.setMarriageHome(config.getLocation("marriageHome"));
        /////////////////////////////
        return user;
    }

    public void setOnline(String uuid, boolean mode) {
        for (User user : userList) {
            if(user.getUuid().equals(uuid)){
                user.setOnline(mode);
                return;
            }
        }
    }

    public User getUser(String uuid){
        for(User user : userList){
            if(user.getUuid().equals(uuid)){
                return user;
            }
        }
        return null;
    }

    public List<User> getUserList(){
        return userList;
    }


}
