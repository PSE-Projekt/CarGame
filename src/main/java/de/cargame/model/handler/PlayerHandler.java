package de.cargame.model.handler;

import de.cargame.controller.input.UserInput;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Setter
@Slf4j
public class PlayerHandler {


    @Getter
    private Player player;

    public PlayerHandler(Player player) {
        this.player = player;
    }


    public void increaseScore(double value) {
        player.increaseScore(value);
    }

    public void resetScore() {
        player.resetScore();
    }

    public Optional<UserInput> getCurrentUserInput() {
        return player.getUserInput();
    }

    public boolean isFastForwarding() {
        return player.isFastForwarding();
    }

    public void increaseLife() {
        player.increaseLife();
    }


    public void decreaseLife() {
        player.decreaseLife();
    }

    public int getLifeCount() {
        return player.getLives();
    }

    public void setPlayerCar(PlayerCar playerCar) {
        player.setPlayerCar(playerCar);
    }

    public double getScore() {
        return player.getScore().getValue();
    }


    public boolean isPlayerAlive() {
        return player.isAlive();
    }

}
