package de.cargame.model.handler;

import de.cargame.model.entity.gameobject.GameObject;
import de.cargame.model.entity.gameobject.Reward;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import de.cargame.model.service.SoundService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class CollisionHandlerTest {


    @Test
    void testCollisionWithReward() throws NoSuchFieldException, IllegalAccessException {
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        Player mockPlayer = mock(Player.class);
        PlayerCar mockPlayerCar = mock(PlayerCar.class);
        Reward mockReward = mock(Reward.class);
        SoundService mockSoundService = mock(SoundService.class);

        when(mockPlayerHandler.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPlayerCar()).thenReturn(mockPlayerCar);
        when(mockPlayerCar.getBound()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(mockReward.getBound()).thenReturn(new Rectangle(25, 25, 20, 20));
        when(mockReward.isCollidable()).thenReturn(true);
        when(mockReward.collect(mockPlayerHandler)).thenReturn(true);

        CollisionHandler collisionHandler = new CollisionHandler(mockPlayerHandler);
        Field soundService = CollisionHandler.class.getDeclaredField("soundService");
        soundService.setAccessible(true);
        soundService.set(collisionHandler, mockSoundService);


        List<GameObject> gameObjects = Arrays.asList(mockPlayerCar, mockReward);

        collisionHandler.checkCollision(gameObjects);

        verify(mockReward).collect(mockPlayerHandler);
        verify(mockSoundService, never()).playCrashSound(); // No crash sounds for rewards.
    }

    @Test
    void testCollisionWithNonCollidableObject() {
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        Player mockPlayer = mock(Player.class);
        PlayerCar mockPlayerCar = mock(PlayerCar.class);
        GameObject nonCollidableObject = mock(GameObject.class);

        when(mockPlayerHandler.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPlayerCar()).thenReturn(mockPlayerCar);
        when(mockPlayerCar.getBound()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(nonCollidableObject.isCollidable()).thenReturn(false);

        CollisionHandler collisionHandler = new CollisionHandler(mockPlayerHandler);
        List<GameObject> gameObjects = Arrays.asList(mockPlayerCar, nonCollidableObject);

        collisionHandler.checkCollision(gameObjects);

        verify(nonCollidableObject, never()).getBound();
    }

    @Test
    void testCollisionWithCrashAndCooldown() {
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        Player mockPlayer = mock(Player.class);
        PlayerCar mockPlayerCar = mock(PlayerCar.class);
        GameObject obstacle = mock(GameObject.class);
        SoundService mockSoundService = mock(SoundService.class);

        when(mockPlayerHandler.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPlayerCar()).thenReturn(mockPlayerCar);
        when(mockPlayerCar.getBound()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(obstacle.getBound()).thenReturn(new Rectangle(25, 25, 20, 20));
        when(obstacle.isCollidable()).thenReturn(true);
        when(mockPlayerCar.hasCrashCooldown()).thenReturn(true);

        CollisionHandler collisionHandler = new CollisionHandler(mockPlayerHandler);
        List<GameObject> gameObjects = Arrays.asList(mockPlayerCar, obstacle);

        collisionHandler.checkCollision(gameObjects);

        verify(mockPlayerCar).hasCrashCooldown();
        verify(mockPlayerHandler, never()).decreaseLife();
        verify(mockSoundService, never()).playCrashSound();
    }

    @Test
    void testCollisionWithCrashWithoutCooldown() throws NoSuchFieldException, IllegalAccessException {
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        Player mockPlayer = mock(Player.class);
        PlayerCar mockPlayerCar = mock(PlayerCar.class);
        GameObject obstacle = mock(GameObject.class);
        SoundService mockSoundService = mock(SoundService.class);

        when(mockPlayerHandler.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPlayerCar()).thenReturn(mockPlayerCar);
        when(mockPlayerCar.getBound()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(obstacle.getBound()).thenReturn(new Rectangle(25, 25, 20, 20));
        when(obstacle.isCollidable()).thenReturn(true);
        when(mockPlayerCar.hasCrashCooldown()).thenReturn(false);

        CollisionHandler collisionHandler = new CollisionHandler(mockPlayerHandler);
        Field soundService = CollisionHandler.class.getDeclaredField("soundService");
        soundService.setAccessible(true);
        soundService.set(collisionHandler, mockSoundService);


        List<GameObject> gameObjects = Arrays.asList(mockPlayerCar, obstacle);

        collisionHandler.checkCollision(gameObjects);

        verify(mockPlayerHandler).decreaseLife();
        verify(mockPlayerCar).setLastCrashTime();
    }

    @Test
    void testNoCollision() {
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        Player mockPlayer = mock(Player.class);
        PlayerCar mockPlayerCar = mock(PlayerCar.class);
        GameObject distantObject = mock(GameObject.class);

        when(mockPlayerHandler.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPlayerCar()).thenReturn(mockPlayerCar);
        when(mockPlayerCar.getBound()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(distantObject.getBound()).thenReturn(new Rectangle(100, 100, 20, 20));
        when(distantObject.isCollidable()).thenReturn(true);

        CollisionHandler collisionHandler = new CollisionHandler(mockPlayerHandler);
        List<GameObject> gameObjects = Arrays.asList(mockPlayerCar, distantObject);

        collisionHandler.checkCollision(gameObjects);

        verify(distantObject, times(1)).getBound();
        verify(mockPlayerHandler, never()).decreaseLife();
    }
}