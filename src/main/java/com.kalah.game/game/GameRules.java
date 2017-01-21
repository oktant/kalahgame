package com.kalah.game.game;

/**
 * Bean implementing all rules in game
 * @author oalizada
 */

import com.kalah.game.model.User;
import com.kalah.game.model.UserService;
import org.springframework.stereotype.Component;

import java.util.Map;



@Component
public class GameRules {

    /*
     * Method which switches the user's turn
     */
    public void switchingUsers(UserService userService, User user){
        user.setUserStatus(false);
        for (User u1 : userService.getUsers()) {
            if (!u1.getUsername().equals(user.getUsername())) {
                u1.setUserStatus(true);

            }
        }
    }


    /**
     *Returns a winner
     * @return String user1, if first user wins, user2 if second and draw if none of the user wins
     */
    public String returnWinner(GameBoard gameBoard){
        if(isGameOver(gameBoard)){
            int user1= gameBoard.getMapGameBoard().get("u1");
            int user2= gameBoard.getMapGameBoard().get("u2");
            if(user1>user2){
                return "user1";
            }
            else if(user1<user2){
                return "user2";
            }
            else{
                return "draw";
            }

        }
        return "notEnded";
    }
    /**
     * Method checks whether the game is over
     * @param Gameboard gameboard
     * @return boolean
     */
    private boolean isGameOver(GameBoard gameBoard){
        int aSum=0;
        int bSum=0;

        for(Map.Entry<String, Integer> i :  gameBoard.getMapGameBoard().entrySet()){
            if(i.getKey().charAt(0)=='a'){
                aSum=aSum+i.getValue();
            }
            else if(i.getKey().charAt(0)=='b'){
                bSum=bSum+i.getValue();
            }
        }
        if(aSum==0 || bSum==0){
            return true;
        }
        return false;
    }
}
