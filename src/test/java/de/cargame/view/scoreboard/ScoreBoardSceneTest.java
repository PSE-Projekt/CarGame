package de.cargame.view.scoreboard;

import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.Score;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.InputReceiver;
import de.cargame.view.navigation.Navigator;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ScoreBoardSceneTest {

    private ScoreBoardScene scoreBoardScene;
    private ApiHandler mockApiHandler;
    private GameStateAPI mockGameStateApi;
    private PlayerAPI mockPlayerApi;
    private InputReceiver mockInputReceiverGamePad;
    private InputReceiver mockInputReceiverKeyboard;
    private Player playerMock;

    @Start
    private void start(Stage stage) {
        mockApiHandler = mock(ApiHandler.class);
        mockGameStateApi = mock(GameStateAPI.class);
        mockPlayerApi = mock(PlayerAPI.class);
        mockInputReceiverGamePad = mock(InputReceiver.class);
        mockInputReceiverKeyboard = mock(InputReceiver.class);

        when(mockApiHandler.getGameStateApi()).thenReturn(mockGameStateApi);
        when(mockApiHandler.getPlayerApi()).thenReturn(mockPlayerApi);
        when(mockApiHandler.getInputReceiverGamePad()).thenReturn(mockInputReceiverGamePad);
        when(mockApiHandler.getInputReceiverKeyboard()).thenReturn(mockInputReceiverKeyboard);

        scoreBoardScene = new ScoreBoardScene(mockApiHandler);

        // Attach scene to stage for TestFX compatibility
        stage.setScene(scoreBoardScene);

        when(mockGameStateApi.getGameState()).thenReturn(GameState.SCORE_BOARD);
        when(mockGameStateApi.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        when(mockPlayerApi.getKeyboardPlayerId()).thenReturn("keyboardPlayer");
        when(mockPlayerApi.getGamepadPlayerId()).thenReturn("gamepadPlayer");
        when(mockApiHandler.getPlayerOneId()).thenReturn("keyboardPlayer");
        this.playerMock = mock(Player.class);
        Score score = new Score();
        score.increaseScore(100.0);

        when(playerMock.getScore()).thenReturn(score);

        when(mockPlayerApi.getKeyboardPlayer()).thenReturn(playerMock);
    }

    @Test
    void testUIElementsAreInitialized() {
        VBox sceneContent = (VBox) scoreBoardScene.getRoot();
        HBox buttonContainer = scoreBoardScene.getButtonContainer();

        assertNotNull(sceneContent, "sceneContent should be initialized");
        assertNotNull(buttonContainer, "buttonContainer should be initialized");
        assertEquals(2, buttonContainer.getChildren().size(), "buttonContainer should have 2 buttons");
    }

    @Test
    void testSetup_CorrectlyRendersScoreBoard() {
        Platform.runLater(() -> scoreBoardScene.setup());

        WaitForAsyncUtils.waitForFxEvents();

        VBox sceneContent = (VBox) ((VBox) scoreBoardScene.getRoot()).getChildren().get(0);
        assertEquals(3, sceneContent.getChildren().size(), "sceneContent should contain 3 sections");

        assertInstanceOf(Text.class, ((VBox) sceneContent.getChildren().get(0)).getChildren().get(0), "First section should contain a title");
        assertInstanceOf(ScoreView.class, sceneContent.getChildren().get(1), "Second section should contain ScoreView");
        assertInstanceOf(HBox.class, sceneContent.getChildren().get(2), "Third section should contain buttons");

        verify(mockInputReceiverGamePad).assignNavigator(eq("gamepadPlayer"), any(Navigator.class));
        verify(mockInputReceiverKeyboard).assignNavigator(eq("keyboardPlayer"), any(Navigator.class));
    }

    @Test
    void testSetup_ThrowsExceptionIfGameStateNotScoreBoard() {
        when(mockGameStateApi.getGameState()).thenReturn(GameState.IN_GAME);

        Platform.runLater(() -> {
            assertThrows(IllegalStateException.class, () -> scoreBoardScene.setup());
        });
    }
}
