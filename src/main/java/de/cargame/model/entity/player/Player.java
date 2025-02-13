package de.cargame.model.entity.player;


import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputBundle;
import de.cargame.model.PlayerObservable;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.service.PlayerService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

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
@Slf4j
public class Player implements UserInputObserver, PlayerObservable {

    private final String id;
    private final int MAX_LIVES;
    private UserInputBundle userInputBundle;
    private Score score;
    private PlayerCar playerCar;
    private int lives;
    private boolean isPlaying;
    private CarType carSelection;
    private List<PlayerObserver> playerObservers = new CopyOnWriteArrayList<>();
    private PlayerService playerService;

    public Player(PlayerService playerService) {
        this.id = UUID.randomUUID().toString();
        this.playerService = playerService;
        this.userInputBundle = new UserInputBundle();
        this.isPlaying = false;
        this.MAX_LIVES = GameConfigService.getInstance().loadInteger(ConfigKey.MAX_LIVES);
        setDefaultValues();
    }

    @Override
    public void update(UserInputBundle userInputBundle) {
        this.userInputBundle = userInputBundle;
    }

    /**
     * Increases the player's current score value by a specified amount and notifies
     * all player observers of the updated state.
     *
     * @param value The amount to add to the player's score.
     */
    public void increaseScore(double value) {
        score.increaseScore(value);
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Resets the player to their default state, initializing key components such as
     * user input, score, lives, and playing status.
     * <p>
     * The method performs the following actions:
     * - Resets the user input bundle to its default state.
     * - Creates a new instance of the {@code Score} class to reset the player's score.
     * - Sets the player's lives to the maximum allowed value.
     * - Sets the playing status to {@code false}, indicating the player is not active.
     * - Notifies all observers of the player's current state using the observer pattern.
     */
    public void setDefaultValues() {
        this.userInputBundle.reset();
        this.score = new Score();
        this.lives = MAX_LIVES;
        this.isPlaying = false;
        log.debug("Default values set for player {}", id);
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Resets the player's lives to the maximum allowed value.
     * <p>
     * This method sets the player's lives to the value of `MAX_LIVES` and ensures that
     * the updated state is communicated to all registered observers by notifying them.
     * It is commonly used to restore the player to full health or lives during game resets or
     * similar scenarios.
     * <p>
     * Additionally, it logs the reset operation for debugging or tracking purposes.
     */
    public void resetLives() {
        this.lives = MAX_LIVES;
        log.debug("Lives reset for player {}", id);
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Resets the player's score to its default value.
     * This method invokes the underlying `Score` class's reset functionality
     * to initialize the score to a predefined default value (usually zero).
     * <p>
     * It logs the score reset action for the player, notifies all subscribed
     * observers about the updated player state, and ensures that external components
     * relying on the player's score are aware of the reset event.
     */
    public void resetScore() {
        score.reset();
        log.debug("Score reset for player {}", id);
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Increases the current number of lives for the player by one.
     * <p>
     * This method updates the player's `lives` state to reflect an additional life
     * granted, typically as a result of meeting specific game conditions or rewards.
     * After updating the state, it notifies all registered {@code PlayerObserver}s
     * with the updated player information, enabling them to react to the change (e.g.,
     * update UI elements or trigger other game behaviors).
     */
    public void increaseLife() {
        lives++;
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Decreases the player's number of lives by one and notifies all registered observers
     * of the updated player state.
     * <p>
     * This method modifies the player's state by decrementing the lives count. Following the
     * update, it triggers a notification to all {@link PlayerObserver} instances that have
     * subscribed to the player updates, allowing other components to react appropriately
     * to the change.
     */
    public void decreaseLife() {
        lives--;
        log.debug("Player {} lost a life, new life count: {}", id, lives);
        playerService.rumbleGamepad(1);
        notifyPlayerObserversWithCurrentValues();
    }

    /**
     * Determines whether the player is currently alive in the game.
     * A player is considered alive if they have more than zero lives remaining
     * and the game is in a playing state.
     *
     * @return true if the player has lives remaining and is currently playing; false otherwise.
     */
    public boolean isAlive() {
        return lives > 0 && isPlaying;
    }


    /**
     * Determines whether the player is currently in a fast-forwarding state based on user input.
     *
     * @return true if the player's fast-forwarding operation is active, false otherwise.
     */
    public boolean isFastForwarding() {
        return this.userInputBundle.isFastForward();
    }

    /**
     * Retrieves the latest user input associated with the player without requiring confirmation.
     * The returned input represents the most recent action or command provided by the player.
     *
     * @return An {@code Optional} containing the latest {@code UserInput} if available,
     * or an empty {@code Optional} if no input exists.
     */
    public Optional<UserInput> getUserInput() {
        return this.userInputBundle.getLatestInputWithoutConfirm();
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
