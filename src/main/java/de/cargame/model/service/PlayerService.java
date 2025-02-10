package de.cargame.model.service;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * The PlayerService class is responsible for managing player setup, interactions,
 * and state operations in the system. It provides methods for creating and initializing
 * player input mechanisms (keyboard and gamepad), registering observers for players and their
 * input devices, and updating or retrieving player states.
 */
@Slf4j
public class PlayerService {

    private final InputService inputService = new InputService();

    @Getter
    private Player keyboardPlayer = new Player();

    @Getter
    private Player gamepadPlayer = new Player();


    /**
     * Creates and configures the keyboard input mechanism for the keyboard player.
     * This method initializes the keyboard, registers the keyboard player as an observer
     * for keyboard inputs, and logs the process.
     */
    public void createPlayerKeyboard() {
        log.debug("Creating keyboard player: {}", keyboardPlayer.getId());
        inputService.initKeyboard();
        inputService.registerKeyboardObserver(keyboardPlayer);
        log.debug("Keyboard player created");
    }

    /**
     * Creates and initializes a gamepad player.
     * This method sets up the required gamepad input device and links it to the gamepad player.
     * It also registers the gamepad player as an observer of the gamepad input events.
     * The setup ensures the gamepad player can receive and respond to gamepad input.
     */
    public void createPlayerGamepad() {
        log.debug("Creating gamepad player: {}", gamepadPlayer.getId());
        inputService.initGamepad();
        inputService.registerGamePadObserver(gamepadPlayer);
        log.debug("Gamepad player created");
    }

    /**
     * Registers a PlayerObserver instance to observe and handle updates for a specific player.
     * The observer will be notified when the state of the player corresponding to the given playerId changes.
     * If the playerId does not correspond to a valid player, a warning will be logged.
     *
     * @param observer the PlayerObserver instance that will observe the player's state updates.
     * @param playerId the unique identifier of the player to be observed.
     *                 Must match the ID of an existing player (e.g., keyboard player or gamepad player).
     */
    public synchronized void registerPlayerObserver(PlayerObserver observer, String playerId) {
        log.debug("Registering player-observer for player with id '{}'", playerId);
        if (playerId.equals(keyboardPlayer.getId())) {
            keyboardPlayer.addObserver(observer);
        } else if (playerId.equals(gamepadPlayer.getId())) {
            gamepadPlayer.addObserver(observer);
        }
        log.warn("For the player with id '{}' could no player-observer be registered, because there is no player with this id.", playerId);

    }

    /**
     * Registers a user input observer for a specified player. The observer will monitor
     * and handle inputs from the player's assigned input device (keyboard or gamepad).
     * If the player ID does not match any existing players, a warning will be logged.
     *
     * @param observer the observer instance implementing the UserInputObserver interface,
     *                 responsible for processing input updates.
     * @param playerId the unique identifier of the player whose input observer is being registered.
     */
    public synchronized void registerInputObserver(UserInputObserver observer, String playerId) {
        log.debug("Registering input-observer for player with id '{}'", playerId);
        if (playerId.equals(keyboardPlayer.getId())) {
            inputService.registerKeyboardObserver(observer);
        } else if (playerId.equals(gamepadPlayer.getId())) {
            inputService.registerGamePadObserver(observer);
        }
        log.warn("For the player with id '{}' could no input-observer be registered, because there is no player with this id.", playerId);
    }


    /**
     * Sets the car selection for a specified player.
     *
     * @param playerId the unique identifier for the player whose car selection is to be set
     * @param carType  the type of car to be assigned to the player
     */
    public void setCarSelection(String playerId, CarType carType) {
        log.debug("Setting car selection '{}' for player with id '{}'", carType, playerId);
        getPlayerById(playerId).ifPresent(player -> player.setCarSelection(carType));
    }

    /**
     * Sets the playing state for a player identified by their player ID.
     *
     * @param playerId the unique identifier of the player whose playing state is to be updated.
     * @param playing  the new playing state to set for the player.
     */
    public void setPlaying(String playerId, boolean playing) {
        log.debug("Setting playing state '{}' for player with id '{}'", playing, playerId);
        getPlayerById(playerId).ifPresent(player -> player.setPlaying(playing));
    }

    /**
     * Checks whether the player with the given ID is currently playing.
     *
     * @param playerId the unique identifier of the player
     * @return true if the player is playing (alive), false otherwise
     */
    public boolean isPlaying(String playerId) {
        return getPlayerById(playerId).map(Player::isAlive).orElse(false);
    }

    private Optional<Player> getPlayerById(String playerId) {
        if (playerId.equals(keyboardPlayer.getId())) {
            return Optional.of(keyboardPlayer);
        } else if (playerId.equals(gamepadPlayer.getId())) {
            return Optional.of(gamepadPlayer);
        }
        return Optional.empty();
    }


}
