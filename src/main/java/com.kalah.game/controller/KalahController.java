package com.kalah.game.controller;


import com.kalah.game.game.PlayerStep;
import com.kalah.game.game.GameEngine;
import com.kalah.game.model.User;
import com.kalah.game.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Controller
 * @author oalizada
 */
@Controller
public class KalahController {
    @Autowired
    UserService userService;

    @Autowired
    GameEngine gameEngine;

    private boolean isFirst = true;
    /*
        * returns main page for login
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }
    /**
       * Authenticate the user. If the user exists returns to /login
       * If the user is 3rd user returns /tableIsFull page
       * Otherwise lets user to enter to game
        *  @param String username
        *  @return String page
        */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String getMainPage(ModelMap model, @ModelAttribute("username") String username) {

        if (username != null && !username.isEmpty() && !username.equals("")) {
            String userAddition = "";
            if (userService.getUsers().size() == 0)
                userAddition = userService.addUser(new User(username, false, "a"));
            else {
                userAddition = userService.addUser(new User(username, false, "b"));
            }
            if (userAddition.equals("user_Already_Exists")) {
                model.addAttribute("error", "user already exist");
                return "login";

            }
            if (userAddition.equals("tableIsFull")) {
                model.addAttribute("username", username);
                return "tableIsFull";
            } else {

                model.addAttribute("username", username);
                return "index";
            }
        }
        model.addAttribute("error", "username is empty");
        return "login";
    }
    /**
     * Deletes the user if user presses Exit button or closes the browser. Returns the winning username.
     * @param username
     * @return String winner or gameOver
     */
    @MessageMapping("/deleteUser")
    @SendTo("/topic/deleteUser")
    public String deleteUser(String username) {

        userService.removeUser(username);
        gameEngine.setGameEngine();
        if (userService.getUsers().size() > 0) {


            return "user:" + username + " disconnected. Winner is " + userService.getUsers().get(0).getUsername();

        }

        return "game over";
    }
    /**
      * Takes users' movements and updates the board and returns updated board
     * @param PlayerStep message
     * @return Map<String, Integer> board
      */
    @MessageMapping("/updateBoard")
    @SendTo("/topic/game")
    public Map<String, Integer> play(PlayerStep message) {
        String playerType = "" + message.getStepId().charAt(0);

        if (userService.getUsers().size() == 2) {
            for (User u : userService.getUsers()) {
                if (u.getUsername().equals(message.getPlayer()) && u.isUserStatus() && u.getType().equals(playerType)) {
                    gameEngine.updateBoard(message.getStepId(), u);
                    if (gameEngine.returnWinner().equals("user1")) {
                        Map<String, Integer> temp = new HashMap<String, Integer>();
                        temp.put(userService.getUsers().get(0).getUsername(), -1);
                        gameEngine.setGameEngine();
                        isFirst=true;
                        return temp;
                    } else if (gameEngine.returnWinner().equals("user2")) {
                        Map<String, Integer> temp = new HashMap<String, Integer>();
                        temp.put(userService.getUsers().get(1).getUsername(), -1);
                        gameEngine.setGameEngine();
                        isFirst=true;
                        return temp;
                    }
                }
            }


        }

        return gameEngine.getMapGameBoard();
    }


    /**
      *Provides with notification for users who should play and starts the game
     * @param String usernameJoined
     *@return String status notifications
     */
    @MessageMapping("/joinGame")
    @SendTo("/topic/startGame")
    public String startGame(String usernameJoined) {

        if (userService.getUsers().size() == 2) {

            if (isFirst == true) {
                userService.getUsers().get(0).setUserStatus(true);
                gameEngine.setGameEngine();

                isFirst = false;
                return userService.getUsers().get(0).getUsername() + " your turn. You are playing with green buttons; " + userService.getUsers().get(1).getUsername() + " you are playing with grey buttons";

            } else if (isFirst == false) {
                for (User user : userService.getUsers()) {
                    if (user.isUserStatus()) {
                        return user.getUsername() + " your turn";
                    }
                }
                return null;
            }
        }
      return "waiting for another user to login";
    }

}
