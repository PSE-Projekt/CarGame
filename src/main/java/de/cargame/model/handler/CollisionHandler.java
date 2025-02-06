package de.cargame.model.handler;

import de.cargame.model.entity.gameobject.GameObject;
import de.cargame.model.entity.gameobject.Reward;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.SoundService;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CollisionHandler {

    private final PlayerHandler playerHandler;
    private final SoundService soundService;

    public CollisionHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
        this.soundService = new SoundService();
    }


    /**
     * Checks for collisions between the player's car and other collidable objects in the game.
     * Iterates through the list of game objects, filters out non-collidable objects and the player's car,
     * and detects intersections between the player's car and the remaining objects. If a collision
     * occurs, appropriate handling mechanisms are invoked. Currently, the collision detection
     * algorithm supports rectangular boundaries only.
     *
     * @param gameObjects A list of all game objects in the game world, which may include static
     *                    and dynamic objects, both collidable and non-collidable. The method
     *                    filters this list for processable objects.
     */
    public void checkCollision(List<GameObject> gameObjects) {
        Player player = playerHandler.getPlayer();
        PlayerCar playerCar = player.getPlayerCar();
        List<GameObject> collidableObjects = filterCollidableObjects(gameObjects);
        collidableObjects.remove(playerCar);


        for (GameObject collidableObject : collidableObjects) {
            Shape playerCarBound = playerCar.getBound();
            Shape collidableObjectBound = collidableObject.getBound();

            boolean collision = shapesIntersect(playerCarBound, collidableObjectBound);
            if (collision) {
                log.debug("Collision detected between {} and {}", playerCar, collidableObject);
                handleCollision(playerCar, collidableObject);
            }
        }
    }


    /**
     * Handles the collision between the player's car and another game object.
     * If the collision is with a reward, invokes the reward-specific handling mechanism.
     * Handles damage or crash events if applicable.
     *
     * @param playerCar       The player's car involved in the collision.
     * @param collisionObject The game object that the player's car collided with.
     */
    private void handleCollision(PlayerCar playerCar, GameObject collisionObject) {
        if (collisionObject instanceof Reward reward) {
            handleCollisionReward(reward);
        } else {
            handleCollisionCrash(playerCar);
        }
    }


    /**
     * Handles the logic for processing a collision with a reward object.
     * If the reward is of type {@code Life} and has not yet been collected,
     * it increases the player's life count and plays the appropriate reward collection sound.
     * The reward is marked as collected after the operation.
     *
     * @param reward The reward object that is involved in the collision.
     */
    private void handleCollisionReward(Reward reward) {
        boolean collected = reward.collect(playerHandler);
        if (collected) {
            soundService.playRewardCollectedSound();
        }
    }


    /**
     * Handles the aftermath of a crash collision involving the player's car.
     * This method ensures that, if the player car is not under a crash cooldown,
     * the crash sound is played, the player's life is decreased, and the last crash
     * time is updated to the current time.
     *
     * @param playerCar The player's car involved in the crash collision.
     */
    private void handleCollisionCrash(PlayerCar playerCar) {
        if (!playerCar.hasCrashCooldown()) {
            log.debug("PlayerCar of player {} crashed", playerCar.getPlayerId());
            soundService.playCrashSound();
            playerHandler.decreaseLife();
            playerCar.setLastCrashTime();
        }else{
            log.debug("PlayerCar of player {} crashed but is under cooldown -> no live decreased", playerCar.getPlayerId());
        }
    }


    private List<GameObject> filterCollidableObjects(List<GameObject> gameObjects) {
        return new ArrayList<>(gameObjects
                .stream()
                .filter(GameObject::isCollidable)
                .toList());
    }

    private boolean shapesIntersect(Shape shape1, Shape shape2) {
        Area area1 = new Area(shape1);
        area1.intersect(new Area(shape2));
        return !area1.isEmpty();
    }

}


