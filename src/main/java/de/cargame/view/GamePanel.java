package de.cargame.view;

import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.gameobject.*;
import de.cargame.model.entity.gameobject.car.ai.AICar;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class GamePanel extends JPanel {


    private final ImageService imageService = new ImageService();
    protected GameModelData gameModelData;

    public GamePanel() {
        setDoubleBuffered(true);
        setOpaque(false);
        setVisible(true);
    }


    public void render(GameModelData gameModelData) {
        this.gameModelData = gameModelData;
        repaint();
    }

    protected void paintGameObjects(List<GameObject> gameObjects, Graphics2D g2d) {

        for (GameObject gameObject : gameObjects) {

            int x = (int) gameObject.getX();
            int y = (int) gameObject.getY();
            int width = gameObject.getWidth();
            int height = gameObject.getHeight();

            Shape bounds = gameObject.getBound();
            String gameObjectId = gameObject.getId();


            if (gameObject instanceof RoadMark) {
                g2d.setColor(Color.GRAY);
                g2d.fill(bounds);
            } else if (gameObject instanceof AICar) {
                BufferedImage kamikazeCarImage = imageService.getRandomKamikazeCarImage(gameObjectId);
                drawImage(g2d, kamikazeCarImage, x, y, width, height);
            } else if (gameObject instanceof Obstacle) {
                BufferedImage obstacleImage = imageService.getRandomObstacleImage(gameObjectId);
                drawImage(g2d, obstacleImage, x, y, width, height);
            } else if (gameObject instanceof Reward reward) {
                if (!reward.isCollected()) {
                    BufferedImage liveImage = imageService.getRandomLiveImage(gameObjectId);
                    drawImage(g2d, liveImage, x, y, width, height);
                }
            } else if (gameObject instanceof Building) {
                BufferedImage buildingImage = imageService.getRandomBuildingImage(gameObjectId);
                drawImage(g2d, buildingImage, x, y, width, height);

            }
            if (gameObject instanceof PlayerCar playerCar) {
                if (playerCar instanceof AgileCar) {
                    BufferedImage agileCarImage = imageService.getRandomAgileCarImage(gameObjectId);
                    if (playerCar.hasCrashCooldown()) {
                        drawImage(g2d, agileCarImage, x, y, width, height, 0.5f);
                    } else {
                        drawImage(g2d, agileCarImage, x, y, width, height);
                    }
                } else {
                    BufferedImage fastCarImage = imageService.getRandomFastCarImage(gameObjectId);
                    if (playerCar.hasCrashCooldown()) {
                        drawImage(g2d, fastCarImage, x, y, width, height, 0.5f);
                    } else {
                        drawImage(g2d, fastCarImage, x, y, width, height);
                    }
                }
            }

        }
    }

    private void drawImage(Graphics2D g2d, BufferedImage image, int x, int y, int width, int height) {
        g2d.drawImage(image, x, y, width, height, null);
    }

    private void drawImage(Graphics2D g2d, BufferedImage image, int x, int y, int width, int height, float alpha) {
        Composite oldComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image, x, y, width, height, null);
        g2d.setComposite(oldComposite);
    }


}
