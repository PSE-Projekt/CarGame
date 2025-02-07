package de.cargame.controller.entity;

/**
 * Represents the various states or screens within the gameplay experience.
 * This enumeration is used to define the current state of the game, enabling
 * transitions and navigation flows between these different stages.
 * <p>
 * The following states are defined:
 * <p>
 * - MAIN_MENU: Represents the initial or main menu of the game, where the player
 * can access options such as starting a game or configuring settings.
 * - CAR_SELECTION: Represents the car selection screen, where the player(s) can choose
 * a vehicle before starting the game.
 * - IN_GAME: Represents the active gameplay state where the player is participating
 * in the game.
 * - SCORE_BOARD: Represents the score board screen where scores and results of the game
 * are displayed.
 */
public enum GameState {

    MAIN_MENU,
    CAR_SELECTION,
    IN_GAME,
    SCORE_BOARD


}
