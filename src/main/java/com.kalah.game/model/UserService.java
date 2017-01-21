package com.kalah.game.model;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for users;
 * @author oalizada
 *
 */
@Repository
public interface UserService  {
    public String addUser(User username);
    public void removeUser(String username);
    public List<User> getUsers();
}
