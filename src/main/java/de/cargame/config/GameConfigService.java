package de.cargame.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class GameConfigService {

    private static GameConfigService INSTANCE = new GameConfigService();
    private final GameConfig gameConfig = new GameConfig();


    private GameConfigService() {
    }


    public static GameConfigService getInstance() {
        return INSTANCE;
    }

    public int loadInteger(ConfigKey configKey) {
        Optional<String> value = gameConfig.getValueKey(configKey);
        if (value.isEmpty()) {
            log.warn("Failed to load value for key: {}", configKey);
            return -1;
        }
        try {
            int val = Integer.parseInt(value.get());
            return val;
        } catch (NumberFormatException e) {
            log.warn("Failed to parse value for key: {} as integer: {}", configKey, value);
            return -1;
        }
    }


    public double loadDouble(ConfigKey configKey) {
        Optional<String> value = gameConfig.getValueKey(configKey);
        if (value.isEmpty()) {
            log.warn("Failed to load value for key: {}", configKey);
            return -1;
        }
        try {
            double val = Double.parseDouble(value.get());
            return val;
        } catch (NumberFormatException e) {
            log.warn("Failed to parse value for key: {} as double: {}", configKey, value);
            return -1;
        }
    }


}
