package de.cargame;

import de.cargame.controller.GameApplicationManager;
import de.cargame.view.ApplicationView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class CarGameApplicationTest {


    @Test
    void testStart_shouldRegisterApplicationView() throws Exception {
        // Given
        Stage primaryStage = mock(Stage.class);
        GameApplicationManager mockGameApplicationManager = mock(GameApplicationManager.class);
        CarGameApplication carGameApplication = new CarGameApplication();

        // Set the GameApplicationManager instance via reflection
        Field gameApplicationManagerField = CarGameApplication.class.getDeclaredField("gameApplicationManager");
        gameApplicationManagerField.setAccessible(true);
        gameApplicationManagerField.set(carGameApplication, mockGameApplicationManager);

        ApplicationView mockApplicationView = mock(ApplicationView.class);
        CarGameApplication spyCarGameApplication = Mockito.spy(carGameApplication);

        doReturn(mockApplicationView).when(spyCarGameApplication).getApplicationView(primaryStage);

        // When
        spyCarGameApplication.start(primaryStage);

        // Then
        verify(mockGameApplicationManager).registerApplicationView(mockApplicationView);
    }

}