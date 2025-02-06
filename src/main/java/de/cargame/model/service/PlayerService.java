package de.cargame.model.service;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PlayerService {

    private final InputService inputService = new InputService();

    @Getter
    private Player keyboardPlayer = new Player();

    @Getter
    private Player gamepadPlayer = new Player();


    public void createPlayerKeyboard() {
        log.debug("Creating keyboard player");
        inputService.initKeyboard();
        inputService.registerKeyboardObserver(keyboardPlayer);
        log.debug("Keyboard player created");
    }

    public void createPlayerGamepad() {
        log.debug("Creating gamepad player");
        inputService.initGamepad();
        inputService.registerGamePadObserver(gamepadPlayer);
        log.debug("Gamepad player created");
    }

    public void registerPlayerObserver(PlayerObserver observer, String playerId) {
        log.debug("Registering player-observer for player with id '{}'", playerId);
        if (playerId.equals(keyboardPlayer.getId())) {
            keyboardPlayer.addObserver(observer);
        } else if (playerId.equals(gamepadPlayer.getId())) {
            gamepadPlayer.addObserver(observer);
        }
        log.warn("For the player with id '{}' could no player-observer be registered, because there is no player with this id.", playerId);

    }

    public void registerInputObserver(UserInputObserver observer, String playerId) {
        log.debug("Registering input-observer for player with id '{}'", playerId);
        if (playerId.equals(keyboardPlayer.getId())) {
            inputService.registerKeyboardObserver(observer);
        } else if (playerId.equals(gamepadPlayer.getId())) {
            inputService.registerGamePadObserver(observer);
        }
        log.warn("For the player with id '{}' could no input-observer be registered, because there is no player with this id.", playerId);
    }


    public void setCarSelection(String playerId, CarType carType) {
        log.debug("Setting car selection '{}' for player with id '{}'", carType, playerId);
        getPlayerById(playerId).ifPresent(player -> player.setCarSelection(carType));
    }

    public void setPlaying(String playerId, boolean playing) {
        log.debug("Setting playing state '{}' for player with id '{}'", playing, playerId);
        getPlayerById(playerId).ifPresent(player -> player.setPlaying(playing));
    }

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
