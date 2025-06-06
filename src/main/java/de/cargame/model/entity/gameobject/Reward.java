package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.handler.PlayerHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an abstract base class for rewards in the game.
 * <p>
 * Rewards are collectible game objects that provide certain benefits or effects
 * to the player when collected. This class extends the {@code GameObject} class,
 * inheriting its fundamental properties such as position, dimensions, boundaries,
 * and movement. The class also introduces specific reward-related behavior.
 */
@Getter
@Setter
public abstract class Reward extends GameObject {

    private boolean collected = false;

    protected Reward(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
    }

    public abstract boolean collect(PlayerHandler playerHandler);


    @Override
    public void move(double deltaTime, boolean isFastForwarding) {
        if (isFastForwarding) {
            moveBy(-GAME_SPEED_FAST_FORWARD * deltaTime, 0);
        } else {
            moveBy(-GAME_SPEED * deltaTime, 0);
        }
    }

    @Override
    protected void setIsStatic() {
        this.isStatic = true;
    }

    @Override
    protected void setDespawnable() {
        this.isDespawnable = true;
    }

    @Override
    protected void setCollidable() {
        this.isCollidable = true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
