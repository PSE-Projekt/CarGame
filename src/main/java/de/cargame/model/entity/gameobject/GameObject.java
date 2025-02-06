package de.cargame.model.entity.gameobject;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.interfaces.Collidable;
import de.cargame.model.entity.gameobject.interfaces.Despawnable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents an abstract base class for all game objects within the game world.
 * Each GameObject has a unique identifier, a boundary definition, and configurable
 * attributes such as whether it is static, collidable, or despawnable.
 * <p>
 * Responsibilities of this class include:
 * - Handling positional movement.
 * - Managing boundary constraints within the game world.
 * - Providing a blueprint for collision, despawning, and static behavior via abstract methods.
 * <p>
 * This class implements the {@link Collidable} and {@link Despawnable} interfaces to determine
 * whether the object can collide with other objects and/or be removed from the game world.
 * <p>
 * Subclasses must implement abstract methods to define specific behavior for
 * setting static, despawnable, and collidable properties, as well as movement logic.
 */
@Getter
@Setter
@Slf4j
public abstract class GameObject implements Collidable, Despawnable {


    protected final double GAME_SPEED;
    protected final double GAME_SPEED_FAST_FORWARD;
    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;
    private final String id = UUID.randomUUID().toString();
    protected GameObjectBound gameObjectBound;
    protected boolean isStatic;
    protected boolean isDespawnable;
    protected boolean isCollidable;
    protected GameMode gameMode;

    protected GameObject(double x, double y, int width, int height, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        GAME_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.GAME_SPEED);
        GAME_SPEED_FAST_FORWARD = GameConfigService.getInstance().loadDouble(ConfigKey.GAME_SPEED_FAST_FORWARD);
        SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);
        this.gameMode = gameMode;

        setDespawnable();
        setIsStatic();
        setCollidable();
        if (Objects.requireNonNull(gameObjectBoundType) == GameObjectBoundType.RECTANGLE) {
            gameObjectBound = new RectangularGameObjectBound(x, y, width, height);
            return;
        }
        log.error("A hitbox was tried to initialize with an illegal shape");
    }

    protected GameObject(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        this(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getHeight(), gameObjectBoundType ,gameMode);
    }

    protected abstract void setIsStatic();

    protected abstract void setDespawnable();

    protected abstract void setCollidable();

    @Override
    public boolean isDespawnable() {
        return isDespawnable;
    }

    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    public abstract void move(double deltaTime, boolean isFastForwarding);

    /**
     * Moves the current object by the specified amounts along the x and y axes.
     * This method delegates the movement to the underlying {@code gameObjectBound} object
     * associated with this instance.
     *
     * @param xAmount The amount by which to move the object along the x-axis.
     * @param yAmount The amount by which to move the object along the y-axis.
     */
    protected void moveBy(double xAmount, double yAmount) {
        gameObjectBound.moveBy(xAmount, yAmount);
    }


    /**
     * Moves the game object by specified amounts in the x and y directions while ensuring
     * the object remains within the boundaries of the game screen. If the object moves
     * outside the screen, its position is reverted to the previous location.
     *
     * @param xAmount      The amount to move the object along the x-axis.
     * @param yAmount      The amount to move the object along the y-axis.
     * @param objectWidth  The width of the object being moved.
     * @param objectHeight The height of the object being moved.
     */
    protected void moveByRespectingGameBoundaries(double xAmount, double yAmount, double objectWidth, double objectHeight) {
        double xOld = gameObjectBound.getCoordinate().getX();
        double yOld = gameObjectBound.getCoordinate().getY();

        // Objekt bewegen
        gameObjectBound.moveBy(xAmount, yAmount);

        double yNew = gameObjectBound.getCoordinate().getY();

        int heightBound = gameMode == GameMode.MULTIPLAYER ? SCREEN_HEIGHT / 2 : SCREEN_HEIGHT;

        if (yNew < 0 || yNew + objectHeight > heightBound) {
            gameObjectBound.getCoordinate().setX(xOld);
            gameObjectBound.getCoordinate().setY(yOld);
        }
    }


    public Shape getBound() {
        return gameObjectBound.getBound();
    }

    public Coordinate getCoordinates() {
        return gameObjectBound.getCoordinate();
    }

    public double getX() {
        return getCoordinates().getX();
    }

    public double getY() {
        return getCoordinates().getY();
    }

    public int getWidth() {
        return gameObjectBound.getBound().getBounds().width;
    }

    public int getHeight() {
        return gameObjectBound.getBound().getBounds().height;
    }
}
