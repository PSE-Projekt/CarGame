package de.cargame.view;

import de.cargame.view.image.*;

import java.awt.image.BufferedImage;

public class ImageService {


    private final GameImages agileCarImages = new AgileCarImages();
    private final GameImages fastCarImages = new FastCarImages();
    private final GameImages buildingImage = new BuildingImages();
    private final GameImages kamikazeCarImage = new KamikazeCarImages();
    private final GameImages liveImage = new LifeImages();
    private final GameImages obstacleImage = new ObstacleImages();


    public BufferedImage getRandomAgileCarImage(String seed) {
        return agileCarImages.getRandomImage(seed);
    }

    public BufferedImage getRandomFastCarImage(String seed) {
        return fastCarImages.getRandomImage(seed);
    }

    public BufferedImage getRandomBuildingImage(String seed) {
        return buildingImage.getRandomImage(seed);
    }

    public BufferedImage getRandomKamikazeCarImage(String seed) {
        return kamikazeCarImage.getRandomImage(seed);
    }

    public BufferedImage getRandomLiveImage(String seed) {
        return liveImage.getRandomImage(seed);
    }

    public BufferedImage getRandomObstacleImage(String seed) {
        return obstacleImage.getRandomImage(seed);
    }


    public BufferedImage getRandomImage(GameImages gameImages, String seed) {
        return gameImages.getRandomImage(seed);
    }

}
