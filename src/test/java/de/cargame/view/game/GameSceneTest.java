package de.cargame.view.game;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.InputReceiver;
import javafx.animation.Animation;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameSceneTest extends ApplicationTest {

    @Mock
    private ApiHandler apiHandler;
    @Mock
    private GameStateAPI gameStateAPI;
    @Mock
    private GameInstanceAPI gameInstanceAPI;
    @Mock
    private PlayerAPI playerAPI;
    @Mock
    private InputReceiver receiver;

    private GameScene gameScene;

    @BeforeEach
    void setUp() {
        gameScene = new GameScene(apiHandler);
    }

    @Test
    void testSetupSingleplayer() {
        when(gameStateAPI.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        when(apiHandler.getPlayerOneId()).thenReturn("player1");
        when(apiHandler.getGameStateApi()).thenReturn(gameStateAPI);
        when(apiHandler.getGameInstanceApi()).thenReturn(gameInstanceAPI);
        when(apiHandler.getPlayerApi()).thenReturn(playerAPI);
        when(apiHandler.getInputReceiverKeyboard()).thenReturn(receiver);
        when(apiHandler.getInputReceiverGamePad()).thenReturn(receiver);
        when(gameInstanceAPI.getModel()).thenReturn(List.of(
                new GameModelData("player1", new LinkedList<>())));

        gameScene.setup();
        VBox root = (VBox) gameScene.getRoot();

        assertEquals(1, root.getChildren().size(), "Should have one GameInstanceView in singleplayer mode");
    }

    @Test
    void testSetupMultiplayer() {
        when(gameStateAPI.getGameMode()).thenReturn(GameMode.MULTIPLAYER);
        when(playerAPI.getKeyboardPlayer()).thenReturn(mock(Player.class));
        when(playerAPI.getGamepadPlayer()).thenReturn(mock(Player.class));
        when(playerAPI.getKeyboardPlayerId()).thenReturn("player1");
        when(playerAPI.getGamepadPlayerId()).thenReturn("player2");
        when(apiHandler.getGameStateApi()).thenReturn(gameStateAPI);
        when(apiHandler.getInputReceiverKeyboard()).thenReturn(receiver);
        when(apiHandler.getInputReceiverGamePad()).thenReturn(receiver);
        when(apiHandler.getPlayerApi()).thenReturn(playerAPI);

        when(gameInstanceAPI.getModel()).thenReturn(List.of(
                new GameModelData("player1", new LinkedList<>()), new GameModelData("player2", new LinkedList<>())));
        when(apiHandler.getGameInstanceApi()).thenReturn(gameInstanceAPI);

        gameScene.setup();
        VBox root = (VBox) gameScene.getRoot();

        assertEquals(2, root.getChildren().size(), "Should have two GameInstanceViews in multiplayer mode");
    }

    @Test
    void testSetupMultiplayerMissingPlayer() {
        assertThrows(NullPointerException.class, gameScene::setup, "Should throw exception if a player is missing");
    }

    @Test
    void testStartRendering() {
        gameScene.startRendering();
        assertTrue(gameScene.isActive(), "Rendering should be active");
        assertEquals(Animation.Status.RUNNING, gameScene.getTimeline().getStatus(), "Animation should be running");
    }

    @Test
    void testStopRendering() {
        gameScene.startRendering();
        gameScene.stopRendering();
        assertFalse(gameScene.isActive(), "Rendering should be inactive");
        assertEquals(Animation.Status.STOPPED, gameScene.getTimeline().getStatus(), "Animation should be stopped");
    }
}