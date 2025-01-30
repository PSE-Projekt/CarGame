package de.cargame.model.entity.player;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * The PlayerUpdate class represents an immutable data structure designed to encapsulate the
 * current state of a player that can be transmitted to observers. It is primarily used in
 * the observer design pattern for notifying {@link PlayerObserver} when the state of a player
 * changes, such as its score or remaining lives.
 * <p>
 * The class holds the following key pieces of information about a player:
 * - The unique identifier of the player.
 * - The player's current score value.
 * - The player's remaining number of lives.
 * <p>
 * Instances of PlayerUpdate are typically created when observing a player's real-time state changes
 * within the game and are passed to observers like panels or other components relying on player updates.
 */
@Getter
@AllArgsConstructor
public class PlayerUpdate {

    private final String playerId;
    private final int scoreValue;
    private final int lives;
}
