package de.cargame.view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a GameObjectView, which is a ImageView with a weight and a gameObjectId.
 * The weight is used to sort the GameObjectViews in the GameObjectViewList.
 */
public class GameObjectView extends ImageView implements Comparable<GameObjectView> {
    private final int weight;
    private final String gameObjectId;

    /**
     * Creates a new GameObjectView instance.
     * @param image The image to be displayed.
     * @param weight The weight of the GameObjectView.
     * @param gameObjectId The id of the GameObject.
     */
    public GameObjectView(Image image, int weight, String gameObjectId) {
        super(image);
        this.weight = weight;
        this.gameObjectId = gameObjectId;
    }

    @Override
    public int compareTo(GameObjectView o) {
        int compareValue = Integer.compare(this.weight, o.weight);

        if (compareValue == 0) {
            return this.gameObjectId.compareTo(o.gameObjectId); // Use direct comparison
        }

        return compareValue;
    }
}
