package com.tropaeo.roleplayplus.Commands;

import com.tropaeo.roleplayplus.DataStructure.UserList;
import com.tropaeo.roleplayplus.Objects.User;
import com.tropaeo.roleplayplus.RoleplayPlus;

public class RoleplayCommandUtil {
    public RoleplayCommandUtil(){

    }

    public boolean isMarried(User user){
        return user.getMarriedUuid() != null;
    }

    public User getUser(String uuid){
        return RoleplayPlus.getUserDataList().getUser(uuid);
    }

    private void test(){

    }

    public User getOfflineUser(String uuid, UserList userList, CommandUtil commandUtil){
        User user = getUser(uuid);
        if(user == null){
            userList.add(uuid, commandUtil.getOfflinePlayerNameByUuid(uuid));
            userList.setOnline(uuid, false);
            user = getUser(uuid);
            return user;
        }
        return user;
    }



}
