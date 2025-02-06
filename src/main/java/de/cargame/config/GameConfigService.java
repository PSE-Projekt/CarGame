package de.cargame.config;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GameConfigService {

    private static GameConfigService INSTANCE = new GameConfigService();
    private final GameConfig gameConfig = new GameConfig();


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
            return Integer.parseInt(value.get());
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
            return Double.parseDouble(value.get());
        } catch (NumberFormatException e) {
            log.warn("Failed to parse value for key: {} as double: {}", configKey, value);
            return -1;
        }
    }


}
