package de.cargame.controller.entity;

import de.cargame.model.entity.gameobject.GameObject;
import lombok.Getter;

import java.util.List;

/**
 * Represents the data model for the current state of the game, including information
 * about the player and the game objects present within the game environment.
 * <p>
 * This class serves as a container that holds:
 * - The unique identifier for the player participating in the game.
 * - A list of active game objects, including vehicles, obstacles, and other entities
 * represented as instances of the {@link GameObject} class.
 * <p>
 * It is used to facilitate communication and synchronization between the game's logic,
 * rendering processes, and other components requiring access to the model state.
 */
@Getter
public class GameModelData {

    private final String playerId;
    private final List<GameObject> gameObjects;


    public GameModelData(String playerId, final List<GameObject> gameObjects) {
        this.playerId = playerId;
        this.gameObjects = gameObjects;
    }
}
