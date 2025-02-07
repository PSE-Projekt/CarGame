package de.cargame.model.entity.gameobject.interfaces;


/**
 * Represents an interface that defines the ability of an object to be identified as collidable
 * within the game environment. This allows for determining whether an object can
 * engage in collision detection.
 * <p>
 * Objects implementing Collidable typically indicate their collidable state by returning
 * true or false through the isCollidable method.
 */
public interface Collidable {
    /**
     * Determines if the object is collidable within the game environment.
     * This method is typically used to check whether an object can engage
     * in collision detection.
     *
     * @return true if the object is collidable, false otherwise
     */
    boolean isCollidable();
}
