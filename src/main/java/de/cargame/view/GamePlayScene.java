package de.cargame.view;

import de.cargame.model.GameInstance;


/**
 * Represents an interface for defining the layout and visual rendering logic of
 * a gameplay scene. Implementations of this interface handle the rendering of
 * the game state and model data within the application.
 */
public interface GamePlayScene {


    /**
     * Renders the current state of the game by processing the provided game instance.
     * This method is typically responsible for updating the visual representation of the game
     * by using the model data contained within the game instance.
     *
     * @param gameInstance the current instance of the game, encapsulating the game state, model data,
     *                     and other relevant information required for rendering.
     */
    void render(GameInstance gameInstance);
}
