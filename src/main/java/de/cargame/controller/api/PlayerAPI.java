package de.cargame.controller.api;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;


/**
 * The PlayerAPI interface provides methods to manage player creation, retrieve player information,
 * and configure player state in the game. It supports operations for both keyboard and gamepad-controlled players.
 */
public interface PlayerAPI {


    /**
     * Creates a keyboard-controlled player and initializes associated resources.
     * This method is responsible for configuring the player entity that will
     * interact with the game using keyboard inputs. It must be called before
     * attempting to use any operations or configurations specific to a
     * keyboard-controlled player.
     * <p>
     * The created player can later be retrieved using methods like
     * {@code getKeyboardPlayer()} or referenced using its unique player ID
     * acquired via {@code getKeyboardPlayerId()}.
     * <p>
     * This method may also register the player for further operations, such as
     * input handling or player state management, depending on the implementation.
     */
    void createPlayerKeyboard();

    /**
     * Creates a new gamepad-controlled player and initializes its state within the game.
     * This method is responsible for setting up the necessary configurations and ensuring
     * that the gamepad player can participate in the game. Should be called before attempting to
     * start or interact with the gamepad player in the game flow.
     * <p>
     * The method interacts with the player-related components to establish the gamepad player,
     * which includes assigning an identifier and registering any relevant configurations.
     */
    void createPlayerGamepad();

    /**
     * Retrieves the unique identifier associated with the keyboard-controlled player.
     *
     * @return a String representing the ID of the player controlled via keyboard.
     */
    String getKeyboardPlayerId();

    /**
     * Retrieves the unique identifier of the player associated with a gamepad.
     *
     * @return a String representing the unique identifier of the gamepad player.
     * If no gamepad-controlled player exists, this method may return null or an empty string
     * depending on the implementation.
     */
    String getGamepadPlayerId();

    /**
     * Retrieves the player entity associated with keyboard controls.
     * This player is initialized and managed through the PlayerAPI interface and
     * represents a user playing the game using keyboard input.
     *
     * @return the Player instance representing the keyboard-controlled player.
     */
    Player getKeyboardPlayer();

    /**
     * Retrieves the player associated with a gamepad input device.
     * This method is used to obtain the Player instance that represents
     * the gamepad-controlled player in the game.
     *
     * @return the Player object representing the gamepad-controlled player.
     */
    Player getGamepadPlayer();

    /**
     * Sets the car selection for a specific player.
     *
     * @param playerId the unique identifier of the player whose car selection is being set
     * @param carType  the type of car to assign to the player
     */
    void setCarSelection(String playerId, CarType carType);

    /**
     * Updates the playing status of a player in the game.
     *
     * @param playerId the unique identifier of the player whose playing status is being updated
     * @param playing  a boolean indicating whether the player is currently playing (true) or not (false)
     */
    void setPlaying(String playerId, boolean playing);

    /**
     * Checks if a player with the given player ID is currently in a playing state.
     *
     * @param playerId the unique identifier of the player whose playing state is to be checked
     * @return {@code true} if the player is currently playing; {@code false} otherwise
     */
    boolean isPlaying(String playerId);

    /**
     * Registers an observer for receiving updates about user input events associated with a specific player.
     *
     * @param observer the {@code UserInputObserver} instance to be notified when user input events occur.
     * @param playerId the unique identifier of the player whose input events should be observed.
     */
    void registerInputObserver(UserInputObserver observer, String playerId);

    /**
     * Registers a player observer to receive updates related to the state of a specific player.
     * The observer will be notified of any changes in the player's state, enabling the implementation
     * of reactive behaviors and updates, such as user interface adjustments or game logic responses.
     *
     * @param observer The PlayerObserver instance that will be registered to listen for player updates.
     * @param playerId The unique identifier of the player whose state changes are to be observed.
     */
    void registerPlayerObserver(PlayerObserver observer, String playerId);

}
