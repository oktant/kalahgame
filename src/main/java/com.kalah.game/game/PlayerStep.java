package com.kalah.game.game;

/**
 * PlayerStep is POJO class for identifying movement of user
 * @author oalizada
 *
 */
public class PlayerStep {
    private String player;
    private String stepId;


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }


    @Override
    public String toString() {
        return  "11111111player='" + player + '\'' +
                ", stepId='" + stepId + '\'' +

                '}';
    }
}
