package de.cargame.model.entity.gameobject.interfaces;

/**
 * Represents an interface to define whether a game object is despawnable.
 * Implementing this interface indicates that the object has a state which determines
 * if it can be removed or despawned from the game environment.
 */
public interface Despawnable {

    /**
     * Determines if the object is despawnable within the game environment.
     * This method is typically used to check whether an object can be removed
     * or despawned from the game.
     *
     * @return true if the object is despawnable, false otherwise
     */
    boolean isDespawnable();
}
