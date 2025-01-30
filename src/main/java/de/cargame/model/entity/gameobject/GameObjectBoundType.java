package de.cargame.model.entity.gameobject;


/**
 * Enum representing the type of boundary that can be used for a GameObject in the game world.
 * This allows different shapes or types of boundaries to be specified and utilized
 * for collision detection or rendering purposes.
 * <p>
 * Currently, this enum includes:
 * - RECTANGLE: Represents a rectangular boundary for a GameObject.
 * <p>
 * This enum allows for extensibility if additional boundary types need to be introduced
 * in the future.
 */
public enum GameObjectBoundType {
    RECTANGLE
}
