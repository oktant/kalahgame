package com.kalah.game.game;

/**
 * Bean Identifies the main logic of the game;
 *
 * @author oalizada
 */


import com.kalah.game.model.User;
import com.kalah.game.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

@Component

public class GameEngine {
    @Autowired
    UserService userService;
    @Autowired
    GameBoard gameBoard;
    @Autowired
    GameRules gameRules;


    public void setGameEngine() {
        gameBoard.setBoard();
    }

    public Map<String, Integer> getMapGameBoard() {
        return gameBoard.getMapGameBoard();
    }

    public void userTurn(User user) {
        gameRules.switchingUsers(userService, user);
    }

    /**
        *Updating the board when needed. Takes parameters keyStep which is user step and the user which played.
        * makes changes to board;
        * @param String keyStep
        * @param User user
     */
    public void updateBoard(String keyStep, User user) {
        boolean nextAttempt = false;
        Integer value = gameBoard.getMapGameBoard().get(keyStep);
        int numericValueOfElement = Integer.parseInt(keyStep.substring(1, keyStep.length()));

        String tempSwitcher = "" + keyStep.charAt(0);
        for (int i = 0; i < value; i++) {
            nextAttempt = false;

            if (numericValueOfElement > 15 && tempSwitcher.equals("a")) {
                if (user.getType().equals("a")) {
                    gameBoard.getMapGameBoard().put("u1", gameBoard.getMapGameBoard().get("u1") + 1);
                    nextAttempt = true;
                }
                else if(user.getType().equals("b")){
                    i--;
                }
                tempSwitcher = "b";
                numericValueOfElement = 10;
            } else if (numericValueOfElement > 15 && tempSwitcher.equals("b")) {
                if (user.getType().equals("b")) {

                    gameBoard.getMapGameBoard().put("u2", gameBoard.getMapGameBoard().get("u2") + 1);
                    nextAttempt = true;
                }
                else if(user.getType().equals("a"))
                {
                    i--;
                }

                tempSwitcher = "a";
                numericValueOfElement = 10;
            } else {

                numericValueOfElement++;
                String tempPosition = tempSwitcher + numericValueOfElement;

                gameBoard.getMapGameBoard().put(tempPosition, gameBoard.getMapGameBoard().get(tempPosition) + 1);
                String tempSwitcherOpposite = "";
                if (tempSwitcher.equals("a")) {
                    tempSwitcherOpposite = "b";
                } else {
                    tempSwitcherOpposite = "a";
                }
                if (i == (value - 1) && tempSwitcher.equals(user.getType()) && gameBoard.getMapGameBoard().get(tempPosition) == 1
                        && gameBoard.getMapGameBoard().get(tempSwitcherOpposite + (27 - numericValueOfElement)) != 0) {


                    gameBoard.getMapGameBoard().put(tempPosition, 0);
                    if (user.getType().equals("a")) {
                        gameBoard.getMapGameBoard().put("u1",
                                gameBoard.getMapGameBoard().get("u1") + 1 + gameBoard.getMapGameBoard().get(tempSwitcherOpposite + (27 - numericValueOfElement)));
                        gameBoard.getMapGameBoard().put(tempSwitcherOpposite + (27 - numericValueOfElement), 0);
                    } else {
                        gameBoard.getMapGameBoard().put("u2",
                                gameBoard.getMapGameBoard().get("u2") + 1 + gameBoard.getMapGameBoard().get(tempSwitcherOpposite + (27 - numericValueOfElement)));
                        gameBoard.getMapGameBoard().put(tempSwitcherOpposite + (27 - numericValueOfElement), 0);
                    }


                }
            }


        }
        gameBoard.getMapGameBoard().put(keyStep, 0);
        if (!nextAttempt) {
            userTurn(user);
        }


    }


    /**
      *Returns a winner
     * @return String user1, if first user wins, user2 if second and draw if none of the user wins
      */
    public String returnWinner() {
        return gameRules.returnWinner(gameBoard);
    }
}
