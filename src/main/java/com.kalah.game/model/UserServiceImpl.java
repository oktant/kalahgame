package com.kalah.game.model;



import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation Of UserService class. Is the model where active users are stored
 * @author oalizada
 *
 */
@Repository
public class UserServiceImpl implements UserService {
    List<User> users = new ArrayList<User>();

    /**
     * Method addUser for adding the new user
     * @param User username;
     *  @return String status of addition
     */
    @Override
    public String addUser(User username) {
        if (!users.contains(username) && users.size() < 2) {
            users.add(username);
            return username.getUsername();

        }


        else if (users.contains(username)) {


            return "user_Already_Exists";

        } else
            return "tableIsFull";

    }
    /**
     *
     *  Removing the user when disconnected or game is over;
     *  @param String username
     *
     */
    @Override
    public void removeUser(String username) {
       

        for (int i = 0; i < users.size(); i++) {
          
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
            }


        }


    }
    /**
     * Method returns the list of current online users
     *@return all users as a list
     */
    @Override
    public List<User> getUsers() {
        return users;
    }
}