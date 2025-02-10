package de.cargame.controller.input.gamepadmapping;

import lombok.Getter;

@Getter
public enum GamePads {

    XBOX_WIRELESS_CONTROLLER(new XBoxWirelessControllerGamepadPadMapping());


    private final GamePadMapping gamePadMapping;


    GamePads(GamePadMapping gamePadMapping) {
        this.gamePadMapping = gamePadMapping;
    }
}
