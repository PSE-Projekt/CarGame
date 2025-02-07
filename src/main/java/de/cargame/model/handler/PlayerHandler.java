package de.cargame.model.handler;

import de.cargame.controller.input.UserInput;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


/**
 * The PlayerHandler class serves as a wrapper around the Player instance,
 * exposing various methods to interact with and manage the player's state.
 * It provides functionality for modifying score, lives, input, and other
 * player-related attributes from higher-level components.
 * <p>
 * This class acts as a bridge between the game logic and the underlying Player
 * implementation, enabling clean and modular interactions.
 */
@Setter
@Slf4j
public class PlayerHandler {


    @Getter
    private Player player;

    public PlayerHandler(Player player) {
        this.player = player;
    }


    /**
     * Increases the current score of the player by the specified value. This delegates
     * the operation to the internal Player instance associated with this PlayerHandler.
     *
     * @param value The amount by which the player's score should be increased.
     */
    public void increaseScore(double value) {
        player.increaseScore(value);
    }

    /**
     * Resets the player's score to the predefined default value.
     * This method delegates the score reset functionality to the underlying Player instance.
     * It ensures that the player's score is initialized to its default state, allowing
     * for scenarios such as restarting the game or resetting player progress.
     */
    public void resetScore() {
        player.resetScore();
    }

    /**
     * Retrieves the current user input associated with the player encapsulated within this handler.
     * The input represents the latest action or command provided by the user.
     *
     * @return An {@code Optional} containing the current {@code UserInput} if available,
     *         or an empty {@code Optional} if no input exists.
     */
    public Optional<UserInput> getCurrentUserInput() {
        return player.getUserInput();
    }

    /**
     * Checks whether the player is currently in a fast-forwarding state.
     *
     * @return true if the player is performing a fast-forwarding action, false otherwise.
     */
    public boolean isFastForwarding() {
        return player.isFastForwarding();
    }

    /**
     * Increases the player's life count by delegating the operation to the underlying {@code Player} instance.
     * <p>
     * This method invokes the {@code increaseLife} method on the {@code Player}, which increments the player's
     * current number of lives. This change typically reflects scenarios such as the collection of a life reward
     * during gameplay. Any observers registered to the {@code Player} are notified of the updated state as part
     * of this operation.
     */
    public void increaseLife() {
        player.increaseLife();
    }


    /**
     * Decreases the player's life count by one.
     * This method serves as a wrapper around the Player class's corresponding functionality,
     * delegating the operation to the underlying Player instance.
     * <p>
     * Calling this method reduces the player's remaining lives and may trigger
     * further notifications or state updates within the game, allowing other components
     * to respond to the change in player state.
     */
    public void decreaseLife() {
        player.decreaseLife();
    }

    /**
     * Retrieves the current life count of the player.
     *
     * @return the number of lives the player currently has.
     */
    public int getLifeCount() {
        return player.getLives();
    }

    /**
     * Assigns a PlayerCar instance to the player. This method updates the player's
     * active car configuration to the specified PlayerCar instance.
     *
     * @param playerCar The PlayerCar instance to be set for the player.
     *                  Represents the car controlled by the player in the game.
     */
    public void setPlayerCar(PlayerCar playerCar) {
        player.setPlayerCar(playerCar);
    }

    /**
     * Retrieves the current score of the player.
     *
     * @return the player's current score as a double value.
     */
    public double getScore() {
        return player.getScore().getValue();
    }


    /**
     * Checks if the player associated with this handler is currently alive.
     * A player is considered alive if they have more than zero lives remaining
     * and the game is in a playing state.
     *
     * @return true if the player has lives remaining and the game state allows for playing; false otherwise.
     */
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

}
