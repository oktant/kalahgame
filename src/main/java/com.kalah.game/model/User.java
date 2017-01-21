package com.kalah.game.model;

/**
 * User class for user authentification and user
 * @author oalizada
 */
public class User {
    private String username;
    private boolean userStatus;
    private String type="a";

    /**
     * User constructor for creating the user
     */
    public User(String username, boolean userStatus, String type){
        this.username=username;
        this.userStatus=userStatus;
        this.type=type;
    }


    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}
