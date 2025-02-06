package de.cargame.controller;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import de.cargame.controller.input.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InputDeviceTest {

    @Mock
    UserInputBundle userInputBundle;

    @InjectMocks
    private Keyboard keyboard = new Keyboard();

    @InjectMocks
    private GamePad gamepad = new GamePad();



}
