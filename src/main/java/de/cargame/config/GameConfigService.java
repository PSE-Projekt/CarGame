package de.cargame.config;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * The GameConfigService class provides access to game configuration values
 * using a singleton design pattern. It wraps the functionality of the
 * {@code GameConfig} class, simplifying access to configuration data while
 * ensuring that only a single instance of the service exists throughout the
 * application.
 * <p>
 * The class enables retrieval of configuration values in specific data types,
 * including integers and doubles, while handling parsing errors and missing
 * keys gracefully with appropriate logging.
 */
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GameConfigService {

    private static GameConfigService INSTANCE = new GameConfigService();
    private final GameConfig gameConfig = new GameConfig();


    /**
     * Provides access to the singleton instance of the GameConfigService.
     * This method ensures that only one instance of the GameConfigService is created
     * and globally accessible throughout the application.
     *
     * @return the singleton instance of GameConfigService
     */
    public static GameConfigService getInstance() {
        if( INSTANCE == null){
            INSTANCE = new GameConfigService();
        }
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
