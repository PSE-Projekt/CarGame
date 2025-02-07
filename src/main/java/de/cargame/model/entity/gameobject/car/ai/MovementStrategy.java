package de.cargame.model.entity.gameobject.car.ai;

/**
 * An interface defining the contract for implementing a movement strategy for AI-controlled entities.
 * Classes implementing this interface are responsible for determining the movement behavior
 * of an entity by providing logic to calculate the target position and retrieve it as coordinates.
 * <p>
 * The implementing class must define how the target position is calculated based on the specific
 * behavior required for the entity (e.g., straight movement, random movement, or aggressive targeting).
 */
public interface MovementStrategy {

    /**
     * Calculates the target position for an entity based on the implemented movement strategy.
     * This method is responsible for determining the new target position, which may depend on
     * various factors such as randomization, current coordinates, or the constraints of
     * the game environment. It updates the target position to be used for subsequent movements.
     * <p>
     * The specific computation of the target position is defined in the implementing class
     * according to the movement strategy. Examples of such strategies include straight movement,
     * cross movement, or any custom behavior designed for AI entities.
     */
    void calcTargetPos();

    /**
     * Retrieves the x-coordinate of the target position calculated by the movement strategy.
     * The target position defines where the entity should move based on the implemented strategy logic.
     *
     * @return The x-coordinate of the target position as a double.
     */
    double getTargetPosX();

    /**
     * Retrieves the target Y-coordinate for the AI entity as calculated by the current movement strategy.
     * The target position represents the endpoint or goal towards which the entity aims to move.
     *
     * @return The Y-coordinate of the target position, as a double.
     */
    double getTargetPosY();

}
