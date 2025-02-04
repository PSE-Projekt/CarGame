package de.cargame.model.entity.player;


import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputBundle;
import de.cargame.model.PlayerObservable;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Player class represents a player within the game and their associated state.
 * It encapsulates various properties such as player ID, score, lives, and input handling.
 * <p>
 * The Player class implements the `UserInputObserver` interface, allowing it to
 * observe and react to updates in user inputs. The main responsibilities of this class include:
 * <p>
 * - Managing the player's score, lives, and playing state.
 * - Handling player-specific game mechanics such as updating inputs and fast-forwarding.
 * - Resetting or initializing the player to a default state when required.
 */
@Getter
@Setter
public class Player implements UserInputObserver, PlayerObservable {

    private final String id;
    private final int MAX_LIVES;
    private UserInputBundle userInputBundle;
    private Score score;
    private PlayerCar playerCar;
    private int lives;
    private boolean isPlaying;
    private CarType carSelection;
    private List<PlayerObserver> playerObservers = new ArrayList<>();

    public Player() {
        this.id = UUID.randomUUID().toString();
        this.userInputBundle = new UserInputBundle();
        this.isPlaying = false;
        this.MAX_LIVES = GameConfigService.getInstance().loadInteger(ConfigKey.MAX_LIVES);
        setDefaultValues();
    }

    @Override
    public void update(UserInputBundle userInputBundle) {
        this.userInputBundle = userInputBundle;
    }

    public void increaseScore(double value) {
        score.increaseScore(value);
        notifyPlayerObserversWithCurrentValues();
    }

    public void setDefaultValues() {
        this.userInputBundle.reset();
        this.score = new Score();
        this.lives = MAX_LIVES;
        this.isPlaying = false;
        notifyPlayerObserversWithCurrentValues();
    }

    public void resetScore() {
        score.reset();
        notifyPlayerObserversWithCurrentValues();
    }

    public void increaseLife() {
        lives++;
        notifyPlayerObserversWithCurrentValues();
    }

    public void decreaseLife() {
        lives--;
        notifyPlayerObserversWithCurrentValues();
    }

    public boolean isAlive() {
        return lives > 0 && isPlaying;
    }


    public boolean isFastForwarding() {
        return this.userInputBundle.isFastForward();
    }

    public Optional<UserInput> getUserInput() {
        return this.userInputBundle.getLatestInput();
    }

    @Override
    public void addObserver(PlayerObserver playerObserver) {
        this.playerObservers.add(playerObserver);
    }

    @Override
    public void removeObserver(PlayerObserver observer) {
        this.playerObservers.remove(observer);
    }

    @Override
    public void notifyObservers(PlayerUpdate playerUpdate) {
        for (PlayerObserver observer : playerObservers) {
            observer.update(playerUpdate);
        }
    }

    private void notifyPlayerObserversWithCurrentValues() {
        notifyObservers(generatePlayerUpdate());
    }

    private PlayerUpdate generatePlayerUpdate() {
        return new PlayerUpdate(getId(), (int) getScore().getValue(), getLives());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
