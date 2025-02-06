package de.cargame.model.entity.gameobject.car.player;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import de.cargame.model.entity.gameobject.car.Car;
import de.cargame.model.handler.PlayerHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;


/**
 * Represents a human-controlled car within the game.
 * This abstract class extends the functionalities of the base `Car` class,
 * while introducing specific attributes and behaviors relevant to a player car.
 * <p>
 * Subclasses of PlayerCar must define specific behaviors and attributes,
 * such as speed and inertia, to differentiate between different types of player cars.
 */
@Getter
@Setter
public abstract class PlayerCar extends Car {


    private final double SCORE_INCREASE_NORMAL_SPEED;
    private final double SCORE_INCREASE_FAST_FORWARD_SPEED;
    private final int CRASH_COOLDOWN_TIME;
    private String playerId;
    private double inertia;
    private long lastCrashTime;
    private PlayerHandler playerHandler;
    private UserInputType currentUserInput = UserInputType.NONE;

    protected PlayerCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
        setPlayerHandler(playerHandler);

        SCORE_INCREASE_NORMAL_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.SCORE_INCREASE_NORMAL_SPEED);
        SCORE_INCREASE_FAST_FORWARD_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.SCORE_INCREASE_FAST_FORWARD_SPEED);
        CRASH_COOLDOWN_TIME = GameConfigService.getInstance().loadInteger(ConfigKey.CRASH_COOLDOWN_TIME);
    }


    /**
     * Updates the position of the player car within the game world. This method processes input
     * received from the player and calculates the movement based on the time elapsed and the
     * fast-forwarding state. It ensures that the car's position is updated while adhering to the
     * boundaries of the game world.
     *
     * @param deltaTime        The time elapsed since the last movement, used for calculating the distance.
     * @param isFastForwarding Indicates whether the player is in fast-forward mode, affecting speed.
     */
    @Override
    public void move(double deltaTime, boolean isFastForwarding) {
        handleUserInput();

        double gameObjectWidth = getBound().getBounds().getWidth();
        double gameObjectHeight = getBound().getBounds().getHeight();
        double distance = calculateDistance(deltaTime, isFastForwarding);

        moveCar(currentUserInput, distance, gameObjectWidth, gameObjectHeight);
    }


    /**
     * Processes the current user's input and updates the state of the player's active input type.
     * <p>
     * This method retrieves the latest user input from the player's input handler and determines if
     * it should update the current user input type for the player. If the retrieved input is different
     * from the current input type, a time-based inertia check is performed to CONFIRM if the input
     * change is valid. If valid, the current input type is updated; otherwise, it defaults to "NONE".
     * If no user input is available, the current input type is set to "NONE".
     * <p>
     * The following conditions are evaluated:
     * - If there is a new user input, it checks if the input type has changed compared to the current input.
     * - If the input type has changed, an inertia-based delay determines whether the change should take effect.
     * - If no input is present, the current input defaults to "NONE".
     * <p>
     * This method ensures a smooth transition between different input states, particularly in scenarios
     * where rapid changes in input could negatively affect gameplay.
     */
    private void handleUserInput() {
        Optional<UserInput> optionalUserInput = playerHandler.getCurrentUserInput();
        if (optionalUserInput.isPresent()) {
            UserInput userInput = optionalUserInput.get();
            if (userInput.getUserInputType() != currentUserInput) {
                if (userInput.timeElapsed(getInertia())) {
                    currentUserInput = userInput.getUserInputType();
                } else {
                    currentUserInput = UserInputType.NONE;
                }
            }
        } else {
            currentUserInput = UserInputType.NONE;
        }
    }


    /**
     * Calculates the distance the player car will travel based on the elapsed time and whether fast-forward mode is active.
     * This method determines the base distance using the car's speed and adjusts it depending on the fast-forward state.
     * It also increments the player's score accordingly.
     *
     * @param deltaTime        The time elapsed since the last movement, used to calculate the base distance traveled.
     * @param isFastForwarding A flag indicating whether the player is in fast-forward mode, which impacts the speed and score increment.
     * @return The calculated distance the car will travel, adjusted for the fast-forward state if applicable.
     */
    private double calculateDistance(double deltaTime, boolean isFastForwarding) {
        double baseDistance = getSpeed() * deltaTime;
        double scoreIncrement = isFastForwarding ? SCORE_INCREASE_FAST_FORWARD_SPEED : SCORE_INCREASE_NORMAL_SPEED;

        playerHandler.increaseScore(scoreIncrement);
        return isFastForwarding ? baseDistance + SCORE_INCREASE_FAST_FORWARD_SPEED : baseDistance;
    }

    private void moveCar(UserInputType userInputType, double distance, double gameObjectWidth, double gameObjectHeight) {
        if (userInputType == UserInputType.UP) {
            moveByRespectingGameBoundaries(0, -distance, gameObjectWidth, gameObjectHeight);

        } else if (userInputType == UserInputType.DOWN) {
            moveByRespectingGameBoundaries(0, distance, gameObjectWidth, gameObjectHeight);
        }
    }

    @Override
    public void setDespawnable() {
        this.isDespawnable = false;
    }

    @Override
    protected void setCollidable() {
        this.isCollidable = true;
    }


    /**
     * Checks whether the player car is currently under a crash cooldown period.
     * A crash cooldown is a defined time window during which subsequent crashes
     * are ignored to prevent multiple crash events from being triggered in quick succession.
     * <p>
     *
     * @return {@code true} if the time elapsed since the last crash is less than the defined cooldown period,
     * {@code false} otherwise.
     */
    public boolean hasCrashCooldown() {
        long now = System.currentTimeMillis();
        return (now - lastCrashTime) < CRASH_COOLDOWN_TIME;
    }


    /**
     * This method sets the last recorded crash time to the value returned by {@link System#currentTimeMillis()}.
     * Useful for tracking when the last crash event occurred, enabling functionality such as cooldown periods.
     */
    public void setLastCrashTime() {
        lastCrashTime = System.currentTimeMillis();
    }
}
