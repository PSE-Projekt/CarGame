package de.cargame.model.entity.player;


import lombok.Getter;


/**
 * The Score class represents the score tracking system for a player in the game.
 * It encapsulates a score value and provides methods for manipulating it.
 * <p>
 * This class supports:
 * - Increasing the current score value by a specified amount.
 * - Resetting the score to a default value.
 * <p>
 * The score value starts at 0 by default and can be adjusted according to game rules
 * or interactions. It is commonly used to track player progress or performance.
 */
public class Score {

    private final double DEFAULT_VALUE = 0;
    @Getter
    private double value = 0;


    /**
     * Increases the current score value by the specified amount.
     *
     * @param value The amount to increase the score by. This value is added
     *              to the current score.
     */
    public void increaseScore(double value) {
        if(value >= 0) {
            this.value += value;
        }
    }


    /**
     * Resets the score value to its default value.
     * <p>
     * This method is used to reinitialize the score to its predefined
     * default state, which is typically necessary when starting a new game.
     */
    public void reset() {
        value = DEFAULT_VALUE;
    }
}
