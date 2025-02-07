package de.cargame.controller;

import de.cargame.controller.api.PlayerAPI;
import de.cargame.exception.PlayerNotFoundException;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

/**
 * The PlayerController class provides implementation for the PlayerAPI interface.
 * It acts as a bridge between higher-level application logic and the underlying PlayerService,
 * which manages the creation, data retrieval, and state configuration of players.
 * Players can be controlled using either a keyboard or a gamepad, and the class
 * provides methods to manage both types.
 */
@Slf4j
public class PlayerController implements PlayerAPI {

    private final PlayerService playerService;


    /**
     * Constructs a new PlayerController with the specified PlayerService.
     * The PlayerService is used to manage player-related operations, such as
     * creating players, retrieving player data, and setting player states.
     *
     * @param playerService the PlayerService instance to be used for managing player operations
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void createPlayerKeyboard() {
        playerService.createPlayerKeyboard();
    }

    @Override
    public void createPlayerGamepad() {
        playerService.createPlayerGamepad();
    }

    @Override
    public String getKeyboardPlayerId() {
        return getKeyboardPlayer().getId();
    }

    @Override
    public String getGamepadPlayerId() {
        return getGamepadPlayer().getId();
    }

    @Override
    public Player getKeyboardPlayer() {
        Player keyboardPlayer = playerService.getKeyboardPlayer();
        if (keyboardPlayer == null) {
            throw new PlayerNotFoundException("Player not present");
        }
        return keyboardPlayer;
    }

    @Override
    public Player getGamepadPlayer() {
        Player gamePadPlayer = playerService.getGamepadPlayer();
        if (gamePadPlayer == null) {
            throw new PlayerNotFoundException("Player not present");
        }
        return gamePadPlayer;
    }

    @Override
    public void setCarSelection(String playerId, CarType carType) {
        playerService.setCarSelection(playerId, carType);
    }

    @Override
    public void setPlaying(String playerId, boolean playing) {
        playerService.setPlaying(playerId, playing);
    }

    @Override
    public boolean isPlaying(String playerId) {
        return playerService.isPlaying(playerId);
    }

    @Override
    public void registerInputObserver(UserInputObserver observer, String playerId) {
        playerService.registerInputObserver(observer, playerId);
    }

    @Override
    public void registerPlayerObserver(PlayerObserver observer, String playerId) {
        playerService.registerPlayerObserver(observer, playerId);
    }
}
