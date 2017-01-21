package com.kalah.game.game;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean which defines and sets the board
 * @author oalizada
 */
@Component
public class GameBoard {
    private Map<String, Integer> mapGameBoard=new HashMap<>();
    public void setBoard() {
        mapGameBoard.put("a11", 6);
        mapGameBoard.put("a12", 6);
        mapGameBoard.put("a13", 6);
        mapGameBoard.put("a14", 6);
        mapGameBoard.put("a15", 6);
        mapGameBoard.put("a16", 6);
        mapGameBoard.put("u1", 0);
        mapGameBoard.put("b11", 6);
        mapGameBoard.put("b12", 6);
        mapGameBoard.put("b13", 6);
        mapGameBoard.put("b14", 6);
        mapGameBoard.put("b15", 6);
        mapGameBoard.put("b16", 6);
        mapGameBoard.put("u2", 0);

    }


    public Map<String, Integer> getMapGameBoard(){
        return mapGameBoard;
    }
}
