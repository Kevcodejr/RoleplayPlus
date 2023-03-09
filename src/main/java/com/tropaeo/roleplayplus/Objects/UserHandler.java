package com.tropaeo.roleplayplus.Objects;

import com.tropaeo.roleplayplus.Commands.MarryCommands.MarryMessages;
import org.bukkit.Location;

public class UserHandler {
    private final User user;

    public UserHandler(User user) {
        this.user = user;
    }

    public void setPrefix(String prefix, MarryMessages marryMessages){
        user.setPrefix(prefix);
        user.saveChanges();
        marryMessages.successMessage("Successfully set your marriage prefix to: " + prefix);
    }
    public void setPrefix(String prefix){
        user.setPrefix(prefix);
        user.saveChanges();
    }

    public void setHome(Location location){
        user.setMarriageHome(location);
        user.saveChanges();
    }


    public void resetMarriage(){
        user.setPrefix(null);
        user.setMarriageHome(null);
        user.setMarriedUuid(null);
        user.saveChanges();
    }


}
