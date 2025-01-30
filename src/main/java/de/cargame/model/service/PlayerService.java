package de.cargame.model.service;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;
import lombok.Getter;

import java.util.Optional;

public class PlayerService {

    private final InputService inputService = new InputService();

    @Getter
    private Player keyboardPlayer = new Player();

    @Getter
    private Player gamepadPlayer = new Player();


    public void createPlayerKeyboard() {
        inputService.initKeyboard(keyboardPlayer.getId());
        inputService.registerKeyboardObserver(keyboardPlayer);
    }

    public void createPlayerGamepad() {
        inputService.initGamepad(gamepadPlayer.getId());
        inputService.registerGamePadObserver(gamepadPlayer);
    }

    public void registerPlayerObservers(PlayerObserver observer) {
        keyboardPlayer.addObserver(observer);
        gamepadPlayer.addObserver(observer);
    }

    public void registerInputObservers(UserInputObserver observer) {
        inputService.registerKeyboardObserver(observer);
        inputService.registerGamePadObserver(observer);
    }


    public void setCarSelection(String playerId, CarType carType) {
        getPlayerById(playerId).ifPresent(player -> player.setCarSelection(carType));
    }

    public void setPlaying(String playerId, boolean playing) {
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
