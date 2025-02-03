package de.cargame.controller.api;

import de.cargame.controller.entity.GameModelData;

import java.util.List;


/**
 * The GameInstanceAPI interface provides methods to manage and control
 * game instances in the application. It allows initiating games for
 * players using different input devices, resetting game instances,
 * and retrieving game model data.
 */
public interface GameInstanceAPI {

    /**
     * Initiates a game session for a keyboard-controlled player.
     * This method retrieves the player associated with the keyboard from the PlayerAPI
     * and starts the game for that player by delegating the operation to the GameInstanceService.
     * <br>
     * This action assumes that a keyboard-controlled player has been created and configured
     * prior to calling this method.
     */
    void startGamePlayerKeyboard();

    /**
     * Initiates a game session for a gamepad-controlled player.
     * This method retrieves the player associated with the gamepad from the PlayerAPI
     * and starts the game for that player by delegating the operation to the GameInstanceService.
     * <p>
     * This action assumes that a gamepad-controlled player has been created and configured
     * prior to calling this method.
     */
    void startGamePlayerGamePad();

    /**
     * Resets all currently active game instances in the application.
     * This operation restores the state of all game-related activities to their default or initial configuration,
     * effectively preparing the system for a new session or a fresh restart.
     * <p>
     * It might involve clearing active players, resetting game objects, and reverting to uninitialized states.
     * This method should be used cautiously to avoid unintended disruptions to ongoing game sessions.
     */
    void resetGameInstances();

    /**
     * Retrieves a list of game model data objects. Each object contains
     * the player ID and a list of game objects associated with that player.
     *
     * @return a list of GameModelData instances representing the current state
     * of the game model, including information about players and their game-related objects.
     */
    List<GameModelData> getModel();
}
