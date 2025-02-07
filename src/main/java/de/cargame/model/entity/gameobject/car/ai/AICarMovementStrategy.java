package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;

import java.util.Random;

/**
 * An abstract class that defines the strategy for AI car movement within the game.
 * This class implements the {@link MovementStrategy} interface and provides default
 * behavior and attributes required for movement strategies. Concrete subclasses
 * are responsible for implementing the actual logic for calculating the target position.
 * <p>
 * The movement behavior is influenced by factors such as the spawning coordinate,
 * the game mode, and the screen height, which are initialized at creation and utilized
 * to compute the target position.
 */
public abstract class AICarMovementStrategy implements MovementStrategy {

    protected final Coordinate gameObjectSpawnCoordinate;
    protected final int SCREEN_HEIGHT;
    protected final int SCREEN_WIDTH;
    protected Coordinate targetPos;
    protected GameMode gameMode;
    protected final Random random;


    protected AICarMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        this.random = new Random(); // Initialize
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);
        SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.gameMode = gameMode;
        this.gameObjectSpawnCoordinate = gameObjectSpawnCoordinate;
        calcTargetPos();
    }

    @Override

    public double getTargetPosX() {
        return targetPos.getX();
    }

    @Override
    public double getTargetPosY() {
        return targetPos.getY();
    }
}
