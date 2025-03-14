package de.cargame.view;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import javafx.geometry.Pos;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CustomSceneTest extends ApplicationTest {

    private TestCustomScene testScene;
    private ApiHandler apiHandlerMock;

    @BeforeEach
    void setUp() {
        apiHandlerMock = mock(ApiHandler.class);
        testScene = new TestCustomScene(apiHandlerMock);
    }

    @Test
    void testRootConfiguration() {
        VBox root = (VBox) testScene.getRoot();
        assertNotNull(root, "Root should be set");
        assertEquals(Pos.CENTER, root.getAlignment(), "Root alignment should be CENTER");
        assertEquals(Color.BLACK, ((BackgroundFill) root.getBackground().getFills().get(0)).getFill(), "Background should be black");
    }

    @Test
    void testScreenDimensions() {
        assertEquals(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), testScene.getWidth(), "Width should match config");
        assertEquals(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT), testScene.getHeight(), "Height should match config");
    }

    private static class TestCustomScene extends CustomScene {
        public TestCustomScene(ApiHandler apiHandler) {
            super(apiHandler);
            configureRoot();
        }

        @Override
        protected void setup() {
            // No-op for testing
        }
    }
}
