package de.cargame.controller.input.gamepadmapping;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum GamePads {

    XBOX_WIRELESS_CONTROLLER(new XBoxWirelessControllerGamepadPadMapping()),
    PS3_Controller(new Ps3Mapping());


    private final GamePadMapping gamePadMapping;


    GamePads(GamePadMapping gamePadMapping) {
        this.gamePadMapping = gamePadMapping;
    }

    public static Optional<GamePadMapping> getGamePadMapping(String controllerName) {
        GamePads[] values = GamePads.values();
        for (GamePads value : values) {
            if (value.gamePadMapping.getControllerName().equals(controllerName)) {
                return Optional.of(value.gamePadMapping);
            }
        }
        return Optional.empty();
    }

    public GamePadMapping getGamePadMapping() {
        return gamePadMapping;
    }
}
