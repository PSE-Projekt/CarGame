package de.cargame.model.entity.gameobject.car.ai;

/**
 * Defines the types of AI cars based on their movement patterns within the game.
 * It helps in categorizing AI-controlled cars and choosing appropriate movement strategies.
 * <p>
 * CROSS_MOVING:
 * Represents AI cars that follow a cross movement strategy.
 * Typically involves movement patterns designed to cross lanes or paths dynamically.
 * <p>
 * STRAIGHT_MOVING:
 * Represents AI cars that follow a straight movement strategy.
 * The movement is typically linear and does not deviate from a predefined path.
 */
public enum AICarType {
    CROSS_MOVING,
    STRAIGHT_MOVING
}
