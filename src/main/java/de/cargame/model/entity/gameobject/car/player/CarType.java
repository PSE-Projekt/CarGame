package de.cargame.model.entity.gameobject.car.player;

/**
 * Represents the different types of cars available in the game.
 * Each car type offers distinct attributes and gameplay characteristics.
 * <p>
 * FAST_CAR:
 * - Represents a high-speed car with slower inertia. Designed for players
 * seeking enhanced speed capabilities in exchange for reduced agility.
 * <p>
 * AGILE_CAR:
 * - Represents a car with enhanced agility and maneuverability. Designed for
 * players seeking faster response times and quicker turns at the cost of speed.
 * <p>
 * NONE:
 * - Represents the absence of a car type, used as a default or placeholder when no
 * specific car type is assigned.
 */
public enum CarType {

    FAST_CAR,
    AGILE_CAR,
    NONE
}
