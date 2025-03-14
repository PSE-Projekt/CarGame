package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class SceneButtonTest {

    private TestSceneButton sceneButton;

    @Start
    private void start(Stage stage) {
        this.sceneButton = new TestSceneButton();
    }

    @Test
    public void testInitialDisplayIsDefault() {
        ImageView display = sceneButton.getDisplay();
        assertEquals(sceneButton.getDefaultImage(), display.getImage(), "Default image should be displayed initially.");
    }

    @Test
    public void testSelectChangesImage() {
        sceneButton.select();
        assertEquals(sceneButton.getSelectedImage(), sceneButton.getDisplay().getImage(), "Selecting should update the image.");
    }

    @Test
    public void testDeselectRevertsImage() {
        sceneButton.select();
        sceneButton.deselect();
        assertEquals(sceneButton.getDefaultImage(), sceneButton.getDisplay().getImage(), "Deselecting should revert to default image.");
    }

    // Testable subclass since SceneButton is abstract
    private static class TestSceneButton extends SceneButton {
        public TestSceneButton() {
            super("/frontend/backToMenu_default.png", "/frontend/backToMenu_selected.png"); // Bypass constructor loading images
        }


        public Image getDefaultImage() {
            return super.defaultDisplay;
        }

        public Image getSelectedImage() {
            return super.displayOnSelection;
        }

        public ImageView getDisplay() {
            return super.display;
        }

        @Override
        public void onClick(ApiHandler apiHandler, String playerId) {
            // do nothing
        }
    }
}
