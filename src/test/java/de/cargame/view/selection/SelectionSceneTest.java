package de.cargame.view.selection;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.InputReceiver;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class SelectionSceneTest {

    private SelectionScene selectionScene;
    private ApiHandler mockApiHandler;
    private PlayerAPI mockPlayerApi;
    private GameInstanceAPI mockGameInstanceApi;
    private GameStateAPI mockGameStateApi;

    @Start
    public void start(Stage stage) {
        mockApiHandler = mock(ApiHandler.class);
        mockPlayerApi = mock(PlayerAPI.class);
        mockGameInstanceApi = mock(GameInstanceAPI.class);
        mockGameStateApi = mock(GameStateAPI.class);

        when(mockApiHandler.getPlayerApi()).thenReturn(mockPlayerApi);
        when(mockApiHandler.getGameInstanceApi()).thenReturn(mockGameInstanceApi);
        when(mockApiHandler.getGameStateApi()).thenReturn(mockGameStateApi);
        when(mockApiHandler.getInputReceiverKeyboard()).thenReturn(mock(InputReceiver.class));
        when(mockApiHandler.getInputReceiverGamePad()).thenReturn(mock(InputReceiver.class));

        selectionScene = new SelectionScene(mockApiHandler);

        // Attach scene to stage for TestFX compatibility
        stage.setScene(selectionScene);
        stage.show();
    }

    @BeforeEach
    void setupMocks() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        when(mockPlayerApi.getKeyboardPlayerId()).thenReturn("keyboardPlayer");
        when(mockPlayerApi.getGamepadPlayerId()).thenReturn("gamepadPlayer");
        when(mockApiHandler.getPlayerOneId()).thenReturn("keyboardPlayer");
    }

    @Test
    public void testSetup_CreatesCorrectSelectionViews_Singleplayer() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);

        Platform.runLater(() -> selectionScene.setup());
        WaitForAsyncUtils.waitForFxEvents(); // Ensure JavaFX execution completes

        List<SelectionInstanceView> selectionViews = selectionScene.getSelectionInstanceViews();
        assertEquals(1, selectionViews.size(), "Singleplayer mode should have 1 selection view");
    }

    @Test
    public void testSetup_CreatesCorrectSelectionViews_Multiplayer() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.MULTIPLAYER);

        Platform.runLater(() -> selectionScene.setup());
        WaitForAsyncUtils.waitForFxEvents();

        List<SelectionInstanceView> selectionViews = selectionScene.getSelectionInstanceViews();
        assertEquals(2, selectionViews.size(), "Multiplayer mode should have 2 selection views");
    }

    @Test
    public void testProceedToGame_SingleplayerMode() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        SelectionInstanceView mockSelectionView = mock(SelectionInstanceView.class);
        when(mockSelectionView.isReady()).thenReturn(true);

        selectionScene.getSelectionInstanceViews().add(mockSelectionView);

        Platform.runLater(() -> selectionScene.proceedToGame());
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockGameInstanceApi).startGamePlayerKeyboard();
        verify(mockGameStateApi).setGameState(GameState.IN_GAME);
        verify(mockApiHandler).switchScene(GameState.IN_GAME);
    }

    @Test
    public void testProceedToGame_MultiplayerMode() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.MULTIPLAYER);
        SelectionInstanceView mockSelectionView1 = mock(SelectionInstanceView.class);
        SelectionInstanceView mockSelectionView2 = mock(SelectionInstanceView.class);
        when(mockSelectionView1.isReady()).thenReturn(true);
        when(mockSelectionView2.isReady()).thenReturn(true);

        selectionScene.getSelectionInstanceViews().add(mockSelectionView1);
        selectionScene.getSelectionInstanceViews().add(mockSelectionView2);

        Platform.runLater(() -> selectionScene.proceedToGame());
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockGameInstanceApi).startGamePlayerKeyboard();
        verify(mockGameInstanceApi).startGamePlayerGamePad();
        verify(mockGameStateApi).setGameState(GameState.IN_GAME);
        verify(mockApiHandler).switchScene(GameState.IN_GAME);
    }

    @Test
    public void testProceedToGame_DoesNotStartIfNotAllReady() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.MULTIPLAYER);
        SelectionInstanceView mockSelectionView1 = mock(SelectionInstanceView.class);
        SelectionInstanceView mockSelectionView2 = mock(SelectionInstanceView.class);
        when(mockSelectionView1.isReady()).thenReturn(true);
        when(mockSelectionView2.isReady()).thenReturn(false);

        selectionScene.getSelectionInstanceViews().add(mockSelectionView1);
        selectionScene.getSelectionInstanceViews().add(mockSelectionView2);

        Platform.runLater(() -> selectionScene.proceedToGame());
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockGameInstanceApi, never()).startGamePlayerKeyboard();
        verify(mockGameInstanceApi, never()).startGamePlayerGamePad();
        verify(mockGameStateApi, never()).setGameState(GameState.IN_GAME);
        verify(mockApiHandler, never()).switchScene(GameState.IN_GAME);
    }

    @Test
    public void testProceedToGame_ThrowsExceptionIfGameModeNotSet() {
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.NOT_SET);

        Platform.runLater(() -> assertThrows(IllegalStateException.class, () -> selectionScene.proceedToGame()));

        WaitForAsyncUtils.waitForFxEvents();
    }
}
