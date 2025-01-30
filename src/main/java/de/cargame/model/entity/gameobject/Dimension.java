package de.cargame.model.entity.gameobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the dimensional attributes of an object within the game world.
 * This class encapsulates the width and height of an object.
 */
@AllArgsConstructor
@Getter
public class Dimension {

    private int width;
    private int height;


}
