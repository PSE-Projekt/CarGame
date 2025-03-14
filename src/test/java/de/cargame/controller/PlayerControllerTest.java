package de.cargame.controller;

import de.cargame.exception.PlayerNotFoundException;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {


    @Mock
    private PlayerService playerService;
    Player testPlayer = new Player(playerService);
    @InjectMocks
    private PlayerController playerController;

    @Mock
    private UserInputObserver userInputObserver;

    @Test
    void testGetKeyboardPlayer_whenKeyboardPlayerExists_thenReturnPlayer() {

        // when
        when(playerService.getKeyboardPlayer()).thenReturn(testPlayer);
        Player keyboardPlayer = playerController.getKeyboardPlayer();

        // then
        assertEquals(keyboardPlayer, testPlayer, "Expected the returned keyboard player to match the mocked player.");
        verify(playerService).getKeyboardPlayer();
    }

    @Test
    void testGetKeyboardPlayer_whenNoKeyboardPlayer_thenThrowPlayerNotFoundException() {

        // when
        when(playerService.getKeyboardPlayer()).thenReturn(null);

        // then
        assertThrows(PlayerNotFoundException.class, () -> playerController.getKeyboardPlayer(),
                "Expected getKeyboardPlayer to throw PlayerNotFoundException when no player is returned by the service.");
    }

    @Test
    void testCreatePlayerKeyboard() {
        // when
        playerController.createPlayerKeyboard();

        // then
        verify(playerService).createPlayerKeyboard();
    }

    @Test
    void testCreatePlayerGamepad() {
        // when
        playerController.createPlayerGamepad();

        // then
        verify(playerService).createPlayerGamepad();
    }

    @Test
    void testSetCarSelection() {
        // given
        String playerId = "12345";
        CarType carType = CarType.AGILE_CAR;

        // when
        playerController.setCarSelection(playerId, carType);

        // then
        verify(playerService).setCarSelection(playerId, carType);
    }

    @Test
    void testIsPlaying() {
        // given
        String playerId = "12345";
        when(playerService.isPlaying(playerId)).thenReturn(true);

        // when
        boolean isPlaying = playerController.isPlaying(playerId);

        // then
        assertEquals(true, isPlaying, "Expected the 'isPlaying' method to return 'true' based on the mock configuration.");
        verify(playerService).isPlaying(playerId);
    }

    @Test
    void testRegisterInputObserver() {
        // given
        String playerId = "12345";

        // when
        playerController.registerInputObserver(userInputObserver, playerId);

        // then
        verify(playerService).registerInputObserver(userInputObserver, playerId);
    }

    @Test
    void testRegisterPlayerObserver() {
        // given
        String playerId = "12345";
        PlayerObserver playerObserver = update -> {
        };

        // when
        playerController.registerPlayerObserver(playerObserver, playerId);

        // then
        verify(playerService).registerPlayerObserver(playerObserver, playerId);
    }

    @Test
    void testRegisterPlayerObserver_InvalidPlayer() {
        // given
        String playerId = "unknown";
        PlayerObserver playerObserver = update -> {
        };

        // when

        Mockito.doThrow(new PlayerNotFoundException("Player not found"))
                .when(playerService).registerPlayerObserver(playerObserver, playerId);

        // then
        assertThrows(PlayerNotFoundException.class,
                () -> playerController.registerPlayerObserver(playerObserver, playerId),
                "Expected registerPlayerObserver to throw PlayerNotFoundException when player is not found");
    }
}
