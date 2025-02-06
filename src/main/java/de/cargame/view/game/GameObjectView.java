package de.cargame.view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObjectView extends ImageView implements Comparable<GameObjectView> {
    private final int weight;
    private final String gameObjectId;

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
