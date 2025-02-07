package de.cargame.model.service;

import de.cargame.model.service.entity.sound.SelectSound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SoundServiceTest {

    private final SelectSound mockSelectSoundClip = mock(SelectSound.class);

    /**
     * Tests for the SoundService's `getInstance` method,
     * which provides a singleton instance of the SoundService class.
     */

    @Test
    void testGetInstanceReturnsNonNullInstance() {
        // Act
        SoundService instance = SoundService.getInstance();

        // Assert
        assertNotNull(instance, "SoundService.getInstance() should not return null.");
    }


    @Test
    void testGetInstanceAlwaysReturnsSameInstance() {
        // Act
        SoundService firstInstance = SoundService.getInstance();
        SoundService secondInstance = SoundService.getInstance();

        // Assert
        assertNotNull(firstInstance, "First instance should not be null.");
        assertNotNull(secondInstance, "Second instance should not be null.");
        assert (firstInstance == secondInstance) : "Both instances should be the same (singleton).";
    }
}